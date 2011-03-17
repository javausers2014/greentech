Ext.ns("EMobility.Intuity");

EMobility.Intuity.Vehicle = Ext.extend(EMobility.Intuity.SubscriptionForm, {
	initId: function() {
		return this._ID = {
			FORM	: Ext.id(),
			
			// Buttons
			SAVE	: Ext.id()
		};
	},

	initView: function() {
		this.getVehicleType();
	},
	
	resetView: function() {
		// Default message
		this.showInfoMessage(this.viewMsg);
		
		// Enable buttons
		this.disableButtons(false);
	},

	store: new Ext.data.ArrayStore({
		autoLoad : false,
		fields : ["name"]
	}),
	
	getVehicleType: function() {
		var	view = this;
		
		// Loading message
		this.showLoadingMessage("<@i18nText key='dp.profile.subscription.vehicle.loadingmsg' />");
		
		VehicleService.getInitData({
			callback: function(data){
				view.store.loadData(data.vehicleTypes);
				view.setFormValues(data.registeredVehicles[0]);
				
                // EMSG-353 - No Success Message
                this._view.hideMessage();
            },
            scope: view,
			exceptionScope: this
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
		
		view.showLoadingMessage("<@i18nText key='dp.profile.subscription.vehicle.updatemsg' />");
		
		VehicleService.registerVehicle(data, {
			callback : function (data) {
				view.setFormValues (data[0]);
				
				// Success message
				view.showSuccessMessage("<@i18nText key='dp.profile.subscription.vehicle.successmsg.save' />");
			},
			exceptionScope: view
		});
	},
	
	viewMsg: "<@i18nText key='dp.profile.subscription.vehicle.viewmsg' />",
	
	disableButtons : function(disabled) {
		Ext.getCmp(this._ID.SAVE).setDisabled(disabled);
	},
	
	// TODO: Fix logic
	setFormValues: function (data){
		var view = this,		
			data = data || {};		
		
		// Save data
		this.savedData = Ext.apply(data, {});
				
		var form  		 = this.getForm();
		var vehicleType  = form.findField("vehicleType");
		var licensePlate = form.findField("licensePlate");
		var plateDisplay = "";
		var b 			 = data.vehicleType == "None";
		
		// Set form values
		// vehicleType.setValue(data.vehicleType);
		
		if (!b) {
			plateDisplay = data.licensePlate;
		} else {
			licensePlate.clearInvalid();			
		}
		
		form.setValues({
			"vehicleType" 	: data.vehicleType,
			"licensePlate"	: plateDisplay
		});
	
		licensePlate.setDisabled(b);
		
		// Enable buttons
		this.disableButtons(false);
		
		form.reset();
		licensePlate.validate();
		
	},	
	
	submitForm: function() {
		var view = this,			
			f = this.getForm(),
			data = f.getFieldValues(false),
			vehicleType  = data.vehicleType,
			licensePlate = data.licensePlate;
		
		if (f.isDirty()) {
			//////////			
			licensePlate = licensePlate ? licensePlate.trim() : "";
		
			if (vehicleType != "None" && Ext.isEmpty(licensePlate)) {
				return;
			}
			
			data.licensePlate = licensePlate;
			
			// Show mask
			view.showLoadingMessage("<@i18nText key='dp.profile.subscription.vehicle.updatemsg' />");

			VehicleService.registerVehicle(data, {
				callback : function (data) {
					if (data) {
						view.setFormValues (data[0]);
					
						// Success message
						view.showSuccessMessage("<@i18nText key='dp.profile.subscription.vehicle.successmsg.save' />");	
					} 
					else {						
						// Reset
						view.resetView();
						
						view.showErrorMessage("<@i18nText key='dp.profile.subscription.vehicle.errormsg.save' />");
					}
				},
				exceptionScope: view
			});			
		}	
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
			layout: 'column',
			defaults: { border: false, layout: 'form', labelSeparator: '', columnWidth: .5 },
			buttons: [
				{
					text 	: "<@i18nText key='common.button.save'/>",
					id		: ID.SAVE,
					handler: function() {
						var f = this.getForm();
						
						if (f.isValid()) {
							// Disable button
							this.disableButtons(true);
							
							// Compulsory fields have been completed
							this.submitForm();
						}
					},
					scope: this
				}
			],
			items: [
				{
					items: {
						xtype: 'select',
						name: 'vehicleType',
						mode: 'local',
						fieldLabel : "<@i18nText key='vehicle.vehicletype.label'/>",
						anchor : "-20",
						valueField: "name",
						displayField: "name",
						emptyText: "<@i18nText key='vehicle.vehicletype.emptytext'/>",
						store: this.store,
						listeners: {
							select : function(combo, record, index) {
								var licensePlate = view.getForm().findField("licensePlate");
								var b = record.get("name") == "None";
			
								licensePlate.clearInvalid();
								licensePlate.setDisabled(b);
								
								if (!b) {
									licensePlate.validate();
								}
							}
						}
					}
				},
				{
					items: {
						xtype: "textfield",
						name: "licensePlate",						
						anchor : "100%",
						fieldLabel : "<@i18nText key='vehicle.licenseplate.label'/>",
						emptyText: "<@i18nText key='vehicle.licenseplate.emptytext'/>",
						allowBlank: false,
						maxLength: 50
					}
				}
			]
		});		
		
		EMobility.Intuity.Vehicle.superclass.initComponent.call(this);
	}
});

Ext.reg('profile-subscription-vehicle', EMobility.Intuity.Vehicle);