Ext.ns("EMobility");

/**
 *
 * Note: _p stands for 'prototype', and used throughout in place of 'this'
 * 
 * @class EMobility.ClassName
 * @extends EMobility.View
 */

EMobility.ChargingStationDetail = Ext.extend(EMobility.MessageView, {
	
	title: "Location",
	iconCls: "icon-x16-chargingactions",

	width: 850,
	height: 420,

	initId: function() {
		this.supr().initId.call(this);
		
		return Ext.apply(this._ID, {
			MapPanel    	: Ext.id(),		
			Grid	    	: Ext.id(),
			FillLink    	: Ext.id(),
			MainForm        : Ext.id(),
			AddressForm 	: Ext.id(),
			GeoPositionForm : Ext.id(),
			Comments		: Ext.id()
		});
	},
	
	initComponent: function() {
		
		var _p = this,
			_ID = this.initId();
		
		_p.model = _p.model || {};
		
		_p.readOnly = _p.model.readOnly;

		var removeIconRenderer = EMobility.constants.removeIconRenderer;
		
		_p._STORES = {};
		
		_p._STORES.spotTypeStore = new Ext.data.JsonStore({
			fields : ["id", "value"],
			proxy: new Ext.ux.data.DwrProxy({
				apiActionToHandlerMap: {
					read: {
						dwrFunction: SpotAggregatorService.getSpotAggregatorTypes,
						getDwrArgsFunction: function(s) { return []; }
					},
					exceptionScope: _p
				}
			})
		});		
		
		
		_p._STORES.spotDetails = new Ext.data.JsonStore({
			fields : EMobility.constants.fields.ChargingSpot
		});

	
		Ext.apply(_p, {			
			layout: "border",
			border: false,			
			items:[
			this.buildErrorPanel(), 
			{
				region: "west",				
				autoScroll: true,
				width : 250	,
				padding: 10,
				split: true,
				defaults:{
					xtype:"form",
					border: false,
					labelSeparator: '',
					layout: "form",
					labelWidth: 70,

					defaults: {
						anchor: '100%',
						xtype: 'textfield',
						allowBlank: false
					}					
				},
				
				items:[
				{
					id: _p._ID.MainForm,
					items: [
					{
						fieldLabel: "Type",
						name: "type",
						xtype:"select",
						valueField: "id",
						displayField: "value",
						store: _p._STORES.spotTypeStore,
						
						readOnly: _p.readOnly						
					},{
						fieldLabel: "ID",
						name: "logicalId",
						readOnly: _p.readOnly
					},{
                        fieldLabel: "Physical ID",
                        name: "physicalId",
                        readOnly: _p.readOnly
					},{
                        fieldLabel: "Restricted",
                        name: "accessRestriction",
                        xtype: "checkbox",
                        readOnly: _p.readOnly
					}
					]
				},
				{
					id: _p._ID.AddressForm,

					items:[
					{
						fieldLabel: "Street",
						name: "street",
						readOnly: _p.readOnly
					}
					,{
						fieldLabel: "Number",
						name: "streetNumber",
						readOnly: _p.readOnly
						
					},{
						fieldLabel: "Zip",
						name: "zipCode",
						readOnly: _p.readOnly
					},{
						fieldLabel: "City",
						name: "city",
						readOnly: _p.readOnly
					},{						
						fieldLabel: "Country",
						name: "country",
						readOnly: _p.readOnly
					}]
				}
				,{
					items:{
						xtype: "button",
						iconCls: "icon-x16-search",
						text: "Show address on map",
						tooltip: "Show location on map using Address",
						handler: _p.searchByAddress.createDelegate(_p),
						style: {
							padding: "5px"
						}						
					}
				},
				{
					id: _p._ID.GeoPositionForm,
					items:[{
						fieldLabel: "Latitude",
						name: "latitude",
						xtype: "textfield",
						validator: EMobility.constants.latValidator,
						readOnly: _p.readOnly
					},{
						fieldLabel: "Longitude",
						xtype: "textfield",
						name: "longitude",
						validator: EMobility.constants.lngValidator,
						readOnly: _p.readOnly
					}]
				}
				,{
					id: _p._ID.Comments,					
					items:{
						allowBlank: true,
						fieldLabel: "Comment",
						name: "note",						
						xtype: "textarea",
						height: 100,
						autoAcroll: true,
						readOnly: _p.readOnly
					}
				}]
			},
			{
				region: "center",
				border: false,
				layout: "border",
				items:[
					{
						region: "center",
						xtype: 'gmappanel',
						id: 	_p._ID.MapPanel,
						zoomLevel: 11,
						gmapType: 'map',
		                center:{
		                	geoCodeAddr: $webtop.getCustomContext().map.center
						},
						listeners:{
							"mapready" : function(mapPanel, map){
								_p.mapLoaded = true;
								
								_p.showChargerLocation.call(_p);
							}
						}
					},{
						region: "south",

						id: _p._ID.Grid,
						xtype: "editorgrid",
						height: 100,
						// minHeight: 50,
						split: true,
						loadMask: true,
						
						viewConfig: {
							forceFit: true,
							emptyText: "No charging spots to display."
						},
						stripeRows: true,
						autoExpandColumn: 2,
						store: _p._STORES.spotDetails,
						sm : new Ext.grid.RowSelectionModel({
							singleSelect : true
						}),
						columns: [
							new Ext.grid.RowNumberer(),
							{
								header: "Spot ID",
								dataIndex: "logicalId",
								editor: new Ext.form.TextField(),
								editable: !_p.readOnly
							},{
								header: "Comments",
								dataIndex: "note",
								editor: new Ext.form.TextField(),
								editable: !_p.readOnly
							},
							{								
								header : "Actions",
								width : 50,
								tooltip : "Remove Spot",
								hidden: _p.readOnly,
								renderer : EMobility.constants.removeIconRenderer,
								listeners:{
									click: function(){
										Ext.Msg.show({
										   title:'Delete Charging Spot',
										   msg: "The device will go offline immediately " +
												"and can be neither used nor controlled anymore! " +
												"Are you sure to delete this charging spot? ",
										   buttons: Ext.Msg.YESNO,
										   fn: function(btn){
											   	if(btn == 'no') {
													return false;
												} else {
													var g = _p.getChargingSpotGrid(),
													r = g.getSelectionModel().getSelected();
													if(r){
														g.store.remove(r);
													}
												}
									   		},										   
										   icon: Ext.MessageBox.QUESTION
										});
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
								
								hidden: _p.readOnly,
								
								handler: function(button, event) {									
									var grid   = _p.getChargingSpotGrid();
									var store  = grid.getStore();
									
					                var ChargingSpot = store.recordType;
					                var p = new ChargingSpot({										
										logicalId: "",
										note: "",
										orientation: ""
					                });
					                
					                p.markDirty();
					                grid.stopEditing();
					                store.insert(0, p);
					                store.getAt(0);
					                grid.startEditing(0, 1); 
						      	}
							}]
						}
					}
				]
			}
			],
			buttons: [{
				text: "Save", 
				hidden: _p.readOnly,
				iconCls: "icon-silk-disk",
				handler: _p.save.createDelegate(_p)
			},
			{
				text: _p.model.readOnly ? "Close" : "Cancel",
				iconCls: "icon-x16-clear",
				handler: _p.cancel.createDelegate(_p)
			}]
		});

		_p.on("afterrender", _p.loadData, _p);
		
		_p.supr().initComponent.call(_p);
	},
	loadData:function(){
		var _p=this;
		
		if(_p.model){
			var item 		= _p.model.chargingStation,
				form 		= _p.getMainForm(), 
				addressform = _p.getAddressForm(), 
				geoform 	= _p.getGeoPositionForm(),
				commentsform= _p.getCommentsForm(),
                access;
			
			if(item && !Ext.isEmpty(item)){
                access = item.accessRestriction;
				
				form.setValues(Ext.applyIf({
				    type : item.type ?  item.type.value : "",
				    accessRestriction: access && access.id == EMobility.constants.AccessRestriction.PRIVATE
				}, item));
				
				commentsform.setValues(item);
				
				if(item.address){
					addressform.setValues(item.address);
					
					if(item.address.position){
						geoform.setValues(item.address.position);
					}
				}
				
				if(item.chargingSpots){
					_p._STORES.spotDetails.loadData(item.chargingSpots);
				}
			}
		}
		
		_p.showChargerLocation();
	},
	showChargerLocation:function(){
		
		var _p = this;
		
		if(_p.mapLoaded){
            var item = _p.getData();
            
			_p.getMapPanel().clearOverlays();
			
			item.position = _p.getGeoPositionForm().getValues();
			item.address = "Charger location using geo co-ordinates</b>";
			
			if(item.position.latitude && item.position.longitude){
				_p.showMarkersOnMap.call(_p, [item]);	
			}
		}
	},	
	getData: function(){
		var _p    = this;
		var form 		= _p.getMainForm(), 
			addressform = _p.getAddressForm(), 
			geoform 	= _p.getGeoPositionForm(),
			commentsform= _p.getCommentsForm(),
			store 		= _p._STORES.spotDetails,
			spots 		= [], 
			cs 			= Ext.apply({}, _p.model.chargingStation || {uid: 0}),
			oldcsType   = cs.type;

		
		Ext.apply(cs, form.getFieldValues());
		
        cs.type = (oldcsType && oldcsType.value == cs.type) ? oldcsType : { id: form.findField("type").getValue() };
        cs.accessRestriction = { id: cs.accessRestriction ? EMobility.constants.AccessRestriction.PRIVATE : EMobility.constants.AccessRestriction.PUBLIC };

		cs.address = addressform.getFieldValues();
		cs.address.position = geoform.getFieldValues();
		
		Ext.apply(cs, commentsform.getFieldValues());
		
		store.commitChanges();
		
        var records = store.getRange(), d;
        for (var i = 0; i < records.length; i++) {
        	d = records[i].data;
        	
            spots.push(d);
        }
		
        cs.chargingSpots = spots;
        
        return cs;
	},
	getChargingSpotGrid: function(){
		return Ext.getCmp(this._ID.Grid);
	},
	getMainForm: function(){
		return Ext.getCmp(this._ID.MainForm).getForm();
	},
	getAddressForm: function(){
		return Ext.getCmp(this._ID.AddressForm).getForm();
	},
	getGeoPositionForm: function(){
		return Ext.getCmp(this._ID.GeoPositionForm).getForm();
	},
	getCommentsForm: function(){
		return Ext.getCmp(this._ID.Comments).getForm();
	},
	getMapPanel: function(){
		return Ext.getCmp(this._ID.MapPanel);
	},
	searchByLocation:function(){
		var _p = this, location = _p.getLocation();

		_p.getMapPanel().clearOverlays();
		
		if(location && location.lat && location.lng){
			GeocodeService.reverseGeocode(location.lat, location.lng,{
				callback: _p.showMarkersOnMap.createDelegate(_p),
				exceptionScope: _p
			});
		}
		
	},
	searchByAddress:function(){
		var _p = this;
		var queryAddr = _p.getAddress();
		
		_p.getMapPanel().clearOverlays();
		
		if(queryAddr && queryAddr.length > 10){
			GeocodeService.geocode(queryAddr,{
				callback: _p.showMarkersOnMap.createDelegate(_p),
				exceptionScope: _p
			});
		}else{
			$webtop.notifyInfo("Search by Address","Partial address details must be specified!");
		}
	},
	showMarkersOnMap: function (data) {
		var _p 			= this,
			mapPanel	= _p.getMapPanel(),
			map 		= mapPanel.getMap();
		
		_p.result       = data;
		
		if(!Ext.isEmpty(data)){
			Ext.each(data, 
				function(item, idx){
					
					var csForm = _p.getData(),					
					    position = item.position,
					    point    = new google.maps.LatLng(position.latitude, position.longitude);
					
					Ext.applyIf(item, csForm);
					
					var marker = {
						title : "Position: " + position.latitude + "," + position.longitude,			
						icon : EMobility.constants.getSpotAggregatorMapMarker(item),
						draggable: true,
						listeners:{
							click: _p.handleMarkerClick.createDelegate(_p, [item, mapPanel, map, idx]),
							dragend: function(){
								var p = marker.getPosition(), lat=p.lat().toFixed(6), lng = p.lng().toFixed(6);
								
								marker.setTitle("Position: " + lat + "," + lng);
								
								item.infoWindow.setPosition(p);
								item.infoWindow.setContent(_p.getInfoWindowContent(item.address, lat, lng))
								
								item.position.latitude = lat;
								item.position.longitude = lng;
							},
							dragstart: function(){
								item.infoWindow.setMap();
							}
						}
					};
					
					marker = mapPanel.addMarker(point, marker, false, false, marker.listeners);
					
					item.marker = marker;
					item.point = point;
					item.infoWindow = mapPanel.createInfoWindow({
						content: _p.getInfoWindowContent(item.address, item.position.latitude, item.position.longitude)
					}, point);
					
					item.infoWindow.bindTo("position", marker, "position");
				}
			);
			
			mapPanel.panTo(data[0].point);
		}else{
			$webtop.notifyInfo("Search", "No results found!");
		}
	},
	getAddress: function(){
		var _p = this;
		var addr = [];
		
		Ext.iterate(_p.getAddressForm().getFieldValues(), function(key, value){
			addr.push(value);
		})

		return addr.join(",");
	},
	getLocation: function(){
		var _p = this;
		
		var form = _p.getGeoPositionForm();
		
		return { 
			lat: form.findField("lat").getValue(),
			lng: form.findField("lng").getValue()
		};
	},
	handleMarkerClick: function(item, mapPanel, map, idx){
        var _p = this;
        
        mapPanel.hideAllInfoWindows();
        
        if(item.infoWindow){
        	item.infoWindow.open(map);
        	
        	if(!_p.readOnly){
	        	Ext.get(_p.id + _p._ID.FillLink)
	        		.on("click", _p.fillGeoPosition.createDelegate(_p, [item, mapPanel])
	        	);
        	}
        }
	},
	getInfoWindowContent : function(address, lat, lng){
		var msg= "";
		var _p = this;
		

		msg  = "<b>" + address + "</b><br><br>Latitude: "+ lat + "<br>Longitude: " + lng + "<b>";;
		if(!_p.readOnly){
			msg  += "<br><br><a href='javascript:void(0)' id='"+ (_p.id + _p._ID.FillLink) +"'>Use location details </a>";
		}

		return msg;
	},
	save: function(){
		var _p = this;
		
		SpotAggregatorService.setSpotAggregator(_p.getData(),{
			callback : function(){				
				$webtop.notifyInfo("Success", "Charging station saved successfully!");
				_p.close();
			},
			exceptionScope: _p
		});
	},
	cancel:function(){
		this.close();
	},
	close:function(){
		var _p = this;
		
		var view = this.model.view;
		
		if(view && view.close){
			view.close();
		}
	},
	fillGeoPosition : function(item, mapPanel){
		var _p = this, form = _p.getGeoPositionForm();
		
		form.findField("latitude").setValue(item.position.latitude);
		form.findField("longitude").setValue(item.position.longitude);
		
		mapPanel.hideAllInfoWindows();
	}
});

Ext.reg('emo-view-chargingstation-details', EMobility.ChargingStationDetail);