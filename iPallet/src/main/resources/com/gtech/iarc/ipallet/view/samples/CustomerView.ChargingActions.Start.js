Ext.ns("EMobility", "EMobility.CustomerView", "EMobility.CustomerView.ChargingActions");

EMobility.CustomerView.ChargingActions.Start = Ext.extend(EMobility.MessageView, {
	initId: function() {
		this.supr().initId.call(this);
		
		return Ext.apply(this._ID, {
			FORM	: Ext.id(),
			
			// Buttons
			START	: Ext.id()
		});		
	},
	
	getUserForm: function(basic) {
		var f = this.findById(this._ID.FORM);
		return basic ? f.getForm() : f;
	},
	
	disableButtons: function(disabled) {
		Ext.getCmp(this._ID.START).setDisabled(disabled);
	},
	
	submitForm: function() {
		var view = this,
			form = view.getUserForm(),
			f = form.getForm();
			
		if (f.isValid()) {
			// Disable buttons
			view.disableButtons(true);
			
			// Get entered values
			var	o = f.getFieldValues(true);
			
			view.showLoadingMessage("Sending charging request..");
			
			ChargeActionService.start(view.model.customer.loginName, o.cepas, o.spot,							
			{
				callback: function (success) {					
					if (success) {
						view.showSuccessMessage("Request submitted successfully. Please allow up to 1 min for action to start.");
					}
				},
				exceptionScope: view
			});	
		}		
	},	
	
	setChargingSpots: function(spots) {		
		if (spots) {
			// console.log(spots);
						
			for( var i = 0, s, data = [], l = spots.length; i < l; ++i ) {
				if (s = spots[i]) {
					var id = s.uid,
						status = s.status.value,
						note = s.note,
						text = id + " - " + status + (note ? " : " + note : "");
						
					if (id) {
						data.push([id, text]);						
					}
				}
			}
			
			// Charging Spots
			this.storeChargingSpots.loadData(data);
		}
		else {
			this.showErrorMessage("Charging Spots data not available.");
		}
	},
	
	initView: function() {
		var view = this,
			model = this.model,
			f = this.getUserForm(true),
			
			// Address
			add = model.address,
			address = add ? add.address : "",
			
			// Customer
			c = model.customer,
			customer = c.firstName + " " + c.lastName + " (" + c.loginName + ")";
		
		// Set form values
		f.setValues({
			customer	: customer,
			address		: model.address.address,
			type		: model.type.value
		});
		
		// Charging spots
		this.setChargingSpots(model.chargingSpots);		
	},	
	
	initComponent: function() {
		
		Ext.QuickTips.init();
		
		var 
		
		view = this,
		ID = this.initId(),
		
		storeRFID = view.storeRFID = new Ext.data.JsonStore({
			autoLoad : false,
			idProperty: 'cardNumber',
			proxy: new Ext.ux.data.DwrProxy({
				apiActionToHandlerMap: {
					read: {
						dwrFunction: RFIDCardService.getRegisteredCards,						
						getDwrArgsFunction: function(s) {
							return [view.model.customer.loginName];							
						},
						exceptionScope: this
					}
				}
			}),
			listeners: {
				load: function(s, data) {
					if (!data || data.length == 0) {
						view.showErrorMessage("User doesn't have any CEPAS card registered yet.");
						
						// Disable Start button
						view.disableButtons(true);
					}
				}
			},
			fields : [
				"cardType", 
				"cardNumber", 
				"created", 
				"description"
			]
		}),		
		
		storeChargingSpots =
		view.storeChargingSpots = new Ext.data.ArrayStore({
				fields: ['value', 'text'],
				data: [					
				]
			});
		
		// Apply config
		Ext.apply(this, {
			layout: 'border',
			border: false,
			style: 'padding: 8px; background: #fff;',
			defaults: { border: false },
			listeners: {
				afterrender: {
					fn: view.initView.createDelegate(this),
					buffer: 500
				}
			},
			items: [
				this.buildMessagePanel({
					defaultMsg: "Please select a CEPAS card, and a charging spot to start an action." 
				}),
				{
					region: 'center',					
					xtype: 'form',		
					api: {
						submit:	view.submitForm.createDelegate(view)
					},
					id: ID.FORM,
					defaults: { anchor: '100%' },
					border: false,
					items: [
						{
							xtype: 'textfield',
							fieldLabel: 'Customer',
							name: 'customer',
							readOnly: true							
						}, {
							xtype: 'textfield',
							fieldLabel: 'Station',
							name: 'address',
							readOnly: true							
						}, {
							xtype: 'textfield',
							fieldLabel: 'Slot Type',
							readOnly: true,
							name: 'type'
						},						
						{
							xtype: "combo",
							fieldLabel: "Charging Spot",
							id: ID.SPOTS,
							name: 'spot',
							disableKeyFilter: true,
							triggerAction: 'all',
							mode: 'local',
							editable: false,							
							allowBlank: false,
							store: storeChargingSpots,
							emptyText: 'Select an Available Charging spot',
							listEmptyText: "No charging spots found.",
							valueField: 'value',
							displayField: 'text',
							listeners: {
								select: function(combo, record, index) {
								}
							}					
						},
						{
							fieldLabel: "CEPAS Card",
							xtype: "combo",									
							id: ID.CEPAS,
							name: 'cepas',
							disableKeyFilter: true,
							triggerAction: 'all',
							allowBlank: false,
							editable: false,
							emptyText: 'Select a CEPAS card',
							store: storeRFID,
							listEmptyText: "No CEPAS card found.",
							valueField: 'cardNumber',
							displayField: 'cardNumber',
							listeners: {
								select: function(combo, record, index) {
								}
							}
						}
					]						
				}
			],			
			buttons:[
				{
					text: "Start", 
					iconCls: "icon-silk-bullet_go",
					id: ID.START,
					handler: function(){						
						var f = view.getUserForm(true);						
						// Compulsory fields have been completed
						f.submit();
					}
				},
				{
					text: "Close", 
					iconCls: "icon-x16-clear",
					handler: function() {
						// Close view
						var w = view.ownerCt;
						w.close();	
					}
				}
			]
		});
		
		this.supr().initComponent.call(this);
	}
});

Ext.reg('emo-view-charging-actions-start', EMobility.CustomerView.ChargingActions.Start);