Ext.ns("EMobility", "EMobility.CustomerView", "EMobility.CustomerView.ChargingActions");

EMobility.CustomerView.ChargingActions.Search = Ext.extend(EMobility.SearchChargingStation, {
	header: false,
	
	onStationDblclick: function (grid, rowIndex, e) {
		var item = Ext.apply({}, this.data[rowIndex]),
			model = Ext.apply(item, {
				customer: this.model
			});
		
		if (item) {
			var win = new Ext.Window({
				title: "Start a Charging Action",					
				modal: true,
				width: 430,
				height: 250,
				layout: 'fit',
				items: {
					xtype: 'emo-view-charging-actions-start',
					model: model
				}
			});
			
			if(win){
				win.show();
			}
		}
	},
	onStationClick: function (grid, rowIndex, e) {
		var _p = this,
			mapPanel = Ext.getCmp(_p._ID.MapPanel);
		
		if(_p.data){
			var item = _p.data[rowIndex];
			
			mapPanel.panTo(item.point);
		}
	}
});

Ext.reg('emo-view-charging-actions-search', EMobility.CustomerView.ChargingActions.Search);