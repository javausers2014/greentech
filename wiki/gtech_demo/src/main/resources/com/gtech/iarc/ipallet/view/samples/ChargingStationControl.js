Ext.ns("EMobility");

/**
 * Basic Template for a View
 * 
 * @author 
 * @class 
 * @extends EMobility.View
 */
EMobility.ChargingStationControl = Ext.extend(EMobility.MessageView, {	
	iconCls: "icon-x16-chargingactions",

	width: 850,
	height: 420,	
	minHeight: 420,
	
	initId: function() {
		this.supr().initId.call(this);
		
		return Ext.apply(this._ID, {
			ChargingSpotGrid : Ext.id()
		});
	},
	initComponent: function() {
		var _p = this,
			ID = this.initId();

		_p._STORES = {
			ChargingStation: new Ext.data.JsonStore({
				autoLoad: false,
                idProperty: 'uid',
                fields: EMobility.constants.fields.SpotAggregator
			}),
			ChargingSpot: new Ext.data.JsonStore({
				autoDestroy: true,
				autoLoad: false,
                fields: EMobility.constants.fields.ChargingSpot
			})		
		};
		
		_p.model = _p.model || {};		
		_p.model.chargingStation = _p.model.chargingStation || {};
		_p.uid = _p.model.chargingStation.uid;
		
		var controlHandler = {
			callback : function(){
				$webtop.notifyInfo("Success", "Operation request initiated successfully!");
			},
			exceptionScope: _p
		};
		
		var customDateRenderer = function(v,p){
			return v ? new Date(v).format("m/d/Y - H:i:s") :" - ";
		};
		
		var refreshTask = {
			run : _p.loadData.createDelegate(_p),
			interval: 15000,
			scope: _p
		};
		
		Ext.apply(_p, {
			border: false,
			defaults: { border: false },
			layout: 'border',
			items: [
				this.buildErrorPanel(),
				{
					region: 'center',
					bodyStyle: 'padding: 10px;',
					layout: 'vbox',
					layoutConfig: {
						align : 'stretch',
						pack  : 'start'
					},
					defaults: { flex: 1 },
					items:[
						{
							title: "Charging Station",
							xtype: "fieldset",
							buttonAlign: "left",			
							items:{
								xtype: "grid",
								loadMask: true,
								height: 50,
								autoScroll: true,
								viewConfig: {
			//						forceFit: true,
									emptyText: "No charging stations to display."
								},					
								sm : new Ext.grid.RowSelectionModel({
									singleSelect : true
								}),
								ds: _p._STORES.ChargingStation,
								columns: [
									new Ext.grid.RowNumberer(),
									{
										header: "Created",
										dataIndex: "createdTimestampUTC",
										renderer: customDateRenderer,
										width: 150
									},					
									{
										header: "Last update",
			//							xtype: "datecolumn",
			//							format: "m/d/Y - H:i:s",
										dataIndex: "updatedTimestampUTC",
										renderer: customDateRenderer,
										width: 150							
									},
									{
										header: "Last System Heartbeat",
										dataIndex: "recentLowLevelHeartbeatTimestampUTC",
										renderer: customDateRenderer,
										width: 150
									},
									{
										header: "Last Application Heartbeat",
										dataIndex: "recentHighLevelHeartbeatTimestampUTC",
										renderer: customDateRenderer,
										width: 150
									}
								]
							},
							
							buttons:[{
								iconCls:"",	
								style: {padding:'3px'},
								text: "Restart Application Software",
								tooltip: "Restart of the charging station application software." +
										" Takes up to 30 Minutes",								
								handler: function(){
									if(_p.uid){
										SpotAggregatorControl.reset(_p.uid, controlHandler);	
									}
								}
							},{
								iconCls:"",
								style: {padding:'3px'},
								text: "Reboot Charging Station",
								tooltip: "Complete reboot of the charging station system including" +
										" operation system and application software." +
										" Takes up to 30 Minutes. ",
								handler: function(){
									if(_p.uid){
										SpotAggregatorControl.reboot(_p.uid, controlHandler);
									}
								}
							},{
								iconCls:"",
								style: {padding:'3px'},
								text: "Factory Reset & Reboot",
								tooltip: "Revert charging station software to factory version and " +
										"reboot of charging station. <font color='blue'>CAUTION: A Factory Reset may " +
										"take a up to 1 hour, during which the charging station " +
										"is not usable.</font> ",
								handler: function(){
									if(_p.uid){
										SpotAggregatorControl.factoryReset(_p.uid, controlHandler);
									}
								}
								
							},{
								iconCls:"",
								style: {padding:'3px'},
								text: "Set Out of Order",
								tooltip: "Set the charging station status as 'Out of Order'",
								handler: function(){
									if(_p.uid){
										SpotAggregatorControl.setOutOfOrder(_p.uid, controlHandler);
									}
								}					
							},{
								iconCls:"",
								style: {padding:'3px'},
								text: "Set Operational",
								tooltip: "Set the charging station status as 'Operational'",
								handler: function(){
									if(_p.uid){
										SpotAggregatorControl.setOperational(_p.uid, controlHandler);
									}
								}
							}]
						},
						{
							title: "Charging Spots",
							xtype: "fieldset",
							layout: 'fit',
							items:{
								id: _p._ID.ChargingSpotGrid,
								xtype: "grid",
								loadMask: true,
								viewConfig: {
			//						forceFit: true,
									emptyText: "No charging spots to display."
								},
								height: 200,
								ds: _p._STORES.ChargingSpot,
								columns:[
									new Ext.grid.RowNumberer(),
									{
										header: "Spot ID",
										dataIndex: "logicalId"
									},
									{
										header: "Status",
										dataIndex: "status",
										renderer : EMobility.constants.lookupRenderer
									},
									{
										header: "Meter Reading",
										dataIndex: "meterReadingInWh"
									},
									{
										header: "Comments",
										dataIndex: "note"
									}
								],
								tbar: {						
									style: 'padding: 3px 0px;',
									defaults: { style: 'padding: 0px 7px;' },
									items:[
									"->",
									{
										text: "Set Operational",
										handler: function(){
											var r = _p.getGrid().getSelectionModel().getSelected();
											
											if(r){
												var uid = r.get("uid");
												
												ChargingSpotControl.setOperational(uid, controlHandler);
											}else{
												$webtop.notifyWarn("Charging Spot Operation", "No charging spot selected!")
											}
										}
									},
									{
										text: "Set Out of Order",
										handler: function(){
											var r = _p.getGrid().getSelectionModel().getSelected();
											
											if(r){
												var uid = r.get("uid");
												
												ChargingSpotControl.setOutOfOrder(uid, controlHandler);
											}else{
												$webtop.notifyWarn("Charging Spot Operation", "No charging spot selected!")
											}
										}
									},"-",
									{
										text: "Lock",
										handler: function(){
											var r = _p.getGrid().getSelectionModel().getSelected();
											
											if(r){
												var uid = r.get("uid");
												
												ChargingSpotControl.lockDoor(uid, controlHandler);
											}else{
												$webtop.notifyWarn("Charging Spot Operation", "No charging spot selected!")
											}
										}
									},
									{
										text: "Unlock",
										handler: function(){
											var r = _p.getGrid().getSelectionModel().getSelected();
											
											if(r){
												var uid = r.get("uid");
												
												ChargingSpotControl.unlockDoor(uid, controlHandler);
											}else{
												$webtop.notifyWarn("Charging Spot Operation", "No charging spot selected!")
											}
										}
									},"-",
									{
										text: "Set Energy On",
										handler: function(){
											var r = _p.getGrid().getSelectionModel().getSelected();
											
											if(r){
												var uid = r.get("uid");
												
												ChargingSpotControl.setEnergyOn(uid, controlHandler);
											}
										}
									},
									{
										text: "Set Energy Off",
										handler: function(){
											var r = _p.getGrid().getSelectionModel().getSelected();
											
											if(r){
												var uid = r.get("uid");
												
												ChargingSpotControl.setEnergyOff(uid, controlHandler);
											}else{
												$webtop.notifyWarn("Charging Spot Operation", "No charging spot selected!")
											}
										}
									}
									]
								}
							}
						}
					]
				}
			],				
			buttons:[{
				text: "Close", 
				iconCls: "icon-x16-clear",
				handler: function(){
					if(_p.model && _p.model.view && _p.model.view.close){
						_p.model.view.close();
					}
				}
			}],
			listeners:{
				afterrender: function(){
//					_p.loadData();
				},
				activate: function(){
					_p.task = Ext.TaskMgr.start(refreshTask);
				},
				deactivate:function(){
					Ext.TaskMgr.stop(refreshTask);
				},
				scope: _p
			}
		});
		
		_p.supr().initComponent.call(_p);
	},
	
	loadData: function(){
        var _p=this;
        
        if(_p.model){
            var cs = _p.model.chargingStation;
            
            _p.uid = cs.uid;
            
            SpotAggregatorService.getSpotAggregator(cs.uid, {
                callback: function(aggregator){
                	/**
                	 * This check is required, in case the model data gets updated
                	 * while the call was in progress. In that case do not show
                	 * stale data.
                	 */
                	var isVisible;
                	try{
                		isVisible = _p.isVisible();
                	}catch(e){
                		// will fail with exception if window is closed.
                	}
                	
                	if(isVisible){
                        cs = aggregator;
                        
                        _p._STORES.ChargingStation.loadData([cs]);
                        
                        if(!Ext.isEmpty(cs.chargingSpots)){
                            _p._STORES.ChargingSpot.loadData(cs.chargingSpots);
                        }
                	}
                },
                exceptionScope: _p             
            });
        }
	},
	
	getGrid:function(){
		return Ext.getCmp(this._ID.ChargingSpotGrid);
	},
	
	getSelectedrecord: function(){
		return this.getGrid.getSelectionModel().getSelected();
	}
});


Ext.reg("emo-view-chargingstation-control", EMobility.ChargingStationControl);