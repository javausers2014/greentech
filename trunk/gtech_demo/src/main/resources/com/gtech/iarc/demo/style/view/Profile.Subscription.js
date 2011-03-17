Ext.ns("EMobility.Intuity");

EMobility.Intuity.Subscription = Ext.extend(EMobility.Intuity.SubView, {
	title: "<@i18nText key='dp.profile.subscription.title' />",
	
	initId: function() {
		this.supr().initId.call(this);
		
		return Ext.apply(this._ID, {
			// Sections
			VEHICLE		: Ext.id(),
			CARDS		: Ext.id(),
			PLAN		: Ext.id(),
			
			// Buttons
			SUBSCRIBE	: Ext.id(),
			UNSUBSCRIBE	: Ext.id()
		});
	},
	
	initView: function() {
		// Loading UI components
		this.showLoadingMessage(this.viewMsg);
		
		// Init subviews in sequence to not crash Ext events
		(function(){ Ext.getCmp(this._ID.VEHICLE).initView(); }).defer(900, this);
		(function(){ Ext.getCmp(this._ID.CARDS).initView(); }).defer(1300, this);
		(function(){ Ext.getCmp(this._ID.PLAN).initView(); }).defer(1700, this);
	},
	
	viewMsg: "<@i18nText key='dp.profile.subscription.loadingmsg' />",
	
	isDirty: function() {
		
		var vehicleForm = Ext.getCmp(this._ID.VEHICLE);		
		var rfidGrid = Ext.getCmp(this._ID.CARDS);
		
		if (vehicleForm.getForm().isDirty()) {
			return true;
		}	
		
		return rfidGrid.isDirty();
		
	},
	initComponent: function() {
		var view = this,
			ID = this.initId();		
			
		Ext.apply(this, {
			title: "<@i18nText key='dp.profile.subscription.title' />",			
			listeners: {				
				expand: {
					fn: this.initView.createDelegate(this),
					single: true					
				}
			},
			items: {
				layout:'vbox',
				layoutConfig: { align : 'stretch', pack  : 'start' },
				defaults: { border: false },
				height: 475,
				items: [
					// Message Panel
					this.buildMessagePanel({
						defaultMsg: this.viewMsg
					}),					
					// Company
					this.buildSection("<@i18nText key='dp.profile.subscription.vehicleinfo.title' />", 110,
						[
							{
								xtype: 'profile-subscription-vehicle',
								trackResetOnLoad: true,
								id: ID.VEHICLE,
								_view: view,	// Link to parent view
								height: 90							
							}
						]
					),
					
					// Security
					this.buildSection("<@i18nText key='dp.profile.subscription.cards.title' />", 150,
						[
							{
							    xtype: 'profile-subscription-rfid-cards',
							    id: ID.CARDS,
								_view: view,	// Link to parent view
								height: 130
							}
						]
					),
					
					// General
					this.buildSection("<@i18nText key='dp.profile.subscription.priceplan.title' />", 185,
						[
							{
								xtype: 'profile-subscription-plan',
								id: ID.PLAN,
								_view: view,	// Link to parent view
								height: 165
							}
						],						
						true	// No border
					)
				]
			}
		});
		
		EMobility.Intuity.Subscription.superclass.initComponent.call(this);
	}
});

Ext.reg('profile-subscription', EMobility.Intuity.Subscription);