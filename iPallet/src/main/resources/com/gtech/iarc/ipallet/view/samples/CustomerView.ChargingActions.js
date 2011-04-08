Ext.ns("EMobility", "EMobility.CustomerView", "EMobility.CustomerView.ChargingActions");

EMobility.CustomerView.ChargingActions = Ext.extend(EMobility.MessageView, {
	title: "Charging Actions",
	iconCls: "icon-silk-car",

	initId: function() {
		this.supr().initId.call(this);
		
		return Ext.apply(this._ID, {
			// Grid
			START_BUTTON 	: Ext.id()
		});		
	},
	
	initView: function() {
		// Update status
		this.getContractStatus();
	},
	
	getContractStatus: function() {
		var view = this,
			userId = this.model.loginName;
		
			// Show mask		
		CustomerService.getContractStatus(userId, {
			callback: function (data) {
				if (data && data.status && 
					(	data.status.id == "SUBSCRIBED" || 
						data.status.id == "SUBSCRIBED_WITHOUT_SAP_ID")) {	
					Ext.getCmp(view._ID.START_BUTTON).enable();
				}				
			},			
			exceptionScope: view
		});		
	},
	
	initComponent: function() {		
		var view = this,
			ID = this.initId(),
			model = this.model;
		
		// Apply config
		Ext.apply(this, {
			defaults: { border: false },
			layout:'card',
			tbar: {
				buttonAlign: 'right',
				style: 'padding-bottom: 5px; background: #fff; border: 0;',
				defaults: { style: 'padding-left: 7px;', xtype: 'button' },
				items: [
					{
						text: 'List',
						enableToggle: true,
						toggleGroup: 'layoutGroup',
						pressed: true,
						iconCls: 'icon-silk-application_view_list',
						listeners: {
							toggle: function(button, pressed) {
								if (pressed) {
									view.getLayout().setActiveItem(0);
								}
							}	
						}
					}, {
						id : ID.START_BUTTON,
						text: 'Start',
						enableToggle: true,
						toggleGroup: 'layoutGroup',		
						iconCls: 'icon-silk-add',
						disabled: true,
						listeners: {
							toggle: function(button, pressed) {
								if (pressed) {
									view.getLayout().setActiveItem(1);
								}
							}
						}
					}					
				]
			},
			activeItem: 0,			
			defaults: { model: model, border: false },		// default model applied to all sub-views
			items: [{
				xtype: 'emo-view-charging-actions-list'
			},{
				xtype: 'emo-view-charging-actions-search'
			}],
			listeners: {
				show: {
					fn: function() {
						this.initView();
					},
					scope: this
				}
			}
		});		
		
		this.supr().initComponent.call(this);
	}
	
});

Ext.reg('emo-view-charging-actions', EMobility.CustomerView.ChargingActions);