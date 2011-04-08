Ext.ns("EMobility");

EMobility.CustomerSearch = Ext.extend(EMobility.MessageView, {
	title: "Customer Search",
	iconCls: "icon-x16-chargingactions",	
	maxInstance: 1,
	
	width: 700,
	height: 400,

	maximized: true,
	resizable: true,
	
	initId: function() {
		this.supr().initId.call(this);
		
		return Ext.apply(this._ID, {
			// Search form
			FORM 	: Ext.id(),
			MONTH 	: Ext.id(),
			FROM 	: Ext.id(),
			TO 	 	: Ext.id(),
			ACTIONS	: Ext.id(),
			ENERGY	: Ext.id(),
			TIME	: Ext.id(),
			CONTRACT: Ext.id(),
			
			// Grid
			CUSTOMERS : Ext.id(),
			
			// Toolbar
			ADD		  	: Ext.id(),
			RESULT	  	: Ext.id()
		});
	},
	
	dateRe: /[0-9\/]/,

	enableComboDates: function(enable) {
		this.findById(this._ID.FROM).setDisabled(!enable);
		this.findById(this._ID.TO).setDisabled(!enable);		
	},
	
	getGrid: function() {
		return this.findById(this._ID.CUSTOMERS);
	},	
	
	search: function() {
		this.store.load();
	},		
	
	resetView: function() {
		// Reset form
		this.findById(this._ID.FORM).getForm().reset();
		this.enableComboDates(false);
		
		// Reset grid and paging toolbar		
		this.store.loadData({
			items: []
		});
		
		// Reset status
		Ext.getCmp(this._ID.RESULT).setText("");
		
		// Clear exception if any
		this.hideMessage();
		
		// Focus on Month combobox
		this.findById(this._ID.MONTH).focus(true);
	},
	
	limit: 10,
	
	getFilter: function() {
		var ID = this._ID,
			f = this.findById(ID.FORM).getForm().getFieldValues(false),		
			d,
			month = this.findById(ID.MONTH).getValue(),
			from, to;
		
		if (month >= 0) {
			if (month < 12) {
				// Get date by month
				d = new Date();
				d.setMonth(month);
				
				from = d.getFirstDateOfMonth();
				to = d.getLastDateOfMonth();								
			} else {
				// Get data by range
				from = this.findById(ID.FROM).getValue();
				to = this.findById(ID.TO).getValue();
			}
		}
						
		f.from = from;
		f.to = to;
		
		delete f.month;
		
		return f;
	},
	
	initComponent: function() {
		
		Ext.QuickTips.init();
		
		var E = EMobility.Enums,
			view = this,
			ID = this.initId(),
		
		// Data store
		store = 
		view.store = new Ext.data.JsonStore({
			remoteSort: false,
			idProperty: '',
			proxy: new Ext.ux.data.DwrProxy({
				apiActionToHandlerMap: {
					read: {
						dwrFunction: CustomerService.getCustomers,						
						getDwrArgsFunction: function(s) {
							var f = view.getFilter();
							return f ? [f] : [];
						},
						exceptionScope: this
					}
				}
			}),
			fields: [
			  {name: "userId"},
			  {name: "firstName"},
			  {name: "lastName"},
			  {name: "companyName"},
			  {name: "address"},
			  {name: "landlinePhoneNumber"},
			  {name: "licensePlate"},
			  {name: "registeredFromTimestampUTC"},
			  {name: "contractStatus"},
			  {name: "externalBillingUid"}
			],
			listeners: {
				load: function(s) {
					Ext.getCmp(view._ID.RESULT).setText("<b>" + store.getCount() + "</b> search results found.");
				}
			}
		});
		
        view.statusTypesStore = new Ext.data.JsonStore({
            fields: ['id', 'value'],
            listeners : {
            	'load' : function() {							
							var rec = Ext.data.Record.create(['id','value']);
							// populate record with values
							var d = new rec({
							  id:'',
							  value:'Any'
							});
							this.insert(0,d);
						}
					
            	},
            
            proxy: new Ext.ux.data.DwrProxy({
                apiActionToHandlerMap: {
                    read: {
                        dwrFunction: CustomerService.getContractStatusTypes,                      
                        getDwrArgsFunction: function(s) {                                                           
                            return [];
                        },
                        exceptionScope: view
                    }
                }
            })
        });		
		
		// Apply config
		Ext.apply(this, {
			layout: 'border',
			defaults: { border: false },
			items: [
				this.buildErrorPanel(), 
				{
					region: 'center',
					layout: 'border',
					border: false,
					items: [
						// left panel				
						{
							header: false,
							border: false,
							region: 'west',
							width: 270,
							split : true,
							bodyStyle: 'background: #fff',
							collapsible: true,
							layout: 'border',
							defaults: { border: false },
							items: [
								{
									region: 'north',
									xtype: 'form',
									labelSeparator: '',
									id: ID.FORM,
									labelWidth: 90,
									padding: 10,
									height: 340,
									defaults: {
										anchor: '100%'
									},
									items: [
									{
										xtype: "textfield",
										name: 'userId',
										fieldLabel: "User Id"
									},
									{
										xtype: "textfield",
										name: 'firstName',
										fieldLabel: "First Name"
									},
									{
										xtype: "textfield",
										name: 'lastName',
										fieldLabel: "Last Name"
									},
									{
										xtype: "textfield",
										name: 'companyName',
										fieldLabel: "Company Name"
									},
									{
										xtype: "textfield",
										name: 'landlinePhoneNumber',
										fieldLabel: "Phone"
									},
									{
										xtype: "textfield",
										name: 'licensePlate',
										fieldLabel: "License Plate"
									},
									{
										xtype: "combo",
										fieldLabel: "Contract Status",
										id: ID.CONTRACT,
										name: 'contractStatus',
										disableKeyFilter: true,
										triggerAction: 'all',
										editable: false,
										value:'Any',
										store: view.statusTypesStore,
										valueField: 'id',
										displayField: 'value'
									},
									{
										xtype: "textfield",
										name: 'sapId',
										fieldLabel: "SAP ID"
									},
									{
										xtype: "combo",										
										fieldLabel: "Registered in",
										id: view._ID.MONTH,
										name: 'month',
										disableKeyFilter: true,
										triggerAction: 'all',
										mode: 'local',        	
										editable: false,	
										value: -1,
										store: new Ext.data.ArrayStore({
											fields: ['value', 'text'],
											data: [							
												[-1, 'Any'],
												[0, 'January'],
												[1, 'February'],
												[2, 'March'],
												[3, 'April'],
												[4, 'May'],
												[5, 'June'],
												[6, 'July'],
												[7, 'August'],
												[8, 'September'],
												[9, 'October'],
												[10, 'November'],
												[11, 'December'],
												[12, 'Custom..']
											]
										}),        			
										valueField: 'value',
										displayField: 'text',
										listeners: {
											select: function(combo, record, index) {				    				
												if (record.data.value === 12) {																				
													view.enableComboDates(true);
													
													// Focus on the From combobox
													(function(){
														this.focus();
													}).defer(10, view.findById(view._ID.FROM))											
												} else {													
													view.enableComboDates(false);											
												}
											}
										}					
									}, {
										xtype: 'datefield',
										fieldLabel: "From",			
										name: 'from',
										id: view._ID.FROM,
										disabled: true,								
										maskRe: view.dateRe,
										value: new Date().format('m-d-Y'),
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
										name: 'to',
										id: view._ID.TO,
										disabled: true,								
										maskRe: view.dateRe,
										value: new Date().format('m-d-Y')
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
							layout: 'fit',
							xtype: 'grid',
							id: ID.CUSTOMERS,
							sortInfo: {
								field: 'userId',
								direction: 'ASC' // or 'ASC' (case sensitive for local sorting)
							},
							loadMask: true,								
							viewConfig: {
								forceFit: true,
								emptyText: "No customers to display."
							},
							store: store,
							listeners: {
								rowdblclick: function(g, i, e) {
									var s = g.getSelectionModel().getSelected(),
										d = s.data,
										v = $webtop.getViewAsWindow({
												id: 'view.CustomerView',
												model: {
													loginName: d.userId,											
													firstName: d.firstName,
													lastName: d.lastName
												}
											});
										
									// Show window
									if (v) {
										v.show();
									}

								}
							},			
							sm: new Ext.grid.RowSelectionModel({								
							}),
								columns: [
									new Ext.grid.RowNumberer(),
									{
										header: "User Id",
										dataIndex: "userId",
										sortable: true,										
										width: 70,
										fixed: true
									},
									{
										header: "First Name",
										dataIndex: "firstName",
										sortable: true
									},
									{
										header: "Last Name",
										dataIndex: "lastName",
										sortable: true
									},
									{
										header: "Company",
										dataIndex: "companyName",
										sortable: true
									},
									{
										header: "Address",
										dataIndex: "address",
										sortable: true										
									},
									{								
										header: "Phone",
										dataIndex: "landlinePhoneNumber",
										sortable: true
									},
									{
										header: "License Plate",
										dataIndex: "licensePlate",
										sortable: true
									},							
									{
										header: "Contract Status",
										dataIndex: "contractStatus",
										menuDisabled:true,
										renderer: EMobility.constants.lookupRenderer
//										renderer: function(v) {											
//											return v ? EMobility.Enums[v] : "Unknown";												
//										}
									},
									{
										header: "SAP ID",
										dataIndex: "externalBillingUid",
										sortable: true
									},
									{
										header: "Registered on",
										dataIndex: "registeredFromTimestampUTC",
										sortable: true,
										width: 90,
										align: 'center',
										renderer: function(v) {
											if (v) {
												return new Date(v).format("m/d/Y");
											}
											return "";
										},
										fixed: true
									}
								],
//							}),					
							tbar: {						
								style: 'padding: 3px 0px;',
								defaults: { style: 'padding: 0px 7px;' },
								items: [
								{
									xtype: 'tbtext', 
									id: ID.RESULT
								}, '->',
								{
									text: 'Add User',
									id: ID.ADD,
									qtip: 'Add a new customer',
									iconCls: 'icon-silk-add',
									handler: function() {					        	
										// Forward to USS
										var ctx = $webtop.getCustomContext(),
											url = ctx ? ctx.url.uss.register : false;
										
										if (url) {
											window.open(url);	
										} else {
											this.showErrorMessage("Technical Exception: URL to Registration page not available");
										}
									},
									scope: this
								}
								]
							}
						}
					]
				}
			]
		}
		);
		
		this.supr().initComponent.call(this);
	}
});