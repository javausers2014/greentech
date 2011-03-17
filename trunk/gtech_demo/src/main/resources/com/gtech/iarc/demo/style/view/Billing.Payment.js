Ext.ns("EMobility.Intuity");

EMobility.Intuity.Payment = Ext.extend(EMobility.Intuity.SubView, {
	
	
	initId: function() {
		this.supr().initId.call(this);
		
		return Ext.apply(this._ID, {
			FORM 	: Ext.id(),
			RADIO 	: Ext.id(),

			// Buttons
			PROCEED	: Ext.id()
		});
	},
	
	initView: function() {
		this.getAccountInfo();
	},
	
	getAccountInfo: function() {
		var	view = this;
		
		// Show mask		
		this.showLoadingMessage('Loading account balance..');

		BillsService.getBillInfo({
	        callback:function(data) {	            
	        	if(data && data.valueDate) {					
					// Set values
					view.setFormValues(data);					
					view.showInfoMessage('Account balance loaded. Please proceed with payment.');
					view.dataUnavailable = false;
        		}
	        	else {
					view.showErrorMessage('Sorry, your billing information is not available now.');
					view.dataUnavailable = true;
	        	}
	        },
			exceptionScope: view
        });		
	},
	
	disableButtons: function(disabled) {		
		Ext.getCmp(this._ID.PROCEED).setDisabled(disabled);		
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
	
	viewMsg: 'Please proceed with payment with the form below.',
		
	initComponent: function() {
		var view = this,
			ID = this.initId();
		
		var ctx = $webtop.getCustomContext();
		var enets_login_url 		    = ctx.url.enets_login;
		var giro_form_download_url      = ctx.url.giro_download;
		var credit_login_url			= "./view/enets-credit-payment";
		var debit_login_url			    = "./view/enets-debit-payment";
		var giro_logo					= "iarc-billing-payment-giro ";
		var txnReference = new Date().getTime();
				
		Ext.apply(this, {
			header: false,
			defaults: { border: false },
			listeners: {
				afterrender: {
					fn: this.initView.createDelegate(this),
					single: true,
					buffer: 500
				}
			},
			items: {
				xtype: 'form',
				id: ID.FORM,
				labelAlign: 'top',
				labelSeparator: '',
				layout:'vbox',
				layoutConfig: { align : 'stretch', pack  : 'start' },
				defaults: { border: false },
				height: 550,
				items: [					
					// Message Panel
					this.buildMessagePanel({
						defaultMsg: this.viewMsg
					}),
					// General
					this.buildSection("Account", 120,
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
//											readOnly: true,
											disabled: true,
											fieldLabel: 'Account Number',
											name: 'externalBillingUid',
											anchor: '-20'
										}
									}, {				            	
										columnWidth: .5,										
										items: {
											xtype: 'textfield',
//											readOnly : true,
											disabled: true,
											fieldLabel: 'Statement Date',
											name: 'valueDate',
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
									fieldLabel	: 'Account Balance',
									name        : 'balance',
									disabled: true
								}
							}															
						]
					),
					
					// Company
					this.buildSection("Payment", 600,
						[							
							{
								xtype: 'box',
								height: 25,
								html: 'Please select one of the payment options below: '
							},
							{
								height: 65,
								xtype: 'toolbar',								
								id: ID.RADIO1,
								cls: 'iarc-billing-payment-btn-group',
								defaults: {
									toggleGroup: 'payment-option',									
									width: 50,									
									scale: 'large',									
									iconAlign: 'top'
								},
								items: [
									{
										style: 'margin-right: 10px',
										pressed: true,
										iconCls: 'iarc-billing-payment-giro',
										handler: function(){
											Ext.getCmp(ID.PROCEED).setText("Download GIRO form");
											view.disableButtons(false);
											view.paymentType = "GIRO";
										}
									}
									
								]
							},							
							{
								height: 65,
								xtype: 'toolbar',								
								id: ID.RADIO,
								cls: 'iarc-billing-payment-btn-group',
								defaults: {
									toggleGroup: 'payment-option',									
									width: 50,									
									scale: 'large',									
									iconAlign: 'top'
								},
								items: [
									{
										iconCls: 'iarc-billing-payment-visa',										
										style: 'margin-right: 10px',
										handler: function(){
											Ext.getCmp(ID.PROCEED).setText("Proceed with Payment");
											if (view.dataUnavailable) {
												view.disableButtons(true);
											}
											view.paymentType = "VISA";
										}
									},
									{
										iconCls: 'iarc-billing-payment-master',
										style: 'margin-right: 10px',
										handler: function(){
											Ext.getCmp(ID.PROCEED).setText("Proceed with Payment");
											if (view.dataUnavailable) {
												view.disableButtons(true);
											}
											view.paymentType = "MASTERCARD";
										}
									},
									{	
										iconCls: 'iarc-billing-payment-enets',
										style: 'margin-right: 10px',
										handler: function(){
											Ext.getCmp(ID.PROCEED).setText("Proceed with Payment");
											if (view.dataUnavailable) {
												view.disableButtons(true);
											}
											view.paymentType = "ENETS";
										}
									}
									
								]
							},	
							{
								height: 30,
								items: {	
									xtype: 'button',
									id: ID.PROCEED,
									text: 'Download GIRO form',
									width: 140,
									handler: function() {
										var data = view.savedData,
											url,
											title="_blank";
										
										switch(view.paymentType){
											case "ENETS":
										   		url = debit_login_url;
										   		
										   		break;
										   		
											case "VISA":
											case "MASTERCARD":
											    url = credit_login_url;
										   		
										   		break;
											
											default:
											case "GIRO":
												window.open(giro_form_download_url);
										}
									   	
									   	if (url) {
									   		window.open(url
												+ "?billingId=" 	+ data.externalBillingUid
									   			+ "&txnReference=" 	+ data.valueDate
												+ "&amount=" 		+ data.balance										
												, title,
												' toolbar=no, location=yes, directories=no, status=no, menubar=yes, scrollbars=yes, resizable=no, copyhistory=no, width='+ 800 +', height='+ 600
											);
									   	}
									}
								}
							},
							{
								height: 300, overflow: 'auto',
								xtype: 'box',
								html :  "<span class='iarc-profile-billing-warning'>If you are using a pop-up blocker,please temporarily disable it. Otherwise,the relevant transaction<br/>"
										+"pages may not be displayed or your transaction request may not be completed.</span>"								
										+"<br/><br/>"
										+"Information:"
										+"<br/><br/>"
										+ "The details provided above are for your information only and are not a substitute for your monthly bill."
										+"<br/>"
										+ "Please allow 2-4 weeks time to process your payment."
										+"<br/>"
										+ "Payment made after 20th day of the month will be reflected in the following month statement.<br/><br/><br/>"
									
						}
						], true
					)
					
				]
			}
		});
		
		EMobility.Intuity.Identity.superclass.initComponent.call(this);
	}
});

Ext.reg('profile-billing-payment', EMobility.Intuity.Payment);