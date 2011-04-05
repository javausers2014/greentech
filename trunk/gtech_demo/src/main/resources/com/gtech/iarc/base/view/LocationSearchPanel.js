Ext.ns("EMobility.ux");

EMobility.ux.LocationSearchPanel = Ext.extend(Ext.Panel, {	
	
	initComponent: function() {		
		var _p = this;
		
		_p._ID = {
			SearchPanel: Ext.id(),
			Location   : Ext.id(),
			Radius	   : Ext.id(),
			ChoiceGrid : Ext.id()
		};
		
		// TODO Add Better Name to event
		this.addEvents(
			"searchclick", 
			"locationselected"
		);
		
		Ext.apply(this, {
			width: 300,
			border: true,
			layout: 'vbox',
			layoutConfig: {
				align : 'stretch',
				pack  : 'start'
			},
			items: [
			// Search Panel
			{
				height: 120,
				id: _p._ID.SearchPanel,
				title: "Search",
				xtype: "form",
				border: false,
				labelWidth: 90,
				bodyStyle:'padding: 8px',	
				defaultType: 'textfield',
				items: [{
					id: _p._ID.Location,
					fieldLabel: 'Location',
					name: 'location',
					anchor: "100%",
					listeners: {
						specialkey: function(field, e){
							if(e.getKey() == e.ENTER){
								_p.doSearch.call(_p);
							}
						}
					}
				},{
					id: _p._ID.Radius,
					fieldLabel: 'Search radius',
					width: 100,
					name: 'radius',
					xtype: 'combo',
					editable: false,
					hideTrigger: false,
					mode: 'local',
					typeAhead: false,
					forceSelection: true,
					triggerAction: 'all',
					displayField: 'text',
					valueField: 'value',
					value: 5000,
					store: new Ext.data.SimpleStore({
//		    					TODO Move it to configuration if required?
						data: [
						       ['100 m', 100]  , ['250 m', 250]  , ['500 m', 500],  ['1 km',1000],
						       ['2 km' , 2000] , ['5 km',5000]   , ['10 km', 10000], 
						       ['15 km', 15000], ['20 km', 20000]
						      ],
						autoLoad: true,
						fields: ['text', 'value']
					}),
					allowBlank: false
				}],
				buttons:[
					{
						text: "Search",
						type: "submit",
						iconCls: "icon-x16-search",
						handler: _p.doSearch.createDelegate(_p)
					}, {
						text: "Clear",
						iconCls: "icon-x16-clear",
						handler: _p.clearForm.createDelegate(_p)							
					}
				]
			},
			// Choice Grid
			{
				flex: 1,
				id: _p._ID.ChoiceGrid,
				title: "Did you mean ?",
				xtype: "grid",
				border: false,
				hidden: true,
				autoScroll: true,
				enableHdMenu: false,
				viewConfig: { forceFit: true },
				store: new Ext.data.JsonStore({			
					autoLoad: false,
            		fields: ["address"]
				}),
				columns:[
			         new Ext.grid.RowNumberer(),
			         {
			         	id: "address",
			         	header: "Address",
			        	dataIndex: "address",
			        	renderer: function(v, p){
			        		p.attr = "title='"+ String.escape(v) +"'";
			        		return v;
			        	}
			         }
				],
				sm: new Ext.grid.RowSelectionModel({singleSelect: true}),				
				listeners: {					
					rowclick: function(grid, rowIndex, e ){ 
						grid.hide();
						
						var location = _p.searchData[rowIndex];

						_p.setLocation(location.address);
						_p.fireEvent("locationselected", location, _p.getRadius());
					}
				}
			}
			]
		});
		
		this.supr().initComponent.call(this);
	},
	 
	 doSearch: function(){
		var _p = this;
		
		var searchList = _p.getAddressList();
		var location = _p.getLocation();
		var radius   = _p.getRadius();
		
		this.getAddressList().hide();
		
		this.fireEvent("searchclick", location, radius, _p.getSearchForm(), _p);
		
	 	if(!Ext.isEmpty(location)){
			GeocodeService.geocode(location, {
				callback: function(data){
					if(!Ext.isEmpty(data) && Ext.isArray(data)){
						if(data.length > 1){
							_p.searchData = data;
							
							_p.showAddressSelectionPanel(data);
						}else{
							_p.fireEvent("locationselected", data[0], radius, _p);
						}
					}else{
						// TODO Display a better message
						$webtop.notifyInfo(
								"Search",
								"<br>No results found!<br>"+
								"Try using a different search criteria.<br>&nbsp;"
						);
					}
				},
				exceptionScope: _p.ownerCt
			});
	 	}		
	 },
	 
	 clearForm: function() {
		var locationTextbox = this.getLocationTextbox();
	 	
		// Clear search form
		locationTextbox.reset();
		
		// Focus
		locationTextbox.focus();
				
		// Close address list panel
		var resultGrid  = this.getAddressList();
		if (!resultGrid.hidden) {
			resultGrid.hide();
		}
	 },
	 
	 showAddressSelectionPanel: function(data){
		 var resultGrid  = this.getAddressList();

		 resultGrid.show();
		 resultGrid.getStore().loadData(data);
		 
		 this.doLayout(true);
	 },
	 
	 getSearchForm : function(){
	 	return Ext.getCmp(this._ID.SearchPanel).getForm();
	 },
	 getAddressList: function(){
	 	return Ext.getCmp(this._ID.ChoiceGrid)
	 },
	 getRadiusCombo : function() {
	 	return Ext.getCmp(this._ID.Radius);
	 },
	 getRadius : function(){
	 	return this.getRadiusCombo().getValue();
	 },
	 getLocationTextbox: function(){
	 	return Ext.getCmp(this._ID.Location);
	 },
	 getLocation: function(){
	 	return this.getLocationTextbox().getValue();
	 },
	 setRadius: function(radius){
	 	Ext.getCmp(this._ID.Radius).setValue(radius);
	 },
	 setLocation: function(location){		
	 	this.getLocationTextbox().setValue(location).focus(true);
	 },
	 getSearchPanel: function(){
	 	return Ext.getCmp(this._ID.SearchPanel);
	 }
});

Ext.reg("emo-ux-location-search-panel", EMobility.ux.LocationSearchPanel);