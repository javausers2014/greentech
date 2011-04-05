Ext.ns("EMobility");

EMobility.CustomerView = Ext.extend(EMobility.View, {
	title: "Customer",
	iconCls: "icon-x16-chargingactions",	
	
	maxInstance: 100,	// maxInstance : 0 is currently not working as a bug in Webtop
	width: 850,
	height: 500,	
	resizable: false,
	maximizable: false,
	
	initId: function() {
		return this._ID = {
			HEADER: Ext.id()
		};
	},
	
	setViewHeader: function() {
		var model = this.model;
		Ext.get(this._ID.HEADER).update(model.firstName + " " + model.lastName + " - <span style='color: #666;'>"+ model.loginName +"</span>");		
	},
	
	initComponent: function() {
		
		var view = this,
			ID = this.initId(),
			model = this.model;
		
		// Apply config
		Ext.apply(this, {			
			border: false,
			defaults: { border: false },
			layout: 'border',
			items: [
				{
					region: "north",
					bodyStyle: "background: #fff;",
					xtype: "panel",
					height: 42,				
					layout: "border",
					items: [{
						xtype: "box",
						html: {
							tag: "img",
							cls: "img-view-header-icon",
							src: view.img("classpath:com.innovations.emobility.theme.icons.x32/preferences_desktop_user.png", true)
						},
						region: "west",
						width: 60 
					},{
						xtype: "box",
						html: [{
							tag: "div",
							cls: "img-view-header-text",
							id: ID.HEADER,
							html: model.firstName + " " + model.lastName + " - <span style='color: #666;'>"+ model.loginName +"</span>"
						}],						
						region: "center"
					},{
						width: 80,
						region: "east",
						layout: 'fit',
						padding: 10,
						border: false,
						items: {
							xtype: "button",
							text: 'Back',
							iconCls: 'icon-silk-bullet_go',
							handler: function() {
								// Show window
								var w = $webtop.getViewAsWindow({
										id: 'view.CustomerSearch'
									});

								if (w) {
									w.show();
								}
								
								// Close this view
								view.ownerCt.close();
							}
						}
					}]	
				}, 
					{
					region: 'center',
					xtype: 'tabpanel',
					activeTab: 0,
					bodyStyle: 'padding: 8px',
					defaults: { model: model },		// default model applied to all sub-views
					items: [
						{
							xtype: 'emo-view-account-info'
						}, {
							xtype: 'emo-view-contact-info'
						}, {
							xtype: 'emo-view-user-subscription'							
						}, {
							xtype: 'emo-view-charging-actions'
						}, {
							xtype: 'emo-view-billing'
						}, {
							xtype: 'emo-view-approval'
						}, {
							xtype: 'emo-view-sap'
						}
					]
				}
			]			
		});
		
		this.supr().initComponent.call(this);
	}
});