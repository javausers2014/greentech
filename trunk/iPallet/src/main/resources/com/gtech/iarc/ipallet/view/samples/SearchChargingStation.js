Ext.ns("EMobility");

/**
 * Provides search functionality for the charging stations based on the 
 * location specified. Uses map to provide the location search. 
 * 
 * @author Ankur Kapila
 * @class EMobility.SearchChargingStation
 * @extends EMobility.View
 */
EMobility.SearchChargingStation = Ext.extend(EMobility.MessageView, {
	
	title: "Search Charging Station",
	iconCls: "icon-x16-chargingactions",
	
	width: 850,
	height: 420,	
	minHeight: 420,
	maximized: true,
	resizeable: true,
	
	STATUS_MAP : EMobility.constants.STATUS_MAP,
	
	directionsService : null,
	directionsRenderer: null,
	directionWindow  :  null,
	
	initId: function() {
		EMobility.SearchChargingStation.superclass.initId.call(this);
		
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
				region: "center",				
				split: true,
				xtype: 'gmappanel',
				id: 	_p._ID.MapPanel,
				zoomLevel: 11,
				gmapType: 'map',
                center:{
                	geoCodeAddr: $webtop.getCustomContext().map.center
				},
				listeners:{
					"mapready" : function(mapPanel, map){
						google.maps.event.addListener(
							map, 'click', 
							function(gMouseEvent) {
								_p.handleMapClick.defer(50,_p, [gMouseEvent.latLng]);
							}
						);

						// Flag Map Ready
                        _p.mapready = true;						
						
                        // Setup Directions Service & Directions Renderer
						_p.directionsService = new google.maps.DirectionsService();
						_p.directionsRenderer = new google.maps.DirectionsRenderer({
							preserveViewport : true,
							suppressMarkers : true,
							polylineOptions : {
								strokeColor : "#2E2EFE"
							}
						});
						
						// Directions Window
						_p.directionWindow = new Ext.Window({
							autoScroll: true,
							forceLayout: true,
							hidden: true,
							closeAction: "hide",
							items:{
								layout: "fit",
								style: { padding : "5px" }
							},
							title: "Driving directions",
							fbar : {
								items:{
									xtype: "button",
									text : "close",
									handler : function(button, event) {
										this.ownerCt.ownerCt.hide();
									}
								}
							}
						});
						
						_p.directionWindow.render(Ext.getBody());
						_p.directionsRenderer.setPanel(_p.directionWindow.getComponent(0).getContentTarget().dom);
						_p.directionsRenderer.setMap(_p.getMap());
						
						// Store the reference to initial center for centering the map later
						_p.initialCenter = mapPanel.getCenter();
						
						if(_p.data){
						  _p.showChargersOnMap(_p.data);
						}
					},
					scope: _p
				}
			},
			{
				id: _p._ID.SearchForm,
				region: "west",
				split: true,
				collapsible: true,
				collapseMode: "mini",
				header: false,
				xtype: "emo-ux-location-search-panel",
				listeners:{
					searchclick : function(location, range){
                        if(Ext.isEmpty(location)){
                            _p.getChargersInRange.defer(10, _p, [location, range]);
                        }
					},
					locationselected : function(location, range){
						_p.getChargersInRange.defer(10, _p, [location, range]);
					}
				}
			},
			{
				id: _p._ID.SouthPanel,
				region: "south",
				split: true,
				layout: "fit",
				collapsible: true,
				collapseMode: "mini",
				header: false,
				height: 200,
				items:{
					title: "Charging Spots",
					xtype: "grid",
					id: _p._ID.ResultGrid,
					autoScroll: true,					
					viewConfig:{
						emptyText: "No results found!"
					},
					loadMask : true,
                    store: new Ext.data.JsonStore({
                        proxy: new Ext.ux.data.DwrProxy({
                            apiActionToHandlerMap: {
                                read: {
                                    dwrFunction: SpotAggregatorService.findSpotAggregators,                      
                                    getDwrArgsFunction: function(s) {
                                        return [s.params];
                                    },
                                    exceptionScope: this
                                }
                            }
                        }),
                        listeners: {
                            load: _p.afterload.createDelegate(_p)
                        },
                        fields: EMobility.constants.fields.SpotAggregator
                    }),
					listeners: {
						rowclick:    _p.onStationClick.createDelegate(_p),
						rowdblclick: _p.onStationDblclick.createDelegate(_p)
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
								sortable:true
								
							},{
								header: "Location",
								dataIndex: "address",
								menuDisabled:true,
								width: 250,
								renderer:function(value, metaData, record, rowIndex, colIndex, store){
									
									value = (value && !Ext.isEmpty(value.address)) ? value.address : "";
									metaData.attr = "title='" + value +"'"
									return value;
								}
							},							{	
								header: "Physical ID",
								dataIndex: "physicalId",
								sortable:true
							},{
								header: "Type",
								dataIndex: "type",
								menuDisabled:true,
								renderer : EMobility.constants.lookupRenderer
							},
							{
								header: "Status of Spots",
								width: 200,
								dataIndex: "chargingSpots",
								sortable:true,
								renderer:function(value, metaData, record, rowIndex, colIndex, store){
									return _p.generateStatusSummaryText.call(_p, value);
								}
							},
                            {
                                header: "Access",
                                width: 100,
                                menuDisabled:true,
                                dataIndex: "accessRestriction",
                                renderer : EMobility.constants.lookupRenderer
                            }
						],
					sm: new Ext.grid.RowSelectionModel({singleSelect: true})
				}
			}]
		});
		
		EMobility.SearchChargingStation.superclass.initComponent.call(_p);
		
		this.on("afterrender", function(){
			_p.getChargersInRange();
		}, _p)
	},
	afterload:function(store){
        var _p = this;

        if(store){
        	_p.setData(store);
        }
    },
    setData:function(store){
        this.data = store.reader.jsonData;
        
        this.showResultGrid(true);
        this.showChargersOnMap(this.data || {});
    },
    getChargersInRange: function(address, range){
        var view     = this,
            search   = {},
            mapPanel = view.getMapPanel(),
            map      = mapPanel.getMap(),
            grid     = view.getResultGrid(),
            store    = grid.getStore();

        // Remove old reference
        delete this.data;

        view.address = address;
        view.range = range;
            
        // On Search without address
        if(address){
            search = address.position;
            search.radiusInMeter = range;            
        }
        
        store.load({params: search});
    },
    showChargersOnMap: function (data) {
        
        if(this.mapready){    // If map is ready
        
            var view        = this,
                mapPanel    = view.getMapPanel(),
                map         = mapPanel.getMap(),
                ds          = view.directionsService,
                dr          = view.directionsRenderer,
                center;

            mapPanel.clearOverlays();
            
            // Remove street view
            map.getStreetView().setVisible(false);
            
            if(dr){
                dr.setMap(null);// Clear any rendered directions
            }
            
            mapPanel.panTo(mapPanel.getCenter());
            
            if(view.address){
                var position    = view.address.position;
                
                center = new google.maps.LatLng(position.latitude, position.longitude);
                
                mapPanel.addMarker(center, {
                    infoWindow : {
                        content: "<div><b>" + Ext.util.Format.htmlEncode(view.address.address) +"</b><br></div>"
                    }
                }, true, true);
                
                mapPanel.drawCircleInverted(center, view.range, true, true, "#333333", "1", "0.5", "#000000", "0.4");
    		}else{
    			mapPanel.setCenter(this.initialCenter, mapPanel.zoomLevel);
    		}
            
            if(!Ext.isEmpty(data)){
                Ext.each(data, 
                    function(item, idx){
                        var position = item.address.position;
                        var point    = new google.maps.LatLng(position.latitude, position.longitude);
                        
                        var marker = {
                            title : Ext.util.Format.htmlEncode(item.address.address),           
                            icon :  EMobility.constants.getSpotAggregatorMapMarker(item),
                            
                            listeners:{
                                click: view.handleMarkerClick.createDelegate(view, [item, mapPanel, map, idx])
                            }
                        };
                        
                        marker = mapPanel.addMarker(point, marker, false, false, marker.listeners);
                        
                        item.marker = marker;
                        item.point  = point;
                        
                        if(center && ds){
                            var dirRequest = {
                                origin : center,
                                destination : point,
                                travelMode : google.maps.DirectionsTravelMode.DRIVING,
                                unitSystem : google.maps.DirectionsUnitSystem.METRIC
                            };
                            
                            ds.route(dirRequest, function(response, status) {
                                var metrics = { };
                                
                                if (status == google.maps.DirectionsStatus.OK) {
                                    metrics = view.getDrivingMetrics(response.routes[0]);
                                    
                                    item.directionsResult = response;
                                }
                                
                                Ext.apply(item, metrics);
                                
                                item.infoWindow = mapPanel.createInfoWindow({
                                    content: view.getInfoWindowContent(item) 
                                }, point);
                            });
                        }else{
                            item.infoWindow = mapPanel.createInfoWindow({
                                    content: view.getInfoWindowContent(item) 
                            }, point);
                        }
                    }
                );
            }
        }
    },
	getMapPanel : function (){
	 	return Ext.getCmp(this._ID.MapPanel);
	},
	getResultGrid: function(){
	 	return Ext.getCmp(this._ID.ResultGrid);
	},
    getSearchPanel: function(){
    	return Ext.getCmp(this._ID.SearchForm);
    },
    showResultGrid: function(){
    	var _p = this,
    	    resultGrid = _p.getResultGrid(),
    	    southPanel = Ext.getCmp(_p._ID.SouthPanel);
		
		resultGrid.show();  
		
		if(southPanel.collapsed){
			southPanel.expand();
		}
    },
	getMap: function(){
		return this.getMapPanel().getMap();
	},
	handleMapClick : function(location){
		return;
		var _p 		      = this;
		
		var mapPanel      = _p.getMapPanel();
		var searchPanel   = _p.getSearchPanel()
		
		var searchAddress = { 
			position : { 
				latitude  : location.lat(),
				longitude : location.lng()
			}, 
			address: "Unknown" 
		};
		
		GeocodeService.reverseGeocode(location.lat(), location.lng(), {
			callback: function(data){
				if(!Ext.isEmpty(data) && Ext.isArray(data)){					
					searchAddress = data[0];
				}
				_p.getChargersInRange(searchAddress, searchPanel.getRadius());
			},
			exceptionScope: _p
		})
	},
	getDrivingMetrics: function (route){

		var metric;
		
		if(route && route.legs && Ext.isArray(route.legs)){
			metric = {
				travelDistance: route.legs[0].distance.text,
				travelTime: route.legs[0].duration.text
			}
			
			if(route.legs.length > 1){
				if(window.console){
					console.warn("Mutiple legs for journey!" + route);
				}
			}
		}

		return metric;
	},
	handleMarkerClick: function(item, mapPanel, map, idx){
        var _p = this;
        var dr = _p.directionsRenderer;
        
        mapPanel.hideAllInfoWindows();
        
        if(item.infoWindow){
        	item.infoWindow.open(map);
        	
        	var dirLink = Ext.get(_p.id + _p._ID.dirLink);
        	if(dirLink){
        		dirLink.on("click", _p.showDirectionsWindow.createDelegate(_p));
        	}
        }
        
        if(dr && item.directionsResult){
            dr.setDirections(item.directionsResult);

            if(!dr.getMap()){
               dr.setMap(_p.getMap());
            }
        }
        
        var grid = _p.getResultGrid();
        
        grid.getSelectionModel().selectRow(idx);
        grid.getView().focusRow(idx);
	},
	getInfoWindowContent : function(item){
		var msg= "";

		if(item){
			msg  = "<div>" +
				   "<b>"             + Ext.util.Format.htmlEncode(item.address.address) + "</b><br><br>" +
				   "<b>Type:</b> "   + Ext.util.Format.htmlEncode(item.type.value) + "<br>" +
				   "<b>Access:</b> " + Ext.util.Format.htmlEncode(item.accessRestriction ? item.accessRestriction.value : "Not Available") + "<br>" +
				   (item.travelDistance ? "<b>Distance:</b> " + item.travelDistance : "") + 
				   (item.travelTime ? "&nbsp; ~" + item.travelTime  + " <a href='javascript:void(0)' id='"+ (this.id + this._ID.dirLink) +"'> View Directions </a>" : "")+
				   "<br><b>Status: </b> " + (this.generateStatusSummaryText(item.chargingSpots)) +
				   "</div>";
		}
		return msg;
	},
	showDirectionsWindow: function(){		
		this.directionWindow.show();
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
	},
	onStationClick: function (grid, rowIndex, e) {
		var _p = this,
			mapPanel = Ext.getCmp(_p._ID.MapPanel);
		
		if(_p.data){
			var item = _p.data[rowIndex];
			
//			mapPanel.panTo(item.point);
			
			google.maps.event.trigger(item.marker, "click");
		}
	},
	
	onStationDblclick: function (grid, rowIndex, e) {
		var _p = this,
		   item = Ext.apply({}, this.data[rowIndex]);
		
		var model = { readOnly: true, chargingStation: item };
		var title = "Charging Station - [" + Ext.util.Format.htmlEncode(item.logicalId) + "]";
		
		if(!_p.win){
			var win;
			
			// TODO Crude way to locate previous window
			var windowMgr = $cmp.windowManager();
			
			windowMgr.each(function(w) {
				if (w.xwt_viewid == "view.ChargingStationDialog" && 
					w.parent == "VIEW_CS_DLG") {
					_p.win = w;
					
					w.on("close", function(){ delete _p.win; }, _p);
				}
			});			
			
			if(!_p.win){
				win = $webtop.getViewAsWindow({
					id: "view.ChargingStationDialog",
					model: model,
					config:{
						title: title,
						parent: "VIEW_CS_DLG"
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
			
		if(win){
			_p.win = win;
			
			win.on("close", function(){ delete _p.win; }, _p);
			
			win.setTitle(title);
			win.show();
		}else 
		if(_p.win){
			var c = _p.win.items.items[0];
			
			c.setModel.call(c, model);
			
			_p.win.setTitle(title);
			_p.win.show();
		}
	}
});