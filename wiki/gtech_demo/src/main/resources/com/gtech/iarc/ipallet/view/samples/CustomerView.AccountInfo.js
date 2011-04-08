Ext.ns("EMobility.CustomerView");

EMobility.CustomerView.AccountInfo = Ext.extend(EMobility.MessageView, {	
	title: "Identity",		
	iconCls: 'icon-silk-vcard',
	
	initId: function() {
		this.supr().initId.call(this);
		
		return Ext.apply(this._ID, {
			FORM 	: Ext.id(),
						
			// Buttons
			SAVE	: Ext.id(),
			RESEND  : Ext.id()  
		});
	},
	
	getAccountInfo: function() {
		var	view = this,
			userId = this.model.loginName;
		var status;
		
		Ext.getCmp(this._ID.RESEND).setDisabled(true);		
		// Show mask		
		this.showLoadingMessage('Loading in progress..');
		
		CustomerService.getAccountInfo(userId, {
			callback: function (data) {
				if (data) {
					if (data) {
						view.showInfoMessage('Account info loaded successfully.');
												
						CustomerService.getContractStatus(userId, {
							callback: function (details) {
								
								if (details) {
								
									//passing the status of the current user to disable resend activation mail.
									if(details.status!=null){
										status=details.status.value;
										view.statuscheck(status);	
									}
									else
										{
										view.statuscheck(null);	
										}
																	
								}
							},
							exceptionScope: view
						});
						
						// Set form values
						view.setFormValues(data);
						
					} 
					else {
						view.showInfoMessage('Account info is missing. Please fill out the form below.');		
					}
				}
			},
			exceptionScope: view
		});
		
		
		return userId;
	},
		
	statuscheck: function(status){
		if(status==null){
			Ext.getCmp(this._ID.RESEND).setDisabled(false);	
		}
		
		else{
			Ext.getCmp(this._ID.RESEND).setDisabled(true);	
		}
			
	},
	
	getUserForm: function(basic) {
		var f = this.findById(this._ID.FORM);
		return basic ? f.getForm() : f;
	},
	
	initView: function() {
		// Update status
		this.getAccountInfo();
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
		Ext.getCmp(this._ID.SAVE).setDisabled(false);
	},
		
	submitForm: function() {
		var	view = this,
			f = this.getUserForm(true),
			data = f.getFieldValues(true),
			userId = this.model.loginName;
		
		if (f.isDirty()) {			
			
			// Filter data
			data = {
				companyName						: data.companyName,
				companyRegistrationId 			: data.companyRegistrationId,
				personalIdentificationNumber 	: data.personalIdentificationNumber,
				securityQuestion 				: data.securityQuestion,
				securityAnswer 					: data.securityAnswer,
				registered                      : data.registered
			};
			
			// Show mask
			this.showLoadingMessage('Save in progress..');
			
			CustomerService.setAccountInfo(userId, data, {
				callback: function (data) {
					var name = "<b>" + userId + "</b>",					
						msg;
					
					if (data) {
						msg = "Account info of customer " + name + " has been saved successfully !";						
						
						// Set values
						view.setFormValues(data);
						
						view.showSuccessMessage(msg);
					} else {
						msg = "Failed to save Account info of customer " + name;						
						
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
		
        //var ctx = $webtop.getCustomContext();
		
		//var uss_service_login = ctx.url.uss.service_login;
		
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
					defaultMsg: 'Please enter account info in the form below.'
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
								title: 'General',
								defaults: { border: false },
								items: [
									{
										layout: 'column',
										defaults: { border: false, layout: 'form' },					            
										items: [
											{
												columnWidth: .5,
												items: [
												{
													xtype : 'compositefield',
													fieldLabel: 'User Id',				            	        	
													anchor: '-20',
													items: [
														{
															xtype: 'textfield',
															allowBlank: false,
															disabled: true,													
															name: 'userId',
															flex : 1
														}, {
															xtype : 'button',	
															fieldLabel: 'Password',						            		
															text : 'Reset Password',
															width : 110,
															handler: function() {
																// Forward to USS
																var parameter = "?userId="+this.model.loginName;
																var ctx = $webtop.getCustomContext(),
																	url = ctx ? ctx.url.uss.service_reset_password + parameter : false;
																   
																    
																if (url) {
																	window.open(url);	
																} else {
																	this.showErrorMessage("Technical Exception: URL to Change Password page not available");
																}
															},
															scope: this
														}
													]
												}
												]
											}, {
												columnWidth: .5,			
												defaults: { anchor: '100%', xtype: 'textfield'},
												items: {												
													allowBlank: false,
													emptyText: 'FIN / NIRC / Passsport',
													fieldLabel: 'ID No',
													name: 'personalIdentificationNumber'												
												}					            			            	
											}
										]
									},
									{
										xtype : 'compositefield',
										disabled: true,
										fieldLabel: 'Full Name',
										items : [
											{
												//the width of this field in the HBox layout is set directly
												//the other 2 items are given flex: 1, so will share the rest of the space
												width:          50,		
												xtype:          'combo',
												mode:           'local',
												value:          'mrs',
												triggerAction:  'all',
												forceSelection: true,
												editable:       false,
												fieldLabel:     'Title',
												name:           'title',
												hiddenName:     'title',
												displayField:   'name',
												valueField:     'value',
												store:          new Ext.data.JsonStore({
													fields : ['name', 'value'],
													data   : [
														{name : 'Mr',   value: 'mr'},
														{name : 'Mrs',  value: 'mrs'},
														{name : 'Miss', value: 'miss'}
													]
												})
											},
											{
												xtype: 'textfield',
												flex : 1,
												name : 'firstName',
												emptyText : 'First Name',
												fieldLabel: 'First',
												allowBlank: false
											},
											{
												xtype: 'textfield',
												flex : 1,
												name : 'lastName',
												emptyText : 'Last Name',
												fieldLabel: 'Last',
												allowBlank: false
											}, {
												xtype : 'button',																            		
												text : 'Change Name',
												width : 110,
												handler: function() {
													// Forward to USS
													var parameter = "?userId="+this.model.loginName;
													var ctx = $webtop.getCustomContext(),
														url = ctx ? ctx.url.uss.change_name + parameter : false;
													
													
													if (url) {
														window.open(url);	
													} else {
														this.showErrorMessage("Technical Exception: URL to ChangeName page not available");
													}
												},
												scope: this
											}
										]
									}
								]			            
							},					
							{
								xtype:'fieldset',
								title: 'Company',
								autoHeight: true,
								layout: 'column',
								defaults: { border: false, layout: 'form' },
								items: [
									{
										columnWidth: .5,
										defaults: { border: false },
										items: {
											xtype: 'textfield',
											fieldLabel: 'Name',
											name: 'companyName',									
											anchor: '-20'
										}				            	
									}, {
										columnWidth: .5,
										defaults: { border: false },
										items: {
											xtype: 'textfield',
											fieldLabel: 'Registration ID',
											name: 'companyRegistrationId',									
											anchor: '100%'
										}				            	
									}
								]
							},  
							{
								xtype:'fieldset',
								title: 'Registration',
								height: 60,
								layout: 'vbox',							
								defaults: { border: false, flex: 1 },
								layoutConfig: {
									align : 'stretch',
									pack  : 'start'
								},
								items: [							
									{
										layout: 'column',
										defaults: { border: false, layout: 'form' },
										items: [
											{
												columnWidth: .5,
												defaults: { border: false },
												items: [{
													xtype: 'compositefield',
													fieldLabel: 'Registered on',										
													items: [
														{
															flex: 1,
															xtype: 'datefield',		
															name: 'registered',
															id:'register',
															width: 80,															
															readOnly  	: true
														},
														{
															xtype     	: 'button',
															text	  	: 'Unregister',												
															width		: 90,
															handler		: function() {
																// Forward to USS
																var parameter = "?userId="+this.model.loginName;
																var ctx = $webtop.getCustomContext(),
																	url = ctx ? ctx.url.uss.unregister + parameter : false;
																
																if (url) {
																	window.open(url);	
																} else {
																	this.showErrorMessage("Technical Exception: URL to Unregister page not available");
																}
															},
															scope		: this
														}
													]
												} 
												]
											}, {				            	
												columnWidth: .5,
												defaults: { border: false, layout: 'form' },									
												items: [
												{
													xtype: 'compositefield',
													fieldLabel: 'Approved on',										
													items: [
														{
															xtype: 'datefield',												
															name: 'approved',															
															width: 80,															
															readOnly  	: true										
														}, {
															fieldLabel	: '&nbsp;',
															labelSeparator: '',
															xtype     	: 'button',
															width		: 140,
															text	  	: 'Resend Activation Email',
															id          : ID.RESEND,
															handler     : function(){
																// Forward to USS
																//var date = Ext.getCmp(this._ID.RESEND).getValue();
																/*if(date != null)
																	{
																	Ext.getCmp('resend_button').setDisabled(true);
																	}*/
																	
																var parameter = "?userId="+this.model.loginName;
																var ctx = $webtop.getCustomContext(),
																	url = ctx ? ctx.url.uss.resend_mail + parameter : false;
																
																if (url) {
																	window.open(url);	
																} else {
																	this.showErrorMessage("Technical Exception: URL to Unregister page not available");
																}
															},
															scope		: this
														} 
													]
												}]
											}]
									}
								]
							},
							
							{
								xtype:'fieldset',
								title: 'Security',
								defaults: { border: false },
								items: {
									layout: 'column',
									defaults: { border: false, layout: 'form' },
									items: [
										{
											columnWidth: .5,
											defaults: { border: false },
											items: {
												xtype: 'textfield',
												fieldLabel: 'Question',
												name: 'securityQuestion',
												allowBlank: false,
												anchor: '-20'
											}				            	
										}, {				            	
											columnWidth: .5,
											defaults: { border: false },
											items: {
												xtype: 'textfield',
												fieldLabel: 'Answer',
												name: 'securityAnswer',
												allowBlank: false,
												anchor: '100%'
											}				            	
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
							iconCls: 'icon-silk-disk_multiple',
							id: ID.SAVE,							
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
								this.getAccountInfo();
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

Ext.reg('emo-view-account-info', EMobility.CustomerView.AccountInfo);