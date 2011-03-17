Ext.ns("EMobility.Intuity");

EMobility.Intuity.PricePlan = Ext.extend(EMobility.Intuity.SubscriptionForm, {
	initId: function() {
		return this._ID = {
			FORM	: Ext.id(),
			
			PLANS	: Ext.id(),
			STATUS	: Ext.id(),
			DETAILS	: Ext.id(),
			
			// Buttons
			SUBSCRIBE	: Ext.id(),
			UNSUBSCRIBE	: Ext.id(),
			CHECK : Ext.id()
		};
	},
	
	MSG_SUBSCRIBE :  	"Thank you for your subscription. <br><br>" +
						"Your account information will be validated.<br>" +
						"As soon as your subscription is approved, you will receive an Email notification <br>" +
						"and you are allowed to use the charging station(s).<br><br>" +						
						"Your billing will commence after you start using the charging station.",
						
	MSG_UNSUBSCRIBE	:	"You will not be able to use the charging station anymore!<br><br>" +
						"Please confirm ?",
	
	termsConditions : {
		prefix: 'I have read the <a href=',
		suffix: ' target="_blank " >Terms and Conditions</a> and agree to it.',		
		url: "http://www.bosch.sg.com"		
	},
	
	initView: function() {
		this.getPricePlan();
	},

	store: new Ext.data.JsonStore({
				autoLoad : false,
				fields : ["id", "name", "description"]
			}),
	
	getPricePlan: function() {
		var	view = this;
			
		// Success message
		this.showLoadingMessage("<@i18nText key='dp.profile.subscription.priceplan.loadingmsg'/>");
		
		SubscriptionService.getInitData({
			callback: function(data){
				view.store.loadData(data.pricePlans);
				view.setFormValues(data.registeredPlan);

				// EMSG-353 - No Success Message
				this._view.hideMessage();
				
			},
			scope: view,
			exceptionScope: view
		});
	},
	
	updateVehicleType: function() {
		var view = this,			
			data = this.getForm().getFieldValues(false);
			vehicleType  = data.vehicleType,
			licensePlate = data.licensePlate;
		
		licensePlate = licensePlate ? licensePlate.trim() : "";
		
		if (vehicleType != "None" && Ext.isEmpty(licensePlate)) {
			return;
		}
		
		data.licensePlate = licensePlate;		
		
		view.showLoadingMessage("<@i18nText key='dp.profile.subscription.vehicle.updatemsg'/>");
		
		VehicleService.registerVehicle(data, {
			callback : function (data) {				
				view.setFormValues (data[0]);
				
				// Success message
				dp.profile.subscription.vehicle.successmsg.save
				view.showSuccessMessage("<@i18nText key='dp.profile.subscription.vehicle.successmsg.save'/>");
			},
			exceptionScope: view
		});
	},
	
	updateTermsConditions : function() {
		var view = this;		
		var checkbox = Ext.getCmp(this._ID.CHECK);
		var combo = Ext.getCmp(this._ID.PLANS);
		if (combo) {
			var planType = combo.getValue();	
			var ctx = $webtop.getCustomContext();
			if(checkbox.rendered){				
				if (planType == 0 ) {
					view.termsConditions.url = ctx.url.terms_conditions.defaultLink;					
					checkbox.setDisabled(true);
				} else if ( planType==1 ) {						
					view.termsConditions.url = ctx.url.terms_conditions.tides;					
					checkbox.setDisabled(false);
				} else if ( planType== 2 ) {					
					view.termsConditions.url = ctx.url.terms_conditions.greencar;					
					checkbox.setDisabled(false);
				} else {					
					view.termsConditions.url = ctx.url.terms_conditions.defaultLink;
					checkbox.setDisabled(false);
				}				
				checkboxLabel = view.termsConditions.prefix + view.termsConditions.url + view.termsConditions.suffix;
				checkbox.wrap.child('.x-form-cb-label').update(checkboxLabel);
			}
		}
	}, 
	
	viewMsg: "<@i18nText key='dp.profile.subscription.priceplan.viewmsg'/>",
	
	disableSubscribe: function(disabled) {
		Ext.getCmp(this._ID.SUBSCRIBE).setDisabled(disabled);
		Ext.getCmp(this._ID.PLANS).setDisabled(disabled);	
		Ext.getCmp(this._ID.CHECK).setValue(disabled);
	},
	
	disableUnsubscribe: function(disabled) {
		Ext.getCmp(this._ID.UNSUBSCRIBE).setDisabled(disabled);		
	},
	
	disableButtons: function(disabled) {
		this.disableSubscribe(disabled);
		this.disableUnsubscribe(disabled);
	},
	
	setFormValues : function (data){
		var view = this,
			f = this.getForm(),
			ID = this._ID,
			data = this.savedData = data || {};

		data.pricePlan = data.pricePlan;

		// FIXME: Quick fix to enable Subscription as Billing needs
		if (data) {
			var pricePlansCombo = f.findField("priceplan"),
				statusInfo 		= f.findField("status"),
				planInfo 		= f.findField("details");				

			if (pricePlansCombo && data.pricePlan){
				pricePlansCombo.setValue(data.pricePlan.id);
			}
			else {
				pricePlansCombo.setValue(0);   // Select None
				
				var record = view.store.getAt(0); // Record at Index 0 is always "None"
				if (planInfo && record) {
					planInfo.setValue(record.get("description"));
				}
			}
			
			if(data.status.id == "SUBSCRIBED_WITHOUT_SAP_ID") {
				data.status.value = "Subscribed";
			}				
			
			if (statusInfo){
				// Handle translation # EMSG-289
				statusInfo.setValue(data.status.value);
			}
			
			switch (data.status.id) {
				case "WAITING_FOR_SUBSCRIPTION":
					Ext.getCmp(this._ID.CHECK).setValue(true).setDisabled(true);
					break;
					
				case "SUBSCRIBED_WITHOUT_SAP_ID":
				case "SUBSCRIBED":
					view.disableSubscribe(true);
					view.disableUnsubscribe(false);
					break;
					
				default:
					view.disableSubscribe(false);
					view.disableUnsubscribe(true);						
			}
			
			if (planInfo && data.pricePlan) {
				var r = view.store.getById(data.pricePlan.id);
				planInfo.setValue(r.get("description"));
			}
			
			view.updateTermsConditions();
			
			// Show status
//			view.showInfoMessage(this.viewMsg);
		}
	},	
	
	subscribe : function () {
		var view = this,
			termsConditions = this.getForm().findField("termsConditions").getValue(),
			planId 			= this.getForm().findField("priceplan").getValue();
		
		if (termsConditions) {
			if (planId !== null) {
				// Disable buttons
				view.disableButtons(true);
				
				view.showLoadingMessage("<@i18nText key='dp.profile.subscription.priceplan.progressmsg'/>");
				
				SubscriptionService.subscribe(planId, {
					callback : function (data) {
						view.setFormValues(data);
						
						view.showSuccessMessage("<@i18nText key='dp.profile.subscription.priceplan.successmsg.subscribe'/>");
						
						Ext.Msg.show({
							title: 	 "<@i18nText key='dp.profile.subscription.priceplan.alert.subscribe.title'/>",
							msg:   	 view.MSG_SUBSCRIBE,
							buttons: Ext.Msg.OK,
							icon:    Ext.MessageBox.INFO
						});
					},
					exceptionScope: view
				});			
			} else {
				view.showErrorMessage("<@i18nText key='dp.profile.subscription.priceplan.errormsg.noplanselected'/>");
			}
		} else {
			view.showErrorMessage("<@i18nText key='dp.profile.subscription.priceplan.errormsg.termsconditions'/>");
		}
		
	},
	
	unsubscribe : function (){
		var view = this;
		
		// Disable buttons
		view.disableButtons(true);
		
		Ext.Msg.show({
			title:   "<@i18nText key='dp.profile.subscription.priceplan.unsubscribe.confirmation.title'/>",
			msg: 	 view.MSG_UNSUBSCRIBE,
			buttons: Ext.Msg.YESNO,
			icon:    Ext.MessageBox.WARNING,
			fn : function(btn, text) {
				if (btn=="yes") {					
					view.showLoadingMessage("<@i18nText key='dp.profile.subscription.priceplan.unsubscribe.loadingmsg'/>");
					
					SubscriptionService.unsubscribe({
						callback : function (data){
							view.setFormValues(data)
							
							view.showSuccessMessage("<@i18nText key='dp.profile.subscription.priceplan.successmsg.unsubscribe'/>");
						},
						exceptionScope: view
					});
				} 
				else {
					view.setFormValues(view.savedData);
				}
			}
		});
	},	

	initComponent: function() {
		
		var view = this,
			p = this._view,
			ID = this.initId();
			
		Ext.apply(this, {			
			
			id: ID.FORM,
			showLoadingMessage	: p.showLoadingMessage.createDelegate(p),
			showSuccessMessage	: p.showSuccessMessage.createDelegate(p),
			showInfoMessage		: p.showInfoMessage.createDelegate(p),
			showErrorMessage	: p.showErrorMessage.createDelegate(p),			
			layout: 'vbox',
			layoutConfig: {
				align : 'stretch',
				pack  : 'start'
			},			
			buttons: [
				{
					text : "<@i18nText key='dp.priceplan.button.subscribe'/>",
					width: 90,
					disabled: true,
					id : ID.SUBSCRIBE,
					handler: this.subscribe.createDelegate(this)
				}, 
				{
					text : "<@i18nText key='dp.priceplan.button.unsubscribe'/>",
					width: 90,
					disabled: true,
					id : ID.UNSUBSCRIBE,
					handler: this.unsubscribe.createDelegate(this)
				}
			],
			items: [
		        {
		        	height: 100,
		        	layout: 'column',
		        	defaults: { border: false, layout: 'form', labelSeparator: '' },
		        	items: [
	    				{
	    					columnWidth: .5,
	    					items: [
	    						{
	    							xtype: 'textfield',													
	    							readOnly: true,
	    							disabled: true,
	    							id: ID.STATUS,
	    							name: 'status',
	    							fieldLabel: "<@i18nText key='priceplan.status.label'/>",													
	    							anchor: '-20'
	    						},
	    						{
	    							xtype: 'select',
	    							id: ID.PLANS,
	    							name: 'priceplan',
	    							fieldLabel: "<@i18nText key='priceplan.label'/>",
	    							anchor: '-20',							
	    							mode: 'local',
	    							valueField: "id",
	    							displayField: "name",
	    							store: this.store,
	    							disabled: true,
	    							listeners : {
	    								select : function(combo, record, index) {	
	    									var c = Ext.getCmp(view._ID.DETAILS);
	    									if (c) {
	    										c.setValue(record.get("description"));
	    									}	    									
	    									view.updateTermsConditions();
    									}
    									
	    							}
	    						}
	    					]												
	    				},
	    				{
	    					columnWidth: .5,
	    					items: [{
	    						xtype: 'textarea',
	    						height: 70,
	    						name: 'details',
	    						readOnly: true,
	    						disabled:true,
	    						id: ID.DETAILS,
	    						fieldLabel: "<@i18nText key='priceplan.details.label'/>",													
	    						anchor: '100%'
	    					}
	    					]
	    				}				
	    			]
		        },
		    	{
			    	height : 50,
			    	anchor: '100%',
			    	xtype:'checkbox',
			    	name:'termsConditions',	
			    	id: ID.CHECK,
			        boxLabel: view.termsConditions.prefix + view.termsConditions.url + view.termsConditions.suffix
			    }

			]
			
		});		
		
		EMobility.Intuity.Vehicle.superclass.initComponent.call(this);
	}
});

Ext.reg('profile-subscription-plan', EMobility.Intuity.PricePlan);
