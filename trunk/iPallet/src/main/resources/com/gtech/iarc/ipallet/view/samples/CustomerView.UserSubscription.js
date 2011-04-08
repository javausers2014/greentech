Ext.ns("EMobility.CustomerView");

/**
 * User Subscription Screen
 * 
 * Displays user's subscription related details
 * 
 * @author Ankur Kapila
 */
EMobility.CustomerView.UserSubscription = Ext.extend(EMobility.MessageView, {	
	title : "Subscription Information",
	iconCls: 'icon-silk-link_edit',
	
	initId: function() {
		this.supr().initId.call(this);
		
		return Ext.apply(this._ID, {
			VehicleForm   : Ext.id(),
			PriceplanForm : Ext.id(),
			PlanDetails   : Ext.id(),
			PlanStatus    : Ext.id(),
			RFIDGrid      : Ext.id(),
			
			// Buttons
			BTN_SUBSCRIBE	  : Ext.id(),
			BTN_UNSUBSCRIBE	  : Ext.id()
		});
	},
	
	userId: "",
	
	resetView: function() {
		// Reset form
		this.setUserPricePlan(this.registeredPlan);
	},
	
	disableSubscribe: function(disabled) {
		Ext.getCmp(this._ID.BTN_SUBSCRIBE).setDisabled(disabled);
		Ext.getCmp(this._ID.PriceplanForm).getForm().findField("priceplan").setDisabled(disabled);
	},
	
	disableUnsubscribe: function(disabled) {
		Ext.getCmp(this._ID.BTN_UNSUBSCRIBE).setDisabled(disabled);
	},
	
	disableButtons: function(disabled) {
		this.disableSubscribe(disabled);
		this.disableUnsubscribe(disabled);
	},
	
	initComponent : function() {
		var _p = this,
			ID = this.initId();
		
		if(_p.model && _p.model.loginName){
			_p.userId = _p.model.loginName;
		}
		
		Ext.QuickTips.init();
		
		var removeIconRenderer = EMobility.constants.removeIconRenderer;
			
		var selectCombo = {
			editable: false,
			typeAhead: false,
			forceSelection : true,
			triggerAction : "all",
			xtype: "combo"
		};
		
		var pricePlanCombo = Ext.apply({
			name: 'priceplan',
			mode: 'local',
			fieldLabel : "Price Plan",
			anchor : "95%",
			valueField: "id",
			displayField: "name",
			store: _p.stores.PricePlans,
			listeners : {
				'select' : function(combo, record, index) {
					var field = Ext.getCmp(_p._ID.PlanDetails);
					if (field) {
						field.update(record.get("description"));
					}
				}
			}
		}, selectCombo);
			
		var vehicleTypeCombo = Ext.apply({
			name: 'vehicleType',
			mode: 'local',
			fieldLabel : "Vehicle Type",
			anchor : "100%",
			valueField: "name",
			displayField: "name",
			emptyText: "Select vehicle...",
			store: _p.stores.VehicleTypes,
			listeners: {
				'select' : function(combo, record, index) {
					var licenseplate = Ext.getCmp(_p._ID.VehicleForm).getForm().findField("licenseplate");
					var b = record.get("name") == "None";

					licenseplate.clearInvalid();
					licenseplate.setDisabled(b);
					
					if(!b){ 
						licenseplate.validate();
					}
				}
			}
		}, selectCombo);
			
		var infoBox = {
			xtype: "box",
			autoScroll : true,
			style : {
				// TODO Move to CSS
				background : "none repeat scroll 0 0 #F8F8F8",
				border : "1px dotted #CCCCCC",
				"font-size" : "12px",
//					"line-height" : "12px",
//					margin : "1em 0",
				padding : "5px"
			}
		};	
		
		// TODO Move the components out
		var cepasGrid = {			
			xtype : "editorgrid",
			id : _p._ID.RFIDGrid,
			clicksToEdit: 1,			
			stripeRows: true,
			viewConfig : {
				forceFit: true,
				emptyText : "No CEPAS cards registered! Card must be registered to use the infrastructure. Click 'Add' to register your CEPAS card now."
			},
			sm : new Ext.grid.RowSelectionModel({
						singleSelect : true
					}),
			store : _p.stores.RFIDCards,
			colModel : new Ext.grid.ColumnModel({
				defaults: {
					sortable: true
				},
				columns : [
					new Ext.grid.RowNumberer(), 
					{
						header: "Card Number",
						width: 120,
						dataIndex : "cardNumber",
						editor : new Ext.form.TextField({
							allowBlank: false,
							maxLength: 16,
							minLength: 16,
							minLengthText: "Card number length is 16.",
							maxLengthText: "Card number length is 16.",
							maskRe: /[0-9]/
						})
					}, {
						header: "Description",
						width: 150,
						dataIndex:"description",
						editor : new Ext.form.TextField({
							emptyText: "Add description"
						})
					}, {
						header: "Added On",
						xtype: "datecolumn",
						width: 70,
						dataIndex : "created",		
						format: "Y-m-d"
					},{
						header : "Remove",
						width : 50,
						tooltip : "Remove Card",
						renderer : removeIconRenderer,
						listeners:{
							click: _p.removeRFIDCard.createDelegate(_p)
						}
					}]
			}),
			tbar : {
				buttonAlign: 'right',
				style: 'padding: 3px 0px;',
				defaults: { style: 'padding-right: 7px;' },
				items: [{
					xtype : "button",
					text : "Add",
					iconCls : "icon-silk-add",
					scope : _p,
					handler : function(button, event) {
						var grid   = Ext.getCmp(_p._ID.RFIDGrid);
						var store  = grid.getStore();
						
		                var RFIDCard = store.recordType;
		                var p = new RFIDCard({
							cardNumber:  "",
							description: "",
							created : new Date()
		                });
		                p.markDirty();
		                grid.stopEditing();
		                store.insert(0, p);
		                store.getAt(0);
		                grid.startEditing(0, 1);
					}
				},{
					xtype : "button",
					text : "Save",
					iconCls : "icon-silk-disk_multiple",
					scope : _p,
					handler : function(button, event) {
						_p.saveRFIDCards();
					}
				}, {
					xtype : "button",
					text : "Refresh",
					iconCls : "icon-silk-arrow_refresh",
					scope : _p,
					handler : function(button, event) {
						this.refreshRFIDCard();	
					}
				}]
			}
		};

		var vehicleForm = {
			id: _p._ID.VehicleForm,
			xtype: "form",
			border: false,
			height: 95,
			items: [
				vehicleTypeCombo,
				{
					name: "licenseplate",
					xtype: "textfield",
					anchor : "100%",
					fieldLabel : "License Plate",
					emptyText: "Enter license plate number",
					allowBlank: false,
					maxLength: 50
				}
			],
			buttons : [{
				xtype   : "button",
				text    : "Save",
				iconCls : "icon-silk-disk_multiple",
				handler : _p.updateVehicleType.createDelegate(_p)
			}]
		};
			
							
		var pricePlanForm = {
			id: _p._ID.PriceplanForm,
			xtype : "form",
			items : [{
		            layout:'column',
		            border: false,		            
		            defaults: { border: false, layout: "form" },
		            items:[{
		                columnWidth:.40,		                		                
		                items: [		                	
		                	pricePlanCombo,		                	
		                	Ext.apply({
								id: _p._ID.PlanStatus,
								name : "planstatus",
								height : 70,
								width : "auto",
								anchor : "95%",
								fieldLabel : "Plan Status"
							}, infoBox)
		                ]
		            },{
		                columnWidth:.60,		                		                
		                items: [
	                	Ext.apply({
							id: _p._ID.PlanDetails,
							name : "plandetail",
							height : 100,
							width : "auto",
							anchor : "100%",
							fieldLabel : "Price Plan Details"									
						},infoBox)
						]
		            }]
            	}
            	],
			buttons : [
				{					
					text : "Subscribe",
					disabled: true,
					id : ID.BTN_SUBSCRIBE,
					iconCls: 'icon-silk-accept',
					handler: _p.subscribe.createDelegate(_p)
				}, 
				{					
					text : "Unsubscribe",
					disabled: true,
					id : ID.BTN_UNSUBSCRIBE,
					iconCls: "icon-silk-delete",
					handler: _p.unsubscribe.createDelegate(_p)
				}
			]
		};	

		Ext.apply(this, {
			listeners: {
				show: {
					buffer: 250,
					fn: function() {
						_p.loadInitData();
					}
				}
			},
			layout: 'border',
			defaults: { border: false },
			items: [
				this.buildMessagePanel({
					defaultMsg: 'User subscription data.'
				}),
				{
					region: 'center',
					layout: 'vbox',
					layoutConfig: {
						align : 'stretch',
						pack  : 'start'
					},
					defaults: {	flex: 1 },
					items : [
						{
							xtype: 'fieldset',
							title: "Price Plan",
							height: 170,
							defaults: { border: false },
							items: pricePlanForm
						}, 
						{
							layout:'hbox',
							border: false,
							bodyStyle: 'padding-top: 10px',
							layoutConfig: {
								align : 'stretch',
								pack  : 'start'
							},				
							items: [
								{	
									title: "Registered Cards",
									xtype: 'fieldset',
									items: cepasGrid,	
									layout: 'fit',
									flex: 1						
								}, {
									width: 10,
									border: false
								}, {
									title: "Vehicle Info",
									xtype: 'fieldset',
									items: vehicleForm,
									layoutConfig: {
										align : 'stretch',
										pack  : 'start'
									},
									layout:'vbox',
									width : 320
								}
							]
						}
					]
				}
			]				
		});

		this.supr().initComponent.call(this);
	}
});

_p = EMobility.CustomerView.UserSubscription.prototype;

_p.stores = {
	PricePlans:
		new Ext.data.JsonStore({
			autoLoad : false,
			fields : ["id", "name", "description"]
		}),
	PaymentMethods : 
		new Ext.data.ArrayStore({
			autoLoad : false,
			fields : ["name"]
		}),
	VehicleTypes : 
		new Ext.data.ArrayStore({
			autoLoad : false,
			fields : ["name"]
		}),
	RFIDCards : 
		new Ext.data.JsonStore({
			autoLoad : false,
			fields : ["cardType", "cardNumber", "created", "description"]
		})
};


_p.loadInitData = function(){
	var _p = this;
	var _s = _p.stores;
	
	_p.showLoadingMessage("Loading user subscription data..");
	
	SubscriptionService.getInitData(_p.userId, {
		callback: function(data){			
//			with(data){
				_s.PricePlans.loadData(data.pricePlans);
//				_s.PaymentMethods.loadData(paymentMethods);
				_p.setUserPricePlan(data.registeredPlan);
//			}
			
			_p.showSuccessMessage("Subscription data has been loaded.");
		},
		exceptionScope: _p
	});
	
	VehicleService.getInitData(_p.userId, {
		callback: function(data){
			with(data){
				_s.VehicleTypes.loadData(vehicleTypes);
				_p.setVehicleType(registeredVehicles[0]);
			}
		},
		exceptionScope: _p
	});
	
	RFIDCardService.getRegisteredCards(_p.userId, {
		callback: function(data){
			_s.RFIDCards.loadData(data);
		},
		exceptionScope: _p
	});
}

_p.removeRFIDCard = function removeRFIDCard(){
	var _p    = this;
	var grid   = Ext.getCmp(_p._ID.RFIDGrid);
	var store  = grid.getStore();
	var record = grid.getSelectionModel().getSelected();

	if(record){
		store.remove(record);
	}
}

_p.refreshRFIDCard=function refreshRFIDCard(){
	var _p = this;
	var _s = _p.stores;
	
	var grid  = Ext.getCmp(_p._ID.RFIDGrid);
	var store = grid.getStore();
	
	grid.stopEditing();
    store.rejectChanges();	
	
	// Show loading
	_p.showLoadingMessage("Reloading RFID cards..");
	
	RFIDCardService.getRegisteredCards(_p.userId, {
		callback: function(data){
			_s.RFIDCards.loadData(data);
			
			_p.showSuccessMessage("RFID cards loaded successfully !");
		},
		exceptionScope: _p
	});
};

_p.saveRFIDCards=function saveRFIDCards(cards){
	var _p   = this;
	var grid  = Ext.getCmp(_p._ID.RFIDGrid);
	var store = grid.getStore();
	
	grid.stopEditing();
	
	var modifiedCards = [];
	Ext.each(store.getRange(), function(item, index, allitems){
		modifiedCards.push(item.data);
	});
	// In case of no modified records , serves as refresh ;)
	
	// Show loading
	_p.showLoadingMessage("Save in progress");
	
	RFIDCardService.registerCards(_p.userId, modifiedCards, {
		callback: function(data){
			store.rejectChanges();
			store.loadData(data);
			
			_p.showSuccessMessage("RFID card details saved successfully");
		},
		exceptionScope: _p
	});
}

_p.setUserPricePlan = function (subscriptionPlan){
	var _p = this;
	var plan = this.registeredPlan = subscriptionPlan  || {};

	plan.pricePlan = plan.pricePlan;
	
	// FIXME: Quick fix to enable Subscription as Billing needs
	if(plan){
//	if(plan && plan.pricePlan){
		var planForm  = Ext.getCmp(_p._ID.PriceplanForm).getForm();
		
		var pricePlansCombo 	= planForm.findField("priceplan");
		var statusInfo 			= Ext.getCmp(_p._ID.PlanStatus);
		var planInfo 			= Ext.getCmp(_p._ID.PlanDetails);
		var status				= plan.status;

		if(pricePlansCombo && plan.pricePlan){
			pricePlansCombo.setValue(plan.pricePlan.id);
		}else{
			pricePlansCombo.setValue(0);   // Select None
			
            var field = Ext.getCmp(_p._ID.PlanDetails);
            var record = _p.stores.PricePlans.getAt(0); // Record at Index 0 is always "None"
            if (field && record) {
                field.update(record.get("description"));
            }
		}
		
		if(statusInfo){
			// TODO: Translation
			statusInfo.update(status.value);
		}
		
		switch(status.id) {
			case "WAITING_FOR_SUBSCRIPTION":
				break;
				
			case "SUBSCRIBED_WITHOUT_SAP_ID":
			case "SUBSCRIBED":
				_p.disableSubscribe(true);
				_p.disableUnsubscribe(false);
				break;
				
			default:
				_p.disableSubscribe(false);
				_p.disableUnsubscribe(true);						
		}
		
		if(planInfo && plan.pricePlan){
			var r = pricePlansCombo.getStore().getById(plan.pricePlan.id);
			if (r) {
				planInfo.update(r.get("description"));	
			}			
		}
	}
};

_p.updateVehicleType = function updateVehicleType(){
	var _p 		 = this;
	
	var form  		 = Ext.getCmp(_p._ID.VehicleForm).getForm();
	
	var vehicleType  = form.findField("vehicleType").getValue();
	var licensePlate = form.findField("licenseplate").getValue();
	
	licensePlate = licensePlate ? licensePlate.trim() : "";
	
	if(vehicleType!="None" && Ext.isEmpty(licensePlate)){
		return;
	}
	
	var vehicle = {
		vehicleType  : vehicleType,
		licensePlate : licensePlate
	};
	
	_p.showLoadingMessage("Updating Vehicle details..");
	
	VehicleService.registerVehicle(_p.userId, vehicle, {
		callback : function (data){
			_p.setVehicleType (data[0]);
			
			_p.showSuccessMessage("Vehicle details updated successfully !");			
		},
		exceptionScope: _p
	})
}

_p.setVehicleType = function setVehicleType(data){
	var _p = this;
	
	if(!data)
		data = {};
	
	var form  		 = Ext.getCmp(_p._ID.VehicleForm).getForm();
	var vehicleType  = form.findField("vehicleType");
	var licensePlate = form.findField("licenseplate")
	var b 			 = data.vehicleType == "None";
	
	vehicleType.setValue(data.vehicleType);
	
	if(!b){
		licensePlate.setValue(data.licensePlate);
		licensePlate.validate();
	}else{
		licensePlate.clearInvalid();
		licensePlate.setValue("");
	}

	licensePlate.setDisabled(b);
};

_p.SUBSCRIBE =  "Thank you for your subscription. <br><br>" +
                "Your account information will be validated and you will receive an Email notification.<br>" +
                "Once your subscription is approved, you are allowed to use the charging station(s). <br><br>" +
                "Your billing will commence after you start using the charging station.";
					
_p.UNSUBSCRIBE ="You will not be able to use the charging station anymore!<br><br>" +
				"Please confirm ?";


_p.subscribe = function subscribe(){	
	var _p = this;
		
	var planId 			= {};
	var planForm  		= Ext.getCmp(_p._ID.PriceplanForm).getForm();
	var pricePlansCombo = planForm.findField("priceplan");
	var existingPlan    = _p.registeredPlan.pricePlan;
	
	planId = pricePlansCombo.getValue();
	
	// EMSG-289 : Commented out the check
	// if(existingPlan.id!=planId){
		 if (planId !== null) {
			// Disable buttons
			_p.disableButtons(true);
			
			if (existingPlan && existingPlan.id && existingPlan.id != null){
				_p.updateSubscription(planId);
			}else{
				var cards = _p.stores.RFIDCards.getCount();
				
				_p.showLoadingMessage("Subscription in progress..");
				
				SubscriptionService.subscribe(_p.userId,planId,{
					callback : function (data){
						_p.setUserPricePlan(data);
						
						_p.showSuccessMessage("Subscription request submitted successfully !");
						
						Ext.Msg.show({
							title: 	 "Subscription",
							msg:   	 _p.SUBSCRIBE,
							buttons: Ext.Msg.OK,
							icon:    Ext.MessageBox.INFO
						});
					},
					exceptionScope: _p
				});			
			}
		} else {
			_p.showErrorMessage("You need to select a subscription plan.");
		}
//	}
};

_p.unsubscribe = function unsubscribe(){
	var _p = this;
	
	// Disable buttons
	_p.disableButtons(true);
	
	Ext.Msg.show({
		title:   "Unsubscribe Plan",
		msg: 	 _p.UNSUBSCRIBE,
		buttons: Ext.Msg.YESNO,
		icon:    Ext.MessageBox.WARNING,
		fn : function(btn, text){
			if(btn=="yes"){
				_p.showLoadingMessage("Unsubscription in progress..");
				
				SubscriptionService.unsubscribe(_p.userId,{
					callback : function (data){
						_p.setUserPricePlan(data)
						
						_p.showSuccessMessage("Unsubscription request submitted successfully !");
					},
					exceptionScope: _p
				});
			}else{
				_p.setUserPricePlan( _p.registeredPlan);
			}
		}
	});
};

_p.updateSubscription = function updateSubscription(planId){
	var _p = this;

	_p.showLoadingMessage("Subscription in progress..");	
	
	SubscriptionService.subscribe(_p.userId, planId, {
		callback : function (data){
			_p.setUserPricePlan(data);

			_p.showSuccessMessage("Subscription change request submitted successfully !");	
						
			Ext.Msg.show({
				title: 	 "Subscription",
				msg:   	 _p.SUBSCRIBE,
				buttons: Ext.Msg.OK,
				icon:    Ext.MessageBox.INFO
			});			
		},
		exceptionScope: _p
	});
};

// dereference global variable
_p = null;

delete _p;

Ext.reg('emo-view-user-subscription', EMobility.CustomerView.UserSubscription);