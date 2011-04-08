Ext.ns("EMobility", "EMobility.CustomerView");

EMobility.CustomerView.Billing = Ext.extend(Webtop.View, {
	title: "Customer Billing",
	iconCls: "icon-silk-layout_sidebar",	
	
	width: 800,
	height: 520,	
	resizable: false,		
	
	initComponent: function() {
		
		var view = this;
		
		// Apply config
		Ext.apply(this, {			
			defaults: { border: false },
			layout:'card',
			tbar: {				
				buttonAlign: 'right',
				border: false,				
				style: 'padding-bottom: 5px; background: #fff; border: 0;',
				defaults: { style: 'padding-left: 7px;', xtype: 'button' },
				items: [
					{
						text: 'Summary',
						enableToggle: true,
						toggleGroup: 'billingGroup',
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
						text: 'Important Info',
						enableToggle: true,
						toggleGroup: 'billingGroup',		
						iconCls: 'icon-silk-information',
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
			defaults: { model: view.model, border: false },		// default model applied to all sub-views
			items: [
				{
					xtype: 'emo-view-billing-summary'
				}, {
					xtype: 'emo-view-billing-important-information'
				}
			]
		});
		
		this.supr().initComponent.call(this);
	}
});


Ext.reg('emo-view-billing', EMobility.CustomerView.Billing);