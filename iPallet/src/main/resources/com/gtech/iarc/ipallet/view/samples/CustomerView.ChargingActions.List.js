Ext.ns("EMobility", "EMobility.CustomerView", "EMobility.CustomerView.ChargingActions");

EMobility.CustomerView.ChargingActions.List = Ext.extend(EMobility.MessageView, {
	header: false,
	
	initId: function() {
		this.supr().initId.call(this);
		
		return Ext.apply(this._ID, {
			// Grid
			GRID 	: Ext.id(),
			
			// Serach form
			FORM 	: Ext.id(),
			MONTH 	: Ext.id(),
			FROM 	: Ext.id(),
			TO 	 	: Ext.id(),
			
			// Summary panel
			ACTIONS	: Ext.id(),
			ENERGY	: Ext.id(),
			TIME	: Ext.id(),
			
			// Toolbar
			RESTART	: Ext.id(),
			STOP	: Ext.id()
		});		
	},
	
	dateRe: /[0-9\/]/,
	
	disableStop: function(disabled) {
		Ext.getCmp(this._ID.STOP).setDisabled(disabled);
	},
	
	disableRestart: function(disabled) {
		Ext.getCmp(this._ID.RESTART).setDisabled(disabled);
	},
	
	disableButtons: function(disabled) {
		this.disableStop(disabled);
		this.disableRestart(disabled);
	},
	
	getGrid: function() {
		return this.findById(this._ID.GRID);
	},		

	getStatus: function(s) {
		var css = s == "PAUSED" ? 'icon-silk-bullet_red' : 'icon-silk-bullet_green',			
			title = Ext.util.Format.htmlEncode(s.value),
			html = '<div class="' + css + '" style="width: 16px; height: 16px; margin:auto;" title="'+ title +'"></div>';		
		
		return html;
	},
	
	enableComboDates: function(enable) {
		this.findById(this._ID.FROM).setDisabled(!enable);
		this.findById(this._ID.TO).setDisabled(!enable);		
	},
		
	search: function() {
		this.store.load({params: {start: this.start = 0, limit: this.limit, sum: true}});
	},
	
	updateSum: function(data) {
		if (data.chargingTime) {
			var E = Ext.get;
			
			// Update UI text of sum
			E(this._ID.ACTIONS).update('' + data.totalSize);
			E(this._ID.TIME).update('' + data.chargingTime);
			E(this._ID.ENERGY).update('' + data.chargedEnergy);
		}
	},
	
	resetView: function() {
		// Reset form
		this.findById(this._ID.FORM).getForm().reset();
		this.enableComboDates(false);
		
		// Clear exception if any
		this.hideMessage();
		
		// Reset sum
		this.updateSum({
			totalSize: "0",
			chargingTime: "0",
			chargedEnergy: "0"
		});
		
		// Reset grid and paging toolbar		
		this.store.loadData({
			totalSize: 0,
			items: []
		});
		
		// Focus on Month combobox
		this.findById(this._ID.MONTH).focus(true);
	},
	
	getSelectedId: function() {
		var s = this.getGrid().getSelectionModel().getSelected();		
		return s ? s.data.id : false;
	},
	
	start: 0,
	limit: 10,
	
	initView: function() {
		// Search
		var f = this.findById(this._ID.FORM).getForm();										
		if (f.isValid()) {
			this.search();
		}
	},
	
	initComponent: function() {
		
		Ext.QuickTips.init();
		
		var view = this,
			ID = this.initId(),
		
		// Data store
		store = 
		view.store = new Ext.data.JsonStore({
			totalProperty: 'totalSize',
            root: 'items',
			remoteSort: true,
			idProperty: '',			
			proxy: new Ext.ux.data.DwrProxy({
				apiActionToHandlerMap: {
					read: {
						dwrFunction: ChargeActionService.getChargeActions,
						exceptionScope: view,
						getDwrArgsFunction: function(s) {
							var d,								
								from, to;
							
							// Get data by range
							from = view.findById(ID.FROM).getValue();
							to = view.findById(ID.TO).getValue(); // beginning of the day
							to = new Date(to.getTime() + 86399999); // end of the day (see EMSG-356) 
							
							var p = s.params,
								params = [view.model.loginName, !!p.sum, from, to, p.start, p.limit],
								sort = p.sort;
							
							if (sort) {
								params = params.concat([sort, p.dir]);									
							}

							return params;
						}						
					}					
				}, 
				listeners : {
					load: function(c, d, p) {
						view.updateSum(d.reader.jsonData);
					}
				}
				
			}),
			
			fields: [
			  {name: "id"},
			  {name: "station"},
			  {name: "start"},
			  {name: "end"},
			  {name: "duration"},
			  {name: "amount"},
			  {name: "status"},
			  {name: "chargedEnergy"},
			  {name: "chargingTime"}
			]
		});
		
		// Apply config
		Ext.apply(this, {
			layout: 'border',
			listeners: {
				show: {
					buffer: 1000,
					fn: function() {
						this.initView();
					},
					scope: this
				}
			},
			border: false,
			defaults: { border: false },
			items: [
				this.buildErrorPanel(),
				// left panel
				{
					title: 'Search',
					region: 'west',					
					width: 170,
					collapsible: false,										
					layout: 'border',
					defaults: { border: false },					
					items: [
						{
							region: 'north',
							xtype: 'form',
							labelSeparator: '',							
							id: view._ID.FORM,
							labelWidth: 40,
							bodyStyle: 'padding: 10px 10px 10px 0px',
							height: 135,
							defaults: {
								anchor: '100%',
								border: false 
							},
							items: [
							{
								xtype: 'datefield',
								fieldLabel: "From",
								id: view._ID.FROM,															
								maskRe: view.dateRe,
								format: 'm-d-Y',
								value: new Date().add(Date.DAY,-15),
								listeners: {
									blur: function(f) {
										var c = view.findById(view._ID.TO),											
											from = f.getValue(),
											to = c.getValue();
										
										if (to < from) {
											c.setValue(from);	
										}
									}
								}
							},{
								xtype: 'datefield',
								fieldLabel: "To",
								id: view._ID.TO,														
								maskRe: view.dateRe,
								format: 'm-d-Y',
								value: new Date()
							}
							],
							buttons: [
								{
									text: "Search", 
									iconCls: "icon-x16-search",
									handler: function() {
										var f = view.findById(view._ID.FORM).getForm();										
										if (f.isValid()) {
											view.search();
										}
									}
								},
								{
									text: "Clear", 
									iconCls: "icon-x16-clear",
									handler: function() {
										view.resetView();
									}
								}
							]
						}, {
							region: 'center'
						}
					]
				},
				// center panel
				{
					region: 'center',
					layout: 'border',
					defaults: { border: false },
					items: [
						{
							region: 'north',
							height: 50,
							layout: 'hbox',
							layoutConfig: {
								align : 'stretch',
								pack  : 'start'
							},
							defaults: {
								flex: 1,
								style: 'text-align: center',
								padding: 5
							},
							items: [
								{
									html: "<span class='em-charging-actions-sum' id='" + view._ID.ACTIONS
											+ "'>0</span><br/><span class='em-charging-actions-text'>No of Charging Actions</span>"
								}, {
									html: "<span class='em-charging-actions-sum' id='" + view._ID.ENERGY
											+ "'>0</span><br/><span class='em-charging-actions-text'>Charged Energy (kWh)</span>"
								}, {
									html: "<span class='em-charging-actions-sum' id='"  + view._ID.TIME
											+ "'>0</span><br/><span class='em-charging-actions-text'>Sum of Charging Time</span>"
								}
							]
						}, {
							region: 'center',
							layout: 'fit',
							items: {
								xtype: 'grid',
								id: ID.GRID,
								sortInfo: {
								    field: 'start',
								    direction: 'DESC' // or 'ASC' (case sensitive for local sorting)
								},
								loadMask: true,
								viewConfig: {
									forceFit: true,
									headersDisabled: true,
									emptyText: "No actions to display."
								},
								store: store,
								sm: new Ext.grid.RowSelectionModel({
									singleSelect: true,
									listeners: {
										selectionchange: {
											buffer: 300,
											fn: function(sm) {
												var s = sm.getSelected(),
													status = s ? s.data.status.id : false;
													
												switch(status) {
													case "INITIALIZED":
													case "IN_PROGRESS":
														// Restart disabled, Stop enabled
														this.disableRestart(true);
														this.disableStop(false);
														break;
													
													case "PAUSED":
														// Restart enabled, Stop disabled
														this.disableRestart(false);
														this.disableStop(true);
														break;
														
													case "FINISHED":
													default:
														this.disableButtons(true);
												}
											},
											scope: view
										}							
									}
								}),
								cm: new Ext.grid.ColumnModel({									
									defaults: {										
										sortable: false
							        },
									columns: [
										new Ext.grid.RowNumberer(),
										{
											header: "Date",
											align: 'center',
											width: 90,
											fixed: true,
											dataIndex: "start",
											renderer: function(v) {
												return v.format("m-d-Y");
											}
										},{
											header: "Charging Station",
											// width: 120,
											dataIndex: "station"										
										},{								
											header: "Start",
											fixed: true,
											width: 50,
											align: 'center',
											dataIndex: "start",
											renderer: function(v) {
												return v.format("H:i");
											}
										},/*{								
											header: "End",
											fixed: true,
											align: 'center',
											width: 50,
											dataIndex: "end",
											renderer: function(v, meta, record){
												var s = record.data.status;												
												return s.id == "FINISHED" ? v.format("H:i") : view.getStatus(s);
											}
										},*/{
											header: "Duration",
											fixed: true,
											width: 50,
											align: 'center',
											dataIndex: "duration",
											renderer: function(v, meta, record){
												var s = record.data.status;												
												return s.id == "FINISHED" ? v : view.getStatus(s);
											}											
										},{
											header: "Charged Energy (kWh)",
											width: 100,
											dataIndex: "amount",
											renderer: function(value, meta, record, row, col, store){
												var id = Ext.id();												
												(function(){
													new Ext.ProgressBar({ renderTo: id, value: (value/55), text: value.toFixed(2) });
												}).defer(25);
												return '<span id="' + id + '"></span>';
											}
										}
									]
								}),
								tbar: {
									buttonAlign: 'right',
									style: 'padding: 3px 0px;',
									defaults: { style: 'padding-right: 7px;' },
									items: [
									{
										text: 'Restart',
										id: ID.RESTART,
										disabled: true,
										iconCls: 'icon-silk-bullet_go',
										handler: function() {
											var view = this,
												id = this.getSelectedId();
											
											if (id) {
												ChargeActionService.restart(id, {
													callback: function(success) {
														// Show result
														if (success) {
															view.showSuccessMessage("Selected charging action of uid [ " + id + " ] has been restarted successfully.");
															
															// Reload the store
															view.store.reload();
														}
														else {
															view.showErrorMesssage("Failed to restart selected action of processUid " + id);
														}
													},
													exceptionScope: this
												});	
											}
										},
										scope: this
									}, {
										text: 'Stop',
										id: ID.STOP,
										iconCls: 'icon-silk-delete',
										disabled: true,
										handler: function() {
											var view = this,												
												id = this.getSelectedId();
											
											if (id) {
												ChargeActionService.stop(id, {
													callback: function(success) {
														// Show result
														if (success) {
															view.showSuccessMessage("Selected charging action of uid [ " + id + " ] has been stopped successfully.");
															
															// Reload the store
															view.store.reload();
														} 
														else {
															view.showErrorMesssage("Failed to stop selected action of processUid " + id);
														}
													},
													exceptionScope: this
												});	
											}
										},
										scope: this
									}
									]
								},
								// paging bar on the bottom
								bbar: {
									xtype: 'paging',
									pageSize: view.limit,
									store: store,
									displayInfo: true,
									displayMsg: 'Displaying actions {0} - {1} of {2}',
									emptyMsg: "No actions to display"
								}
							}
						}
					]
				}
			]
		});
		
		this.supr().initComponent.call(this);
	}
});

Ext.reg('emo-view-charging-actions-list', EMobility.CustomerView.ChargingActions.List);