Ext.ns("EMobility.CustomerView");
EMobility.CustomerView.SAP = Ext.extend(EMobility.MessageView, {
	title: "SAP",
	iconCls: 'icon-silk-application_form',
	
	initId: function() {
		this.supr().initId.call(this);
		
		return Ext.apply(this._ID, {
			FORM 	: Ext.id(),
			SAVE 	: Ext.id()
		});
	},
	
	getUserForm: function() {
		return this.findById(this._ID.FORM).getForm();
	},
	
	disableButtons: function(disabled) {
		Ext.getCmp(this._ID.SAVE).setDisabled(disabled);
	},
	
	initView: function() {
		// Update status
		this.getSAP();
	},
	
	resetView: function() {
		// Reset form
		var d = this.savedData;
		if (d) {
			this.setFormValues(d);	
		}
	},
		
	setFormValues: function(data) {
		var form = this.getUserForm(true);
		
		// Save data
		this.savedData = Ext.apply(data, {});
		
		// Set form values
		form.setValues(data);
		
		// Enable buttons
		this.disableButtons(false);		
	},
			
	getUserForm: function(basic) {
		var f = this.findById(this._ID.FORM);
		return basic ? f.getForm() : f;
	},
	
	getSAP: function() {
		var	view = this,			
			userId = this.model.loginName;
			
		// Show mask		
		this.showLoadingMessage('Loading in progress..');
		
		BillsService.getSAP(userId, {
			callback: function (data) {
				if (data && data.sapUid) {
					view.showInfoMessage('SAP info loaded successfully.');
				}else{
					view.showInfoMessage('SAP info is missing. Please fill out the form below.');
				}
				
				view.setFormValues(data);
			},
			exceptionScope: view
		});
	},	
		
	setSAP: function() {
		var	view = this,
			f = this.getUserForm(true),
			data = f.getFieldValues(true),
			userId = this.model.loginName;
			
		// Show mask		
		this.showLoadingMessage('Save in progress..');
		
		BillsService.setSAP(userId, data, {
			callback: function (data) {
				var name = "<b>" + userId + "</b>",					
					msg;
				
				if (data) {
					msg = "SAP info of customer " + name + " has been saved successfully !";						
					
					// Set values
					view.setFormValues(data);
					
					view.showSuccessMessage(msg);
				} else {
					msg = "Failed to save SAP info of customer " + name + ". Message: " + success;						
					
					// Reset
					view.resetView();
					
					view.showErrorMessage(msg);
				}						
			},
			exceptionScope: view
		});
	},	
		
	initComponent: function() {
		
		Ext.QuickTips.init();
		
		var view = this,
			ID = this.initId();
		
		Ext.apply(this, {
			listeners: {
				show: {
					buffer: 500,
					fn: function() {
						this.initView();
					},
					scope: this
				}
			},
			layout: 'border',
			border: false,			
			defaults: { border: false },
			items: [
				this.buildMessagePanel({
					defaultMsg: 'SAP ID is used for billing process. Please verify and enter user SAP information below.'
				}),				
				{
					region: 'center',
					layout: 'vbox',
					layoutConfig: {
						align : 'stretch',
						pack  : 'start'
					},
					defaults: { border: false },
					items: {
						id: ID.FORM,
						xtype: 'form',						
						height: 100,
						scope: this,				
						labelWidth: 130,				
						defaults: { xtype: 'textfield', anchor: '100%' },
						items: [
							{
								fieldLabel: 'SAP ID',
								name: 'sapUid'
							}, {
								fieldLabel: 'Revenue Account',
								name: 'revenueAccount'
							}
						],					
						buttons: [
						{
							text: 'Save',
							id: ID.SAVE,
							iconCls: 'icon-silk-disk_multiple',
							disabled: true,
							handler: function() {
								this.setDisabled(true);
								
								// Approve
								view.setSAP();
							}
						},{
							text: 'Reset',
							iconCls : "icon-silk-arrow_refresh",
							handler: function() {
								this.resetView();
							},
							scope: this
						}]	
					}
				}
			]
		});
		
		this.supr().initComponent.call(this);
	}
});

Ext.reg('emo-view-sap', EMobility.CustomerView.SAP);