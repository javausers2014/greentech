Ext.ns("EMobility");

/**
 * 
 * @class EMobility.ChargingStationDialog
 * @extends EMobility.View
 */
EMobility.ChargingStationDialog = Ext.extend(EMobility.View, {
	title: "Charging Station",
	iconCls: "icon-x16-chargingactions",
	
	maxInstance: 2,
	
	width: 850,
	height: 420,	
	minHeight: 420,
	maximized: true,
	
	initComponent: function() {
		var _p = this;
		
		_p.model = Ext.apply({view: _p}, _p.model);
		
		// Apply config
		Ext.apply(this, {			
			defaults: { border: false },
			layout: 'border',			
			items: {
				xtype: 'tabpanel',
				
				frame: true,
				region: "center",
				activeTab: 0,
				items: [
					{
						title: 'Details',
						xtype: "emo-view-chargingstation-details",
						border: false,
						model: _p.model
					}
					, 
					{
						title: "Control",
						xtype: "emo-view-chargingstation-control",
						border: false,
						disabled: Ext.isEmpty(_p.model.chargingStation), 
						model: _p.model
					}
				]
			}
		});
		
		this.supr().initComponent.call(this);
	},
	setModel: function(model){
		this.model = model;
		
		Ext.each(this.items.items[0].items.items, function(item, idx){
			if(item.model){
				Ext.apply(item.model,model);
			}
			
			if(item.disabled){
                item.enable();
			}
			
			if(item.loadData && Ext.isFunction(item.loadData)){
				item.loadData.defer(10, item);
			}
		}, this);
	}
});