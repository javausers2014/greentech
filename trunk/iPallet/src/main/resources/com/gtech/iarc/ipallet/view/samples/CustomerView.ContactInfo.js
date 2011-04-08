Ext.ns("EMobility.CustomerView");
EMobility.CustomerView.ContactInfo = Ext.extend(EMobility.MessageView, {
	title: "Contact Information",
	iconCls: 'icon-silk-user_suit',
	
	initId: function() {
		this.supr().initId.call(this);
		
		return Ext.apply(this._ID, {
			FORM 	: Ext.id(),		
			
			MOBILE 	: Ext.id(),
			LANDLINE: Ext.id(),			
			
			STREET 	: Ext.id(),
			UNITNO 	: Ext.id(),
			CITY 	: Ext.id(),
			ZIPCODE	: Ext.id(),
			COUNTRY	: Ext.id(),
						
			// Buttons
			SAVE	: Ext.id()
		});
	},
		
	getUserForm: function(basic) {
		var f = this.findById(this._ID.FORM);
		return basic ? f.getForm() : f;
	},
	
	initView: function() {
		// Update status
		this.getContactInfo();
	},
	
	resetView: function() {
		// Reset form
		var d = this.savedData;
		if (d) {
			this.setFormValues(d);	
		}
	},
	
	getContactInfo: function() {
		var	view = this,
			userId = this.model.loginName;
			
		// Show mask		
		this.showLoadingMessage('Loading in progress..');
		
		CustomerService.getContactInfo(userId, {
			callback: function (data) {
				if (data) {
					if (data) {
						view.showInfoMessage('Contact info loaded successfully.');	
						
						// Set form values
						view.setFormValues(data);
					} 
					else {
						view.showInfoMessage('Contact info is missing. Please fill out the form below.');		
					}
				}
			},
			exceptionScope: view
		});
		
		return userId;
	},
	
	setFormValues: function(data) {
		var address = data.address,	
			form = this.getUserForm(true);
		
		// Save data
		this.savedData = Ext.apply(data, {});
		
		// Set form values
		form.setValues({
			emailAddress		: data.emailAddress,
			mobilePhoneNumber	: data.mobilePhoneNumber,
			landlinePhoneNumber	: data.landlinePhoneNumber,
			faxNumber			: data.faxNumber,
			street				: address.street,
			streetNumber		: address.streetNumber,
			zipCode				: address.zipCode,
			city				: address.city,
			country				: address.country
		});
		
		// Enable buttons
		Ext.getCmp(this._ID.SAVE).setDisabled(false);
	},
		
	submitForm: function() {
		var view = this,
			userId = this.model.loginName,
			form = view.getUserForm(),
			f = form.getForm();
			
		if (f.isDirty()) {
		
			var	v = f.getFieldValues(true),
				form = this.findById(view._ID.FORM),
				data = Ext.apply({}, this.savedData);
			
			var E = Ext.getCmp,
				ID = view._ID,
				address = {
					street			: E(ID.STREET).getValue(),
					streetNumber	: E(ID.UNITNO).getValue(),
					zipCode			: E(ID.ZIPCODE).getValue(),
					city			: E(ID.CITY).getValue(),
					country			: E(ID.COUNTRY).getValue()
				};
			
			Ext.apply(data, {				
				mobilePhoneNumber		: v.mobilePhoneNumber,
				address					: address,
				landlinePhoneNumber		: v.landlinePhoneNumber,
				faxNumber				: v.faxNumber
			});
			
			// Show mask
			this.showLoadingMessage('Save in progress..');
			
			CustomerService.setContactInfo(userId, data, {
				callback: function (data) {
					var name = "<b>" + userId + "</b>",					
						msg;
					
					if (data) {
						msg = "Contact info of customer " + name + " has been saved successfully !";						
						
						// Set values
						view.setFormValues(data);
						
						view.showSuccessMessage(msg);
					} else {
						msg = "Failed to save Contact info of customer " + name;						
						
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
		
		Ext.QuickTips.init();
		
		var view = this,
			ID = this.initId();
		
		Ext.apply(this, {			
			listeners: {
				show: {
					fn: function() {
						// Init
						view.initView();
					},
					buffer: 500
				}
			},
			layout: 'border',
			border: false,			
			defaults: { border: false },
			items: [
				this.buildMessagePanel({
					defaultMsg: 'Please enter contact info in the form below.'
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
						height: 350,
						scope: this,
						api: {				    
							submit:	view.submitForm.createDelegate(view)
						},
						items: [
							{
								xtype:'fieldset',
								title: 'Email & Fax',			            
								defaults: { border: false, layout: 'column' },
								items: [
									{
										defaults: { border: false, layout: 'form' },
										items: [
											{
												columnWidth: .5,
												defaults: { border: false },
												items: {
													xtype : 'compositefield',
													fieldLabel: 'Email',
													anchor: '-20',
													items: [
														{													
															xtype     	: 'textfield',
															name		: 'emailAddress',
															readOnly	: true,
															allowBlank: false,
															flex : 1
														}, {
															xtype : 'button',
															text : 'Edit',
															width : 50,
															handler: function(){
																var parameter = "?userId="+view.getContactInfo();
																var ctx = $webtop.getCustomContext(),
																	url = ctx ? ctx.url.uss.change_email+parameter : false;
																
																if (url) {
																	window.open(url);	
																} else {
																	this.showErrorMessage("Technical Exception: URL to ChangeName page not available");
																}
															}
														}
													]
												}
											}, {
												columnWidth: .5,
												defaults: { border: false },
												items: {
													xtype: 'textfield',
													fieldLabel: 'Fax Number',
													name: 'faxNumber',
													anchor: '100%'
												}
											}	
										]				                
									}
								]
							}, {
								xtype:'fieldset',
								title: 'Phone',
								layout: 'column',
								defaults: { border: false, layout: 'form' },
								items: [
									{
										columnWidth: .5,
										defaults: { border: false },
										items: {
											xtype: 'textfield',
											id: ID.MOBILE,
											fieldLabel: 'Mobile Phone',
											name: 'mobilePhoneNumber',											
											anchor: '-20',
											validator: function(v) {
												var landline = Ext.getCmp(ID.LANDLINE);
												
												if(v === "") {													
													if (landline.getValue() === "") {
														return "Please fill in either Mobile or Landline phone number."
													}
												}
												
												landline.clearInvalid();
												
												return true;											
											}
										}				            	
									}, {				            	
										columnWidth: .5,
										defaults: { border: false },										
										items: {
											xtype: 'textfield',
											id: ID.LANDLINE,
											fieldLabel: 'Landline',		
											name: 'landlinePhoneNumber',
											anchor: '100%',
											validator: function(v) {
												var mobile = Ext.getCmp(ID.MOBILE);
												
												if(v === "") {													
													if (mobile.getValue() === "") {
														return "Please fill in either Mobile or Landline phone number."
													}
												}
												
												mobile.clearInvalid();
												
												return true;											
											}
										}				            	
									}
								]							
							}, {
								xtype:'fieldset',
								title: 'Address',
								defaults: { border: false, layout: 'form' },
								layout: 'column',
								items: {
									columnWidth: .5,
									defaults: { border: false, allowBlank: false },
									items: [							
										{
											xtype     : 'textfield',
											id		  : ID.UNITNO,
											fieldLabel: 'Unit No.',					            
											name	  : 'streetNumber',
											width	  : 60											
										}, 
										{
											xtype     : 'textfield',
											id		  : ID.STREET,
											fieldLabel: 'Street',
											name	  : 'street',
											anchor	  : '-20'
										},										
										{
											xtype     : 'textfield',
											id		  : ID.ZIPCODE,
											fieldLabel: 'Postal Code',					            						            
											name	  : 'zipCode',
											maskRe	  : /[0-9]/,										
											allowBlank: false,
											width     : 60
										},
										{
											xtype     : 'textfield',
											id		  : ID.CITY,
											fieldLabel: 'City',					            
											name	  : 'city',
											anchor	  : '-20',
											allowBlank: false
										},
										{
											xtype     : 'textfield',
											id		  : ID.COUNTRY,
											fieldLabel : 'Country',
											name	  : 'country',
											allowBlank: false,
											anchor	  : '-20'
										}
									]
								}
							}
						],
						listeners: {
							afterrender: function(p) {
								this.mask = new Ext.LoadMask(this.body.dom, {
									msg:"Please wait..."
								});
							}
						},
						buttons: [{
							text: 'Save',
							id: ID.SAVE,	
							iconCls: 'icon-silk-disk_multiple',
							handler: function() {
								var f = view.getUserForm(true);
								
								if (f.isValid()) {
									// Disable button
									this.setDisabled(true);
									
									// Compulsory fields have been completed
									f.submit();
								}
							}
						},{
							text: 'Reset',
							//iconCls : "icon-silk-arrow_refresh",
							handler: function() {
								// Reset form
								this.resetView();
							},
							scope: this
						},
						{
							text: 'Refresh',
							iconCls : "icon-silk-arrow_refresh",
							handler: function() {
								// Reload form
								this.getContactInfo();
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

Ext.reg('emo-view-contact-info', EMobility.CustomerView.ContactInfo);