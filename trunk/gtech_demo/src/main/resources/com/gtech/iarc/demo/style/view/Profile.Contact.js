Ext.ns("EMobility.Intuity");

EMobility.Intuity.Contact = Ext.extend(EMobility.Intuity.SubView, {
	title: "<@i18nText key='profile.contact.title' />",
	
	initId: function() {
		this.supr().initId.call(this);
		
		return Ext.apply(this._ID, {
			FORM 		: Ext.id(),
			MOBILE		: Ext.id(),
			LANDLINE	: Ext.id(),			

			// Buttons
			SAVE	: Ext.id(),
			RESET	: Ext.id()
		});
	},	
	
	initView: function() {
		this.getContactInfo();
	},

	getContactInfo: function() {
		var	view = this;

		// Show mask		
		this.showLoadingMessage("<@i18nText key='common.loadingmsg' />");

		PersonalInfoService.getContactInfo({
			callback: function (data) {
				if (data) {
                    // EMSG-353 - No Success Message
                    view.hideMessage();	
					
					// Set form values
					view.setFormValues(data);
				} 
				else {
					view.showInfoMessage("<@i18nText key='dp.profile.contact.infomsg.missingcontactinfo' />");		
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
	
	viewMsg: "<@i18nText key='dp.profile.contact.viewmsg' />",
	
	submitForm: function() {
		var	view = this,
			f = this.getUserForm(true),
			data = f.getFieldValues(false);	
		
		if (f.isDirty()) {
			
			// Show mask
			this.showLoadingMessage("<@i18nText key='dp.profile.contact.loadingmsg.submit' />");
			
			PersonalInfoService.setContactInfo(data, {
				callback: function (data) {
					var msg;
					
					if (data) {
						msg = "<@i18nText key='dp.profile.contact.successmsg.submit' />";						
						
						// Set values
						view.setFormValues(data);
						
						view.showSuccessMessage(msg);
					} else {
						msg = "<@i18nText key='dp.profile.contact.errormsg.submit' />";						
						
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
			title: "<@i18nText key='profile.contact.title' />",
			listeners: {
				expand: {
					fn: this.initView.createDelegate(this),
					single: true,
					buffer: 500
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
				height: 330,						
				items: [
					// Message Panel
					this.buildMessagePanel({
						defaultMsg: this.viewMsg
					}),
					// General
					this.buildSection("<@i18nText key='profile.contact.address.title' />", 170,
						[
							{
								layout: 'column',
								defaults: { border: false, layout: 'form', labelSeparator: '' },
								items: [
									{
										columnWidth: .5,
										defaults: { xtype : 'textfield', allowBlank	: false, anchor	: '-20' },
										items: [
											{
												id		  	: ID.STREET,
												fieldLabel	: "<@i18nText key='profile.contact.address.street.label' />",
												name	  	: 'street',
												tabIndex	: 50
											},
											{
												id		  	: ID.CITY,
												fieldLabel 	: "<@i18nText key='profile.contact.address.city.label' />",
												name	  	: 'city',												
												tabIndex	: 52

											},
											{
												id		  	: ID.COUNTRY,
												fieldLabel 	: "<@i18nText key='profile.contact.address.country.label' />",
												name	  	: 'country',
												tabIndex	: 54

											}
										]				            	
									}, 
									{				            	
										columnWidth: .5,
										defaults: { xtype : 'textfield', allowBlank	: false, width : 80 },
										items: [
											{
												id		  	: ID.HOUSENUMBER,
												fieldLabel	: "<@i18nText key='profile.contact.address.streetnumber.label' />",
												name	  	: 'streetNumber',
												tabIndex	: 51
											},
											{
												id		  	: ID.ZIPCODE,
												fieldLabel	: "<@i18nText key='profile.contact.address.zipcode.label' />",
												name	  	: 'zipCode',
												maskRe	  	: /[0-9]/,
												tabIndex	: 53
											}
										]																            	
									}
								]
							}
						]
					),
					
					// Contact Numbers
					this.buildSection("<@i18nText key='profile.contact.contactnumbers.title' />", 70,
						[
							{
								layout:'hbox',
								height: 70,
								layoutConfig: { align : 'stretch', pack  : 'start' },
								defaults: { border: false, layout: 'form', labelSeparator: '', flex: 1 },
								items: [
									{
										items: {
											xtype: 'textfield',
											id: ID.MOBILE,
											fieldLabel: "<@i18nText key='profile.contact.contactnumbers.mobile.label' />",
											tabIndex: 55,
											name: 'mobilePhoneNumber',
											validator: function(v) {
												var landline = Ext.getCmp(ID.LANDLINE);
												
												if(v === "") {													
													if (landline.getValue() === "") {
														return "<@i18nText key='dp.profile.contact.contactnumbers.validationmsg' />"
													}
												}
												
												landline.clearInvalid();
												
												return true;											
											},
											anchor: '-20'
										}			            	
									}, 
									{
										items: {
											xtype: 'textfield',
											id: ID.LANDLINE,
											tabIndex: 56,
											fieldLabel: "<@i18nText key='profile.contact.contactnumbers.landline.label' />",		
											name: 'landlinePhoneNumber',						            		
											anchor: '-20',
											validator: function(v) {
												var mobile = Ext.getCmp(ID.MOBILE);
												
												if(v === "") {													
													if (mobile.getValue() === "") {
														return "<@i18nText key='dp.profile.contact.contactnumbers.validationmsg' />"
													}
												}
												
												mobile.clearInvalid();
												
												return true;											
											}
										}				            	
									},
									{
										items: {
											xtype		: 'textfield',
											tabIndex	: 57,
											fieldLabel	: "<@i18nText key='profile.contact.contactnumbers.fax.label' />",
											name		: 'faxNumber',
											anchor		: '100%'
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
							}
						]
					)
				]
			}
		});
		
		EMobility.Intuity.Contact.superclass.initComponent.call(this);		
	}
});

Ext.reg('profile-contact', EMobility.Intuity.Contact);