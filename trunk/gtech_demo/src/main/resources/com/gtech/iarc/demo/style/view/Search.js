Ext.ns("EMobility.Intuity");

EMobility.Intuity.Search = Ext.extend(EMobility.Intuity.View, {
	toolbarTitle: "<@i18nText key='search.title' />",
	
	initId: function() {
		return this._ID = {
			// Search form
			Location 	: Ext.id(),
			Radius 		: Ext.id(),
			
			MapPanel	: Ext.id(),
			ResultGrid	: Ext.id(),
			ChoiceGrid	: Ext.id(),
			ProfileLink : Ext.id(),
			InfoMsgPanel : Ext.id()
		};
	},
	
	STATUS_MAP : EMobility.constants.STATUS_MAP,
	
	directionsService : null,
	directionsRenderer: null,
	directionWindow  :  null,
	
	initView: Ext.emptyFn,
	
	/* Search Toolbar */
	getLocation: function() {
		return Ext.getCmp(this._ID.Location).getValue();
	},
	
	getAddressGrid: function() {
		return Ext.getCmp(this._ID.ChoiceGrid);
	},
	 
	getRadius : function() {
		return Ext.getCmp(this._ID.Radius).getValue();
	},
	
	getMap: function(){
		return this.getMapPanel().getMap();
	},
	 
	getMapPanel : function (){
	 	return Ext.getCmp(this._ID.MapPanel);
	},
	
	getResultGrid: function(){
	 	return Ext.getCmp(this._ID.ResultGrid);
	},
	
	setData:function(store){
		this.data = store.reader.jsonData;
		
		this.showResultGrid(true);
//		this.setResultGridData(data);
		this.showChargersOnMap(this.data || {});
	},
	
	setResultGridData: function(data){
		this.getResultGrid().getStore().loadData(data || {});		

		// Show Search results GRID
		this.showResultGrid(true);
    },
    
    getChargersInRange: function(address, range){
        var view     = this,
            search   = {},
            mapPanel = view.getMapPanel(),
            map      = mapPanel.getMap(),
            grid     = view.getResultGrid(),
            store    = grid.getStore();
        

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
		
    		var view 	    = this,
                mapPanel	= view.getMapPanel(),
                map 		= mapPanel.getMap(),
                ds          = view.directionsService,
                dr          = view.directionsRenderer,
                center;
            
            // Clear Overlays i.e markers and info windows
            mapPanel.clearOverlays();
            
            // Remove street view
            map.getStreetView().setVisible(false);
            
            if(dr){
                dr.setMap(null); // Clear any rendered directions
            }		
    		
    		if(view.address){
                var position = view.address.position;
                
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
	
	handleMapClick : function(location){
		// return;
		var view 		  = this;
		
		var mapPanel      = view.getMapPanel();
		var searchPanel   = view;
		
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
				view.getChargersInRange(searchAddress, searchPanel.getRadius());
			},
			exceptionScope: view
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
        var view = this;
        var dr = view.directionsRenderer;
        
        mapPanel.hideAllInfoWindows();
        
        if(item.infoWindow){
        	item.infoWindow.open(map);
        	
        	var dirLink = Ext.get(view.id + view._ID.dirLink);
        	if(dirLink){
        		dirLink.on("click", view.showDirectionsWindow.createDelegate(view));
        	}
        }
        
        if(dr && item.directionsResult){
            dr.setDirections(item.directionsResult);

            if(!dr.getMap()){
               dr.setMap(view.getMap());
            }
        }
        
        var grid = view.getResultGrid();
        
        grid.getSelectionModel().selectRow(idx);
        grid.getView().focusRow(idx);
	},
	
	onStationClick: function (grid, rowIndex, e) {
		if(this.data && this.mapready){
			var item = this.data[rowIndex];
			
//			mapPanel.panTo(item.point);
			
			google.maps.event.trigger(item.marker, "click");
		}
	},
	
	getInfoWindowContent : function(item){
		var msg= "";

		if(item){
			msg  = "<div>" +
			       (item.type && item.type.id == "PARK_AND_CHARGE" ? "<b>" +
			       		"<@i18nText key='search.quickchargingstation.text' />" +
			       		"</b><br><br>":"") +
				   "<b>" + Ext.util.Format.htmlEncode(item.address.address) + "</b><br><br>" +
				   // EMSG-289: Remove type: 
				   // "<b>Type:</b> " + item.type + "<br>" +
				   "<b><@i18nText key='search.access.text' />:</b> " + Ext.util.Format.htmlEncode(item.accessRestriction ? item.accessRestriction.value : "Not Available") + "<br>" +
				   (item.travelDistance ? "<b>Distance:</b> " + item.travelDistance : "") + 
				   (item.travelTime ? ("&nbsp; ~" + item.travelTime  + (this.directionWindow ? " <a href='javascript:void(0)' id='"+ this.id + this._ID.dirLink +"'> View Directions </a>" : "" )) : "")+
				   "<br><b><@i18nText key='search.spotstatus.text' />: </b> " + (this.generateStatusSummaryText(item.chargingSpots)) +
				   "</div>";
		}
		return msg;
	},
	
	showDirectionsWindow: function() {
		if(this.directionWindow){
    		this.directionWindow.syncSize();
    		this.directionWindow.show();
		}
	},
	
	generateStatusSummaryText: function(value) {
        var view = this;
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
                        	text = "<span style='color:" + view.STATUS_MAP[stat.id].textColor + "'>" + 
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
	
	showAddressGrid: function() {
		this.showResultGrid(false);
	},
	
	/*
	*	Show Result Grid and hide Did-You-Mean Grid and vice versa.
	*	Only one panel can be displayed at a time.
	*/
	showResultGrid: function(show) {
		var addressList = this.getAddressGrid(),
			resultList = this.getResultGrid();
			
		if (show) {
			addressList.hide();
			resultList.show();
		} 
		else { 
			addressList.show();
			resultList.hide();
		}
		
		addressList.ownerCt.doLayout(true);
	},
	
	search: function() {
		var view = this,		
			location = view.getLocation(),
			radius   = view.getRadius();
		
		delete this.data;
		
		// Show Result Grid
		// this.showResultGrid(true);
		// Show loading icon
		this.showResultGrid(true);
		
		// this.fireEvent("searchclick", location, radius, view.getSearchForm(), view);		
		this.searchclick(location, radius);
		
	 	if(!Ext.isEmpty(location)) {
			GeocodeService.geocode(location, {
				callback: function(data) {
					if (!Ext.isEmpty(data) && Ext.isArray(data)) {
						if(data.length > 1) {
							view.searchData = data;
							
							view.showAddressSelectionPanel(data);
						} else {
							view.locationselected(data[0], radius);
						}
					} else {
						// TODO Display a better message
						$webtop.notifyInfo(
								"Search",
								"<br>No results found!<br>"+
								"Try using a different search criteria.<br>&nbsp;"
						);
					}
				}
			});
	 	}
	},

	/*
		Show hint address grid panel
	*/
	showAddressSelectionPanel: function(data) {
		var addressList  = this.getAddressGrid();
		addressList.getStore().loadData(data);		 
		
		var resultGrid = this.getResultGrid();
		resultGrid.getStore().loadData([]);		
		
		// Show Address Grid
		this.showAddressGrid();
	},
	
	setLocation: function(location){
	 	Ext.getCmp(this._ID.Location).setValue(location);
	},
	 
	resetView: function() {
		// Reset search form
		
		// Clear search results
	},
		
	/* Config functions */
	buildMainLeft: function() {
		var view = this,
			ID = this._ID;
		
		// Map Panel	
		return {
			// region: "center",
			split: true,
			xtype: 'gmappanel',
			id: 	ID.MapPanel,
			zoomLevel: 11,
			gmapType: 'map',
			center:{
				geoCodeAddr: $webtop.getCustomContext().map.center
			},
			listeners:{
				mapready : function(mapPanel, map) {
					google.maps.event.addListener(
						map, 'click', 
						function(gMouseEvent) {
							view.handleMapClick.defer(50,view, [gMouseEvent.latLng]);
						}
					);
					// Flag Map Ready
					view.mapready = true;
					
					view.directionsService = new google.maps.DirectionsService();
					view.directionsRenderer = new google.maps.DirectionsRenderer({
						preserveViewport : true,
						suppressMarkers : true,
						polylineOptions : {
							strokeColor : "#2E2EFE"
						}
					});
					
					view.directionWindow = new Ext.Window({
						width: 500,
						autoScroll: true,
						forceLayout: true,
						hidden: true,
						modal: true,
						closeAction: "hide",
						items:{
							layout: "fit",
							style: { padding : "5px" }
						},
						title: "Driving directions",
						fbar : {
							items:{
								xtype: "button",
								text : "<@i18nText key='common.button.close' />",
								handler : function(button, event) {
									this.ownerCt.ownerCt.hide();
								}
							}
						}
					});
					
					view.directionWindow.render(Ext.getBody());
					view.directionsRenderer.setPanel(view.directionWindow.getComponent(0).getContentTarget().dom);
					view.directionsRenderer.setMap(view.getMap());
					
					var mapPanel = view.getMapPanel();
					
					mapPanel.panTo(mapPanel.getCenter());				
					
					// Store the reference to initial center for centering the map later
					this.initialCenter = mapPanel.getCenter();
					
					if(view.data){
					   view.showChargersOnMap(view.data);
					}
				},
                scope: view
			}
		};
	},	
	
	buildMainRight: function() {
		var view = this,
			ID = this._ID;
		return {
			animate: true,
			header: false,
			collapsible: true,
			split: false,
			layout:'vbox',
			layoutConfig: {
				align : 'stretch',
				pack  : 'start'
			},		
			items: [
			    {
			    	id: ID.InfoMsgPanel,
			    	cls: "iarc-profile-info-message",
			    	xtype: "box",
			    	hidden: true
			    },
				{
					flex: 1,
					hidden: true,
					header: false,
					id: ID.ChoiceGrid,					
					xtype: "lite-grid",
					hidden: true,
					viewConfig: {
						forceFit: true, 
						headersDisabled: true
					},
					store: new Ext.data.JsonStore({
	            		fields: ["address"]
					}),
					columns:[
				         new Ext.ux.grid.RowNumberer(),
				         {
				         	id: "address",
				        	header: "Did you mean ?",
				        	dataIndex: "address",
				        	renderer: function(v, p){
				        		p.attr = "title='"+ Ext.util.Format.htmlEncode(v) +"'";
				        		return v;
				        	}
				         }
					],
					sm: new Ext.grid.RowSelectionModel({singleSelect: true}),					
					listeners: {
						rowclick : function(grid, rowIndex, e ){ 
							// Hide this Address Grid and show Result Grid
							view.showResultGrid(true);
							
							var location = view.searchData[rowIndex];
	
							view.setLocation(location.address);
							view.locationselected(location, view.getRadius());
						}
					}
				},							
				{
					flex: 1,
					header: false,
					xtype: 'lite-grid',
					loadMask : true,
					id: ID.ResultGrid,					
					viewConfig: { 
						forceFit: true,
						headersDisabled: true,
						emptyText: "<@i18nText key='search.resultgrid.emptytext' />" 
					},
					store: new Ext.data.JsonStore({
						storeId: "CSResultsStore" + view.id,						
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
                            load: view.setData.createDelegate(view)
                        },
                        fields: EMobility.constants.fields.SpotAggregator
					}),
					listeners: { rowclick: this.onStationClick.createDelegate(this) },
					colModel: new Ext.grid.ColumnModel({
						defaults: { sortable: false },
						columns: [
							
							{
								width: 30,
								fixed: true,
								dataIndex: "",
								align: 'center',
								id: 'iarc-grid-plug-status',
								renderer : function(value, metaData, record) {
									var cls = EMobility.constants.getAggregatorCls(record.data);									
									return "<div class='iarc-grid-plug-wrap " + cls +"'></div>";
								}
							},							
							{
								header: "<@i18nText key='search.resultgrid.location.header' />",
								dataIndex: "address",								
								renderer:function(value, metaData /*, record, rowIndex, colIndex, store */) {
									value = (value && !Ext.isEmpty(value.address)) ? value.address : "Not Available";
									metaData.attr = "title='" + Ext.util.Format.htmlEncode(value) + "'";
									
									return value;
								}
							},{
								header: "<@i18nText key='search.resultgrid.status.header' />",
								width: 100,
								fixed: true,
								dataIndex: "status",
								renderer: EMobility.constants.spotStatusTextRenderer
                            }
						]
					}),
					sm: new Ext.grid.RowSelectionModel({
						singleSelect: true
					})
				}
			]
		};
	},
	
	buildToolbarRight: function() {
		var view = this,
			ID = this._ID;
		
		return [
			// Left side			
			{
				id: ID.Location,
				width: 152,
				xtype: 'textfield',
				name: 'location',
				listeners: {
					specialkey: {
						fn: function(field, e){
							if(e.getKey() == e.ENTER){
								view.search();
							}
						},
						buffer: 350
					}
				}
			},
			{	xtype: 'tbspacer', width: 10	}, // add a 10px space
			{
				id: ID.Radius,
				width: 80,
				name: 'radius',
				xtype: 'select',
				mode: 'local',
				displayField: 'text',
				valueField: 'value',
				value: 5000,
				store: new Ext.data.SimpleStore({
					// TODO Move it to configuration if required?
					data: [
							['100 m', 100]  	, ['250 m', 250]  	, ['500 m', 500]		, ['1 km',	1000],
							['2 km' , 2000] 	, ['5 km',	5000]   , ['10 km', 10000], 
							['15 km', 15000]	, ['20 km', 20000]
						  ],
					autoLoad: true,
					fields: ['text', 'value']
				}),
				allowBlank: false
			},
			{	xtype: 'tbspacer', width: 10	}, // add a 10px space			
			{
				xtype: 'iarc-button',
				width: 60,				
				text: "<@i18nText key='common.button.search' />",
				listeners: {
					click: {
						fn: this.search.createDelegate(this),
						buffer: 500
					}
				}
			}
		];
	},
	
	searchclick : function(location, radius) {
		if(Ext.isEmpty(location)) {
			this.getChargersInRange.defer(10, this, [location, radius]);
		}
	},

	locationselected : function(location, radius) {
		this.getChargersInRange.defer(10, this, [location, radius]);
	},
	
	initComponent: function() {
		var view = this, ID = this.initId();
		
		EMobility.Intuity.Search.superclass.initComponent.call(this);
        
		this.on("afterrender", function(){
            view.search();
            view.showProfileInfoMessage();
		}, this);
	},
	
	showProfileInfoMessage: function(){
		var view = this;
		var ID = this._ID;
		
		SubscriptionService.getPricePlanSubscription({
			callback: function(data){
				var msg, subscriptionStatus = data.status ? data.status.id : null;
				
				if (subscriptionStatus == "WAITING_FOR_SUBSCRIPTION") {	
					msg = "<span>" + 
							"Your subscription is currently under approval. " +
							"You can start charging when the approval process is complete." +
							"</span>";					
					
				} else if (subscriptionStatus == "UNSUBSCRIBED" || subscriptionStatus == "INITIALIZED")  {					
					
					msg = "<span>" + 
							"Please complete your account information before charging is permitted.  <br /><br />" +
							"<a id='"+ ID.ProfileLink + "' href='javascript:void(0);'>" +
							"&gt; Complete Your Account" +	
							" </a>" +
							"</span>";
				}
				
				if(msg && !Ext.isEmpty(msg)){					
					Ext.getCmp(ID.InfoMsgPanel).update(msg);
					Ext.getCmp(ID.InfoMsgPanel).setVisible(true);
					
					var profileLink = Ext.get(ID.ProfileLink);
					if (profileLink) {
						profileLink.on("click", view.showProfile.createDelegate(view));
					}
				}
			},
			scope: view,
			exceptionScope: view
		});		
	},
	showProfile: function(){
		/* Temporary  fix to toggle Profile button state */
		Ext.getCmp("iarc-toolbar-profile-btn").toggle(true,false);
		
		$webtop.showWindow("view.Intuity.Profile",{},null, InteractionModel.TRADITIONAL);
	}
});