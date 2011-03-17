Ext.ns("EMobility.Intuity");

EMobility.Intuity.Logbook = Ext.extend(EMobility.Intuity.View, {
	toolbarTitle: "<@i18nText key='logbook.toolbar.title' />",
		
	initId: function() {
		return this._ID = {
			// Search form
			FORM 	: Ext.id(),		
			FROM 	: Ext.id(),
			TO 	 	: Ext.id(),
			
			// Total summary
			ACTIONS	: Ext.id(),
			ENERGY	: Ext.id(),
			TIME	: Ext.id(),
			
			GRID    : Ext.id()
		};
	},
	
	dateRe: /[0-9\/]/,
	dateFormat: 'm/d/Y',
	
	getStatus: function(s) {
		if (s) {
			var css = s.id == "PAUSED" ? 'icon-silk-bullet_red' : 'icon-silk-bullet_green',
				title = Ext.util.Format.htmlEncode(s.value),
				html = '<div class="' + css + '" style="width: 16px; height: 16px; margin:auto;" title="'+ title +'"></div>';		
			
			return html;
		}
		
		return "";
	},
		
	enableComboDates: function(enable) {
		this.findById(this._ID.FROM).setDisabled(!enable);
		this.findById(this._ID.TO).setDisabled(!enable);		
	},
		
	initView: function() {
		this.search();
	},
	
	search: function() {
		this.store.load({params: {start: this.start = 0, limit: this.limit, sum: true}});
	},
	
	updateSum: function(data) {
		if (data.chargingTime) {
			var E = Ext.get,
				a = E(this._ID.ACTIONS),
				t = E(this._ID.TIME),
				e = E(this._ID.ENERGY);			
			
			// Update UI text of sum
			if (a) {
				a.update('' + data.totalSize);	
			}
			
			if (t) {
				t.update('' + data.chargingTime);	
			}
			
			if (e) {
				e.update('' + data.chargedEnergy);	
			}						
		}
	},
	
	resetView: function() {
		// Reset form
		// this.findById(this._ID.FORM).getForm().reset();
		this.enableComboDates(false);
		
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
		})		
		
	},
	
	start: 0,
	limit: 20,
	
	/* Config functions */
	buildMainLeft: function() {
		var view = this,
			ID = this._ID;
			
		return {
			id: ID.GRID,
			xtype: 'lite-grid',
			disableSelection: true,
			sortInfo: {
				field: 'start',
				direction: 'DESC' // or 'ASC' (case sensitive for local sorting)
			},
			loadMask: true,
			view: new Ext.ux.grid.GroupingView({
				emptyText: "<@i18nText key='logbook.grid.emptyText' />",
				headersDisabled: true,
				forceFit:true
			}),
			store: view.store,
			colModel: new Ext.grid.ColumnModel({
				defaults: {
					sortable: false
				},
				columns: [									
					new Ext.ux.grid.MonthNumberer(),
					{
						header: "<@i18nText key='logbook.grid.columns.day.header' />",
						align: 'center',
						width: 45,
						fixed: true,
						dataIndex: "start",
						id: 'iarc-calendar-day',
						renderer: function(v) {
							return v.format("d");
						},
						groupRenderer: function(v) {
							return v.format("m-y");
						}
					},									
					{
						header: "<@i18nText key='logbook.grid.columns.location.header' />",										
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
					},
					{
						header: "<@i18nText key='logbook.grid.columns.duration.header' />",
						fixed: true,
						width: 60,
						align: 'center',
						dataIndex: "duration",
						renderer: function(v, meta, record){
							var s = record.data.status;												
							return (s && s.id == "FINISHED") ? v : view.getStatus(s);							
						}
					},
					{
						header: "<@i18nText key='logbook.grid.columns.kwh.header' />",
						fixed: true,
						width: 35,
						align: 'left',
						dataIndex: "amount",
						renderer: function(value, meta, record, row, col, store){											
							return value.toFixed(2);
						}
					},
					{																				
						dataIndex: "amount",
						width: 140,
						align: 'left',
						id: 'iarc-energy-progress-bar',
						fixed: true,
						renderer: function(value, meta, record, row, col, store){
							var id = Ext.id();												
							(function(){
								new Ext.ProgressBar({ 
									renderTo: id,
									cls: 'iarc-grid-progress-bar',
									border: false,
									height: 10,
									value: (value/55)
								});
							}).defer(350);
							return '<span id="' + id + '"></span>';
						}
					}
				]
			}),
			// paging bar on the bottom
			bbar: {
				xtype: 'paging',
				style: 'padding-left: 60px',
				pageSize: view.limit,
				store: view.store,
				displayInfo: true,
				displayMsg: "<@i18nText key='logbook.bbar.displayMsg' />"
					+ " {0} - {1} " + "<@i18nText key='common.lang.of' />" + "{2}",
				emptyMsg: "<@i18nText key='logbook.bbar.emptyText' />"
			}
		};
	},
	
	buildMainRight: function() {
		var ID = this._ID;
		return {
			layout:'vbox',
			layoutConfig: {
				align : 'stretch',
				pack  : 'start'
			},
			items: [
				{
					height: 150,
					style: 'padding: 5px;',
					html: 	"<div class='iarc-logbook-total-charge-caption' id='" + ID.ENERGY	+ "'>" +
								"0" +
							"</div>" 
				},
				{
					height: 25
				},	
				{																        
					height: 150,
					style: 'padding: 5px;',
					html: 	"<div class='iarc-logbook-total-action-caption' id='" + ID.ACTIONS	+ "'>" +
								"0" +
							"</div>" 
				},
				{
					height: 25
				},	
				{																        
					height: 150,
					style: 'padding: 5px;',
					html: 	"<div class='iarc-logbook-total-time-caption' id='" + ID.TIME	+ "'>" +
								"00:00:00" +
							"</div>" 
				}
			]
		};
	},
	
	buildToolbarRight: function() {
		var view = this,
			ID = this._ID;
		
		return [
			// Left side						
			{
				xtype: 'datefield',										
				id: ID.FROM,												
				maskRe: view.dateRe,
				format: view.dateFormat,
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
			},
			{	xtype: 'tbspacer', width: 10	}, // add a 10px space
			
			{   xtype: 'label', html : 'to'},
			
			{	xtype: 'tbspacer', width: 10	}, // add a 10px space
			{
				xtype: 'datefield',							
				id: ID.TO,
				value: new Date(),
				format: view.dateFormat,
				maskRe: view.dateRe
			},
			{	xtype: 'tbspacer', width: 10	}, // add a 10px space
			{
				xtype: 'iarc-button',
				width: 60,
				text: "<@i18nText key='common.button.search' />",							
				listeners: {
					click: {
						buffer: 500,
						fn: this.search.createDelegate(this)
					}
				}
			}
		];
	},
	
	initComponent: function() {		
		var view = this,
			ID = this.initId(),
			
		// Data store
		store = 
		view.store = new Ext.data.GroupingStore({
			remoteSort: true,			
			groupField: 'start',
			proxy: new Ext.ux.data.DwrProxy({
				apiActionToHandlerMap: {
					read: {
						dwrFunction: ChargeActionService.getChargeActions,
						getDwrArgsFunction: function(s) {
							var d,							
								from, to;
							
							// Get data by range
							from = view.findById(view._ID.FROM).getValue();
							to = view.findById(view._ID.TO).getValue(); // beginning of the day
							to = to.add(Date.HOUR, 23).add(Date.Minute, 59).add(Date.SECOND, 59); //end of the day
							
							var p = s.params,
								params = [!!p.sum, from, to, p.start, p.limit],
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
			reader: new Ext.data.JsonReader({
			   	// metadata configuration options:			   	
			  	root: 'items',
			  	totalProperty: 'totalSize',
				idProperty: '',
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
			})			
		});
		
		view.on("afterrender", function(){
			var bbar = Ext.getCmp(view._ID.GRID).getBottomToolbar();
			
			if(bbar && bbar.refresh){
				bbar.refresh.hide();
			}
		});
		
		EMobility.Intuity.Logbook.superclass.initComponent.call(this);
	}
});