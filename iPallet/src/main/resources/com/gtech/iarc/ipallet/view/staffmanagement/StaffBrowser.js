Ext.ns("iarc.staffManagement");

iarc.staffManagement.Browser = Ext.extend(Webtop.View, {
	width: 800,
	height: 500,
	title: "<@i18nText key="ipallet.view.title.staffmanagement"/>",
	iconCls: "icon-silk-folder-user",	
	initComponent: function() {
	
		var dataStore = new Ext.data.Store({
			autoLoad: true,
		 	proxy: new Ext.ux.data.DwrProxy({
		 		apiActionToHandlerMap : {
		 			read : {
		 				dwrFunction : RemoteUserAccountService.getUsers
		 			}
		 		}
		 	}),
		 	reader: new Ext.data.JsonReader({
		 		root : 'recordsData',
		 		fields : [ {name: 'id'}, {name: 'name'}, {name: 'email'}, {name: 'department'} ]
		 	})
		});
	
		Ext.apply(this, {
			items:[{
				xtype: "panel",				
				height: 468,
				width: 786,
				layout: "border",
				items: [{
					xtype: "form",
					region: "west",
					title: "Search Criteria",
					cls: "wCls-ToolPanel",
					width: 250,
					defaultType: "textfield",
					padding: 10,
					labelAlign: "top",
					defaults: {
						width: '100%'
					},
					items: [{
						fieldLabel: "User ID",
						name: "userId"
					},{
						fieldLabel: "Name Contains",
						name: "nameContains"
					},{
						fieldLabel: "Email (Wildcards * accepted)",
						name: "EmailContains"						
					}],
					buttons: [
						{text: "Search"},
						{text: "Clear"}
					]
				},{
					xtype: "grid",
					store: dataStore,					
					colModel: new Ext.grid.ColumnModel({
						defaults: {
							width: 60,
							sortable: true
						},
						columns: [
							new Ext.grid.RowNumberer(),
							{
								header: "User ID", dataIndex: "id"
							},{
								header: "User Name", dataIndex: "name", width: 150
							},{
								header: "E-mail", dataIndex: "email", width: 200
							},{
								header: "Department", dataIndex: "department", width: 80
							}
						]
					}),
					sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
					region: "center",
					bbar: new Ext.PagingToolbar({
						pageSize: 25,
						store: dataStore,
						displayInfo: true,
						displayMsg: 'Displaying results {0} - {1} of {2}',
						emptyMsg: "No results to display"
					})					
				}],
				tbar: [
					{ 
						text: "Create New User",
						iconCls: "icon-silk-user-add",
						listeners: {
							click: function (btn, evt) {
								new com.innovations.webtop.kitchensink.views.usermanagement.AddUser({
									appKey: "usermanagement.adduser",
									className: "com.innovations.webtop.kitchensink.views.usermanagement.AddUser"
								}).show();
								return;								
							}
						}
					}
				]
			}]
		});
		this.supr().initComponent.call(this);
	}	
});


