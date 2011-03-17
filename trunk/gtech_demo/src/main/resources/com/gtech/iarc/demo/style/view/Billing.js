Ext.ns("EMobility.Intuity");

EMobility.Intuity.Billing = Ext.extend(EMobility.Intuity.View, {
	toolbarTitle: "Billing",
		
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
					html: 	"<a class='bullet-arrow' href='javascript:void(0);'>" +
								"Terms and Conditions" +
							"</a>"
				},
				{
					height: 25,
					xtype: 'box',
					cls: 'iarc-profile-right-links',
					style: 'padding-left: 12px;',
					html: 	"<a class='bullet-arrow' href='javascript:void(0);'>" +
								"FAQ" +
							"</a>"
				},
				{
					height: 25,
					xtype: 'box',
					cls: 'iarc-profile-right-links',
					style: 'padding-left: 12px;',
					html: 	"<a class='bullet-arrow' href='javascript:void(0);'>" +
								"Need help ?" +
							"</a>"
				}
			]
		};		
	},
	
	buildMainLeft: function() {
		return {
			cls: 'iarc-profile-main-accordion',
			defaults: {
				bodyStyle	: 'padding: 0px 10px 10px 20px', 
				autoScroll	: true,
				model		: this.model
			}, 
			layout: 'fit',			
			items: [
				// Account
				{
					xtype: 'profile-billing-payment'					
				}				
			]
		};
	}
});


