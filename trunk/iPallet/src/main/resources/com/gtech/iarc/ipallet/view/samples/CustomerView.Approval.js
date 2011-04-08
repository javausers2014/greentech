Ext.ns("EMobility.CustomerView");
EMobility.CustomerView.Approval = Ext.extend(EMobility.MessageView, {
	title: "Approval",
	iconCls: 'icon-silk-lock',
	
	initId: function() {
		this.supr().initId.call(this);
		
		return Ext.apply(this._ID, {
			FORM 	: Ext.id(),
			
			ID 		: Ext.id(),
			CONTRACT: Ext.id(),
			STATUS	: Ext.id(),
			REASONS	: Ext.id(),
			
			// Buttons
			APPROVE	: Ext.id(),
			REJECT	: Ext.id(),
			ANNUL	: Ext.id()
		});
	},
		
	getUserForm: function() {
		return this.findById(this._ID.FORM).getForm();
	},
	
	initView: function() {
		// Update status
		this.getContractStatus();
	},
	
	getContractStatus: function() {
		var view = this,
			userId = this.model.loginName;
		
			// Show mask		
		this.showLoadingMessage('Loading in progress..');
		
		CustomerService.getContractStatus(userId, {
			callback: function (data) {
				if (data) {
					view.showInfoMessage('Contract status loaded successfully.');
					
					// Set form values
					view.setFormValues(data);	
				}				
			},			
			exceptionScope: view
		});		
	},
	
	resetView: function() {
		// Reset form
		var d = this.savedData;
		if (d) {
			this.setFormValues(d);	
		}
	},
		
	setFormValues: function(data) {
		
		// Save data
		this.savedData = Ext.apply(data, {});
		
		var form = this.getUserForm(true),
			status = data.status,
			time = data.lastUpdatedTimestamp; 
		
		// Set form values
		form.setValues({
		    id			: data.uid,				    
		    contract	: status.value,
			status		: time ? new Date(time) : "Unknown",
			reason		: data.reason
		});
		
		// Disable buttons
		switch(status.id) {
			case 'WAITING_FOR_SUBSCRIPTION':
				this.disableApprove(false);
				this.disableReject(false);
				this.disableAnnul(true);
				break;
			
			case 'SUBSCRIBED':
			case 'SUBSCRIBED_WITHOUT_SAP_ID':
				this.disableApprove(true);
				this.disableReject(true);
				this.disableAnnul(false);
				break;
			
			default: 
				this.disableApprove(true);
				this.disableReject(true);
				this.disableAnnul(true);
		}
	},
	
	disableForm: function() {
		Ext.getCmp(this._ID.REASONS).setDisabled(true);
	},
	
	getReasons: function() {
		return Ext.getCmp(this._ID.REASONS).getValue();
	},
	
	getUserForm: function(basic) {
		var f = this.findById(this._ID.FORM);
		return basic ? f.getForm() : f;
	},
		
	approveApplication: function() {
		var	view = this,
			cmt = this.getReasons(),			
			userId = this.model.loginName;
			
		// Show mask		
		this.showLoadingMessage('Approval in progress');
		
		CustomerService.approveApplication(userId, cmt, {
			callback : function (data) {
				if (data) {
					var msg = "Customer <b>" + userId + "</b> has been approved successfully !";
					
					// Set form values
					view.setFormValues(data);
					
					// Show success message
					view.showSuccessMessage(msg);
				}
			},
			exceptionScope : view
		});
	},
	
	rejectApplication: function() {
		var	view = this,
			cmt = this.getReasons(),			
			userId = this.model.loginName;
			
		// Show mask		
		this.showLoadingMessage('Rejection in progress');
		
		CustomerService.rejectApplication(userId, cmt, {
			callback : function (data) {
				if (data) {
					var msg = "Customer <b>" + userId + "</b> has been rejected successfully !";
					
					// Set form values
					view.setFormValues(data);
					
					// Show success message
					view.showSuccessMessage(msg);
				}
			},
			exceptionScope : view
		});
	},
	
	annulContract: function() {
		var	view = this,
			cmt = this.getReasons(),			
			userId = this.model.loginName;
			
		// Show mask		
		this.showLoadingMessage('Annulation in progress');
		
		CustomerService.annulContract(userId, cmt, {
			callback : function (data) {
				if (data) {
					var msg = "Customer <b>" + userId + "</b> has been annuled successfully !";
					
					// Set form values
					view.setFormValues(data);
					
					// Show success message
					view.showSuccessMessage(msg);
				}
			},
			exceptionScope : view
		});
	},
	
	disableApprove: function(disabled) {
		Ext.getCmp(this._ID.APPROVE).setDisabled(disabled);
	},
	
	disableReject: function(disabled) {
		Ext.getCmp(this._ID.REJECT).setDisabled(disabled);
	},
	
	disableAnnul: function(disabled) {
		Ext.getCmp(this._ID.ANNUL).setDisabled(disabled);
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
			defaults: { border: false },
			items: [
				this.buildMessagePanel({
					defaultMsg: 'Please approve or reject subscription application.'
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
						height: 200,
						scope: this,
						labelWidth: 130,
						defaults: { xtype: 'textfield', anchor: '100%' },
						items: [
							{
								fieldLabel: 'Contract ID',
								id: ID.ID,
								name: 'id',
								allowBlank: false,
								readOnly: true
							}, {
								fieldLabel: 'Contract Status',
								id: ID.CONTRACT,
								name: 'contract',
								allowBlank: false,
								readOnly: true
							}, {
								fieldLabel: 'Last status change',
								id: ID.STATUS,
								name: 'status',
								allowBlank: false,
								readOnly: true
							}, {
								fieldLabel: 'Reasons',
								id: ID.REASONS,
								name: 'reason',
								height: 65,
								emptyText: 'Reasons why Approve / Reject / Annul an application / contract ?',
								xtype: 'textarea'
							}
						],					
						buttons: [
						{
							text: 'Approve Application',
							id: ID.APPROVE,
							iconCls: 'icon-silk-accept',
							disabled: true,
							handler: function() {
								// Disable button
								this.setDisabled(true);
								
								// Approve
								view.approveApplication();
							}
						},{
							text: 'Reject Application',
							id: ID.REJECT,
							iconCls: "icon-silk-delete",
							disabled: true,
							handler: function() {
								// Disable button
								this.setDisabled(true);	           		
								
								// Reject
								view.rejectApplication();
							}
						},{
							text: 'Annul Contract',
							id: ID.ANNUL,							
							iconCls: "icon-x16-clear",
							disabled: true,
							handler: function() {
								// Disable button
								this.setDisabled(true);
								
								// Reject
								view.annulContract();
							}
						}]	
					}
				}
			]
		});
		
		this.supr().initComponent.call(this);
	}
});

Ext.reg('emo-view-approval', EMobility.CustomerView.Approval);