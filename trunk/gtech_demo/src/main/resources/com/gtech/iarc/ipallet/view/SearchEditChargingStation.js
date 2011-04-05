Ext.ns("EMobility");

/**
 * Provides search functionality for the charging stations based on the 
 * location specified. Uses map to provide the location search. 
 * 
 * @author Ankur Kapila
 * @class EMobility.SearchChargingStation
 * @extends EMobility.View
 */
EMobility.SearchEditChargingStation = Ext.extend(EMobility.MessageView, {
	
	title: "Search Charging Station",
	iconCls: "icon-x16-chargingactions",
	
	width: 850,
	height: 420,	
	minHeight: 420,
	maximized: true,
	resizeable: true,
	
	DEFAULT_RADIUS : 7000, 	// Radius in metres
	STATUS_MAP : EMobility.constants.STATUS_MAP,
	
	initId: function() {
		this.supr().initId.call(this);
		
		return Ext.apply(this._ID, {
			SearchForm 	: Ext.id(),		
			MapPanel 	: Ext.id(),
			ResultGrid 	: Ext.id(),
			SouthPanel 	: Ext.id(),
			dirLink     : Ext.id()
		});
	},
	
	initComponent: function() {
	
		var _p = this,
		
		/*
		 * Has to be defined here as unique id's are required 
		 * per instantiation of the window
		 */ 
		ID = this.initId();
		
		Ext.apply(this, {
			layout: "border",
			defaults: { border: false },
			items: [
			this.buildErrorPanel(),
			{
				id: _p._ID.SearchForm,
				region: "west",
				split: true,
				collapsible: true,
				collapseMode: "mini",
				header: false,
				xtype: "emo-ux-location-search-panel",
				listeners:{
					"searchclick" : function(location, range){
						
						if(Ext.isEmpty(location)){
							_p.showChargersInRange.defer(10, _p, [location, range]);
						}
					},
					"locationselected" : function(location, range){
						_p.showChargersInRange.defer(10, _p, [location, range]);
					}
				}
			},
			{
				id: _p._ID.SouthPanel,
				region: "center",
				layout: "fit",
				header: false,
				items:{
					title: "Charging Spots",
					xtype: "grid",
					id: _p._ID.ResultGrid,
					autoScroll: true,
					loadMask: true,
					viewConfig:{
						forceFit: true,
						emptyText: "No charging stations found!"
					},
					
					sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
					
					store: new Ext.data.JsonStore({
						autoLoad: false,

						proxy: new Ext.ux.data.DwrProxy({
							apiActionToHandlerMap: {
								read: {
									dwrFunction: SpotAggregatorService.findSpotAggregators,						
									getDwrArgsFunction: function() {
										var f = {};
										
										if(_p.location && _p.location.position){
											f = _p.location.position;
											f.radiusInMeter = _p.range;
										}
										
										return [f];
									},
									exceptionScope: _p
								}
							}
						}),

						idProperty : "uid",
						
	            		fields: EMobility.constants.fields.SpotAggregator
					}),
					listeners: {
						rowdblclick: function (grid, idx, e){
							_p.showAddEditDialog(grid.getStore().getAt(idx).data);
						}
					},
					columns: [
						new Ext.grid.RowNumberer(),
                        {
                            width: 33,
                            fixed: true,
                            dataIndex: "",
                            align: 'center',                                
                            renderer : function(value, metaData, record) {
                                var cls = EMobility.constants.getAggregatorCls(record.data);                                    
                                return "<div class='emo-grid-plug-wrap " + cls +"'></div>";
                            }
                        },
						{	
							header: "ID",
							dataIndex: "logicalId",
							sortable: true
							
						},{
							header: "Location",
							dataIndex: "address",
							menuDisabled:true,
							width: 250,
							renderer:function(value, metaData, record, rowIndex, colIndex, store){
								value = (value && !Ext.isEmpty(value.address)) ? value.address : "";
								
								metaData.attr = "title='"+ Ext.util.Format.htmlEncode(value) + "'";
								
								return value;
							}
						},							{	
							header: "Physical ID",
							dataIndex: "physicalId",
							sortable: true
						},{
							header: "Type",
							dataIndex: "type",
							menuDisabled:true,
							renderer: EMobility.constants.lookupRenderer
						},
						{
							header: "Status of Spots",
							width: 200,
							dataIndex: "chargingSpots",
							sortable: true,
							renderer:function(value, metaData, record, rowIndex, colIndex, store){
								return _p.generateStatusSummaryText.call(_p, value);
							}
						},
                        {
                            header: "Access",
                            width: 100,
                            dataIndex: "accessRestriction",
                            menuDisabled:true,
                            renderer : EMobility.constants.lookupRenderer
                        },
						{
							header:"Actions",
							dataIndex: 0,
							sortable: true,
							renderer : EMobility.constants.removeIconRenderer,
							listeners:{
								click: function(){
									var g = _p.getResultGrid(),
										r = g.getSelectionModel().getSelected();
										
									if(r){
										SpotAggregatorService.deleteSpotAggregator(r.get("uid"),{
											callback: function(){
												g.store.remove(r);
												g.store.commitChanges();
												
												g.store.reload();
											},
											exceptionScope: _p
										});
									}
								}
							}
						}
					],
					tbar: {
						buttonAlign: 'left',
						style: 'padding: 3px 0px;',
						defaults: { style: 'padding-right: 7px;' },
						items: [
						{
							text: 'Add',
							iconCls: 'icon-silk-add',
							
							handler: function(button, event) {
								_p.showAddEditDialog();
					      	}
						}]
					}
				}
			}],
			listeners:{
				afterrender: function(){
					var c = this.getSearchPanel(),
						sp = c.getSearchPanel();
					
					var radius = c.getRadiusCombo();
					radius.setValue(7000);
					radius.hide();
					
					sp.setHeight(100);
					
					_p.showChargersInRange.defer(1000, _p, [null, null]);
					
					_p.locateWindow.defer(1000, _p);
				}
			}
		});
		
		this.supr().initComponent.call(this);
	},
	showChargersInRange: function(location, range){
		var _p 		 = this;
		
		_p.location = Ext.isEmpty(location) ? null : location;
		_p.range = range;

		var g = _p.getResultGrid();
		
		if(g){
			g.getStore().load();
		}
	 },
	getResultGrid: function(){
	 	return Ext.getCmp(this._ID.ResultGrid);
	},
    getSearchPanel: function(){
    	return Ext.getCmp(this._ID.SearchForm);
    },
    setResultGridData: function(data){
    	var _p = this;
    	var resultGrid 	= _p.getResultGrid();
    	var southPanel = Ext.getCmp(_p._ID.SouthPanel);
		
		_p.chargerResult = data;    	
    	
		resultGrid.getStore().loadData(data || {});
		
		resultGrid.show();  
		
		if(southPanel.collapsed){
			southPanel.expand();
		}
    },
	showChargers: function (data) {
		var _p 			= this;
		
		_p.setResultGridData.defer(10,_p,[data]);
	},
	showAddEditDialog:function(item){
		var _p = this;
		
		var model = { chargingStation: item	};
		var title = item ? "Edit Charging Station - [" + Ext.util.Format.htmlEncode(item.logicalId) + "]" : "Add Charging Station";
		
		if(!_p.win){
			var win;
			
			// TODO Crude way to locate previous window
            _p.win = _p.locateWindow();
            
			if(!_p.win){
				win = $webtop.getViewAsWindow({
					id: "view.ChargingStationDialog",
					model: model,
					config:{
						title: title,
						parent: "EDIT_CS_DLG",
						isAdd : !item 
					}
				});
				
				// if still we dont have a window! flag error
				if(!win){
					Ext.Msg.show({
						title:   "Error",
						msg: 	 "There was a problem opening the details dialog. Reloading the page may resolve the issue.",
						buttons: Ext.Msg.OK,
						icon:    Ext.Msg.ERROR
					});
					return;
				}
			}
		}
		
		if(_p.win){
            Ext.Msg.show({
                title:   "Error",
                msg:     "You can only add/edit one charging station at a time!",
                buttons: Ext.Msg.OK,
                icon:    Ext.Msg.ERROR,
                fn: function(){ _p.win.show(); }
            });
            return;
		}
		
		if(win){
			_p.el.mask((item ? "Edit" : "Add") + " charging station in progress...");
			
			_p.win = win;
			
			win.on("close", _p.refresh, _p);
			win.show();
		}else 
		if(_p.win){
			var c = _p.win.items.items[0];
			
			c.setModel.call(c, model);
			_p.win.setTitle(title);
			_p.win.show(); // Required if window is minimized
		}
	},
	locateWindow: function(){
		var win,windowMgr = $cmp.windowManager(), _p = this;
        
        windowMgr.each(function(w) {
            if (w.xwt_viewid == "view.ChargingStationDialog" && 
                w.parent == "EDIT_CS_DLG") {
                win = w;

                w.on("close", _p.refresh, _p);
                
                _p.el.mask((w.isAdd  ? "Edit" : "Add") + " charging station in progress...");
                
                return false;
            }
        });
        
        return win;
	},
	refresh: function(){
		var _p = this;
		
		delete _p.win;
		
		_p.el.unmask();
		
		_p.showChargersInRange(_p.location, _p.range);
	},
	generateChargerStatusLed:function(value){
		var _p = this;
		var statusLed;
		
		if(!Ext.isEmpty(value)){
			if(!value.statusLed){
				if(Ext.isArray(value)){
					var statuses = [];
					
					Ext.each(value, function(item){
						var status = _p.STATUS_MAP[item.status.id];
						
						if(!status){
							status = _p.STATUS_MAP.UNKNOWN;
						}
		
						// TODO Check whether escape is enough ?
						status = "<div class='"+ status.iconCls +
								 "' style='width:16px;height:16px;' title='"+ 
								 Ext.util.Format.htmlEncode(status.text) +"'></div>"
						
						statuses.push(status);
					});
				
					value.statusLed = "<table><tr><td>" + statuses.join("</td><td>") + "</td></table>";
				}
			}
			
			statusLed = value.statusLed;
		}
		
		return  Ext.isEmpty(statusLed) ? "Not Available" : statusLed;
	},
    generateStatusSummaryText:function(value){
        var _p = this;
        var statusSummary;
        
        if(!Ext.isEmpty(value)){
            if(!value.statusSummary){
                if(Ext.isArray(value)){
                    var statusCnt = {};
                    var summary = [];
                    
                    Ext.each(value, function(item){
                        
                        var status = item.status ? item.status.id : EMobility.constants.ChargingSpotStatus.UNKNOWN;
                        
                        if(!statusCnt[status]){
                           statusCnt[status] = item.status;
                           statusCnt[status].cnt = 0;
                        }
                        
                        statusCnt[status].cnt++;
                    });
                    
                    Ext.iterate(EMobility.constants.ChargingSpotStatus, function(status, idx){
                        var stat = statusCnt[status];
                        var text;
                        
                        if(stat){
                            text = "<span style='color:" + _p.STATUS_MAP[stat.id].textColor + "'>" + 
                                       Ext.util.Format.htmlEncode(stat.value) + ":&nbsp;" + stat.cnt +
                                   "</span>";
                            
                            summary.push(text);
                        }
                    });
                    
                    value.statusSummary = summary.join("&nbsp;");
                }
            }
            
            statusSummary = value.statusSummary;
        }
        
        return statusSummary;
    }
});