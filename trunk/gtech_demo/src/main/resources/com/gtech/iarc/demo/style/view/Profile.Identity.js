Ext.ns("EMobility.Intuity");

EMobility.Intuity.Identity = Ext.extend(EMobility.Intuity.SubView, {
	
	initId: function() {
		this.supr().initId.call(this);
		
		return Ext.apply(this._ID, {
			FORM 	: Ext.id(),

			// Buttons
			SAVE	: Ext.id(),
			RESET	: Ext.id(),
			REFRESH : Ext.id()
		});
	},
	
	initView: function() {
		this.getAccountInfo();
	},
		
	getAccountInfo: function() {
		var	view = this;

		// Show mask		
		this.showLoadingMessage("<@i18nText key='common.loadingmsg' />");

		PersonalInfoService.getAccountInfo({
			callback: function (data) {
				if (data) {
					// Set form values
					view.setFormValues(data);
					
					// EMSG-353 - No Success Message
                    view.hideMessage();					
				} 
				else {
					view.showInfoMessage("<@i18nText key='dp.profile.identity.infomsg.missingprofileinfo' />");		
				}
			},
			exceptionScope: view
		});
	},
	
	disableButtons: function(disabled) {
		var ID = this._ID,
			E = Ext.getCmp; 
		
		E(ID.SAVE).setDisabled(disabled);
		E(ID.RESET).setDisabled(disabled);
	},
	
	isDirty: function() {
		var form = this.getUserForm(true);
		return form.isDirty();
	},
	
	setFormValues: function(data) {
		
		var form = this.getUserForm(true);				
		
		// Save data
		this.savedData = Ext.apply(data, {});
				
		// Set form values
		form.setValues(data);
		form.reset();
		
		// Enable buttons
		this.disableButtons(false);
	},
	
	viewMsg: "<@i18nText key='dp.profile.identity.viewmsg' />",
	
	submitForm: function() {
		var	view = this,
			f = this.getUserForm(true),
			data = f.getFieldValues(false);
		
		if (f.isDirty()) {
			
			// Show mask
			this.showLoadingMessage("<@i18nText key='dp.profile.identity.loadingmsg.submit' />");
			
			PersonalInfoService.setAccountInfo(data, {
				callback: function (data) {
					var msg;
					
					if (data) {
						msg = "<@i18nText key='dp.profile.identity.successmsg.submit' />";						
						
						// Set values
						view.setFormValues(data);
						
						view.showSuccessMessage(msg);
					}
					else {
						msg = "<@i18nText key='dp.profile.identity.errormsg.submit' />";						
						
						// Reset
						view.resetView();
						
						view.showErrorMessage(msg);
					}						
				},
				exceptionScope: view
			});
		}	
	},
	
	initComponent: function() {
		var view = this,
			ID = this.initId();
		
		Ext.apply(this, {
			title: "<@i18nText key='profile.identity.title' />",			
			listeners: {
				afterrender: {
					fn: this.initView.createDelegate(this),
					single: true,
					buffer: 1000
				}
			},
			items: {				
				xtype: 'form',
				id: ID.FORM,
				trackResetOnLoad: true,
				api: { submit:	this.submitForm.createDelegate(this) },
				labelAlign: 'top',
				labelSeparator: '',
				layout:'vbox',
				layoutConfig: { align : 'stretch', pack  : 'start' },
				defaults: { border: false },
				height: 405,
				items: [
					// Message Panel
					this.buildMessagePanel({
						defaultMsg: this.viewMsg
					}),
					// General
					this.buildSection("<@i18nText key='profile.identity.general.title' />", 170,
						[
							{
								height: 50,
								layout: 'column',
								defaults: { border: false, layout: 'form', labelSeparator: '' },					        		
								items: [
									{
										columnWidth: .5,
										items: {
											xtype: 'textfield',
											allowBlank: false,
											disabled: true,
											fieldLabel: "<@i18nText key='profile.identity.general.userid.label' />",
											name: 'userId',
											anchor: '-20'
										}				            	
									}, {				            	
										columnWidth: .5,										
										items: {
											xtype: 'textfield',
											allowBlank: false,									            		
											fieldLabel: "<@i18nText key='profile.identity.general.personalIdentificationNumber.label' />",
											name: 'personalIdentificationNumber',
											anchor: '100%'
										}				            	
									}
								]
							},
							{
								height: 50,
								layout: 'form',					       
								labelSeparator: '',
								flex: 1,
								items: {
									xtype     	: 'textfield',									
									anchor		: '100%',						                        
									fieldLabel	: "<@i18nText key='profile.identity.general.fullname.label' />",						                         
									name		: 'fullName',
									disabled	: true,
									readOnly  	: true
								}
							},
							{
								height: 50,
								layout: 'form',					       
								labelSeparator: '',
								flex: 1,
								items: {
									xtype     	: 'textfield',									
									anchor		: '100%',						                        
									fieldLabel	: "<@i18nText key='profile.identity.general.emailaddress.label' />",				                         
									name		: 'emailAddress',
									disabled	: true,
									readOnly  	: true
								}								
							}									
						]
					),
					
					// Company
					this.buildSection("<@i18nText key='profile.identity.company.title' />", 70,
						[
							{
								layout: 'column',
								defaults: { border: false, layout: 'form', labelSeparator: '', columnWidth: .5 },
								items: [
									{
										items: {
											xtype: 'textfield',
											fieldLabel: "<@i18nText key='profile.identity.company.companyname.label' />",
											name: 'companyName',
											allowBlank: false,
											anchor: '-20'
										}				            	
									}, 
									{
										items: {
											xtype: 'textfield',
											fieldLabel: "<@i18nText key='profile.identity.company.companyregistrationid.label' />",
											name: 'companyRegistrationId',
											allowBlank: false,
											anchor: '100%'
										}				            	
									}
								]
							}
						]
					),
					
					// Security
					this.buildSection("<@i18nText key='profile.identity.security.title' />", 70,
						[
							{
								layout: 'column',
								defaults: { border: false, layout: 'form', labelSeparator: '', columnWidth: .5 },	
								items: [
									{
										items: {
											xtype: 'textfield',
											allowBlank: false,
											fieldLabel: "<@i18nText key='profile.identity.security.securityquestion.label' />",
											name: 'securityQuestion',
											anchor: '-20'
										}				            	
									}, 
									{
										items: {
											xtype: 'textfield',
											allowBlank: false,
											fieldLabel: "<@i18nText key='profile.identity.security.securityanswer.label' />",
											name: 'securityAnswer',
											anchor: '100%'
										}				            	
									}
								]
							}
						]
					),
					
					// Buttons
					this.buildButtons(
						[
							{
								text: "<@i18nText key='common.button.save' />",
								id: ID.SAVE,
								handler: function() {									
									var f = this.getUserForm(true);
									
									if (f.isValid()) {
										// Disable button
										this.disableButtons(true);
										
										// Compulsory fields have been completed
										f.submit();
									}
								},
								scope: this
							},
							{
								text: "<@i18nText key='common.button.reset' />",
								id: ID.RESET,
								handler: this.resetView.createDelegate(this)								
							},
							{
								text: "<@i18nText key='common.button.refresh' />",
								id: ID.REFRESH,
								handler: function() {
									this.getAccountInfo();									
								},
								scope: this
							}
						]
					)
				]
			}
		});
		
		EMobility.Intuity.Identity.superclass.initComponent.call(this);
	}
});

Ext.reg('profile-identity', EMobility.Intuity.Identity);