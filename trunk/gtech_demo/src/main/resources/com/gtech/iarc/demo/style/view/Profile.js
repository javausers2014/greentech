Ext.ns("EMobility.Intuity");

EMobility.Intuity.Profile = Ext.extend(EMobility.Intuity.View, {
	toolbarTitle: "<@i18nText key='dp.profile.toolbar.title' />",
	
	/* Config functions */	
	buildMainRight: function() {
		return {
			layout:'vbox',
			layoutConfig: {
				align : 'stretch',
				pack  : 'start'
			},
			items: [
				{
					height: 200,
					xtype: 'box',
					cls: 'iarc-profile-right-car'
				},
				{
					height: 21
				},	
				{
					height: 25,
					xtype: 'box',
					cls: 'iarc-profile-right-links',
					style: 'padding-left: 12px;',
					html: 	"<a class='bullet-arrow' onclick='window.open($webtop.getCustomContext().url.uss.change_password)' href='javascript:void(0);'>" +
								"<@i18nText key='dp.profile.link.changepswd' />" +
							"</a>"
				},
				{
					height: 25,
					xtype: 'box',
					cls: 'iarc-profile-right-links',
					style: 'padding-left: 12px;',
					html: 	"<a class='bullet-arrow' onclick='window.open($webtop.getCustomContext().url.uss.change_email)' href='javascript:void(0);'>" +
								"<@i18nText key='dp.profile.link.changeemail' />" +
							"</a>"
				},
				{
					height: 25,
					xtype: 'box',
					cls: 'iarc-profile-right-links',
					style: 'padding-left: 12px;',
					html: 	"<a class='bullet-arrow' onclick='window.open($webtop.getCustomContext().url.uss.unregister)' href='javascript:void(0);'>" +
								"<@i18nText key='dp.profile.link.unregister' />" +
							"</a>"
				}
			]
		};		
	},
	
	
	
	buildMainLeft: function() {
		return {			
			layoutConfig: {
				// layout-specific configs go here
				titleCollapse: true,
				animate: false
			},
			
			checkForUnsavedChanges: function() {									
				var panel  = this; 
				var activePanel = Ext.getCmp(panel.getLayout().activeItem.id);				
				if (activePanel.isDirty()) {
					Ext.Msg.alert("<@i18nText key='dp.profile.alert.unsavedchanges.title' />", "<@i18nText key='dp.profile.alert.unsavedchanges.msg' />").setIcon(Ext.MessageBox.ERROR);	
					return false;
				}
			},
			
			cls: 'iarc-profile-main-accordion',
			defaults: {
				bodyStyle	: 'padding: 0px 10px 10px 20px', 
				autoScroll	: true,
				model		: this.model,
				defaults	: { 
					border	: false,
					width	: 596		// !Important: Workaround against Ext accordion layout bug
				},									
				listeners : {
					'beforecollapse' : function() {	
						if (this.isVisible()) {
							// return Ext.getCmp('accordion-panel').unsavedDataAlert();
							return this.ownerCt.checkForUnsavedChanges();
						}						
					},					
					
					'beforeexpand' : function() {
						if (this.isVisible()) {
							return this.ownerCt.checkForUnsavedChanges();
						}						
					}
				
				}
			}, 
			layout: 'accordion',
			activeItem: 0,
			items: [				
				// Account
				{
					xtype: 'profile-identity'
				},				
				// Contact
				{
					xtype: 'profile-contact'					
				},								
				// Subscription
				{
					xtype: 'profile-subscription'
				}
				
			]
		};
	}
});