Ext.ns("iPallet.Staff");

iPallet.Staff.Browser = Ext.extend(Webtop.View, {
	width: 800,
	height: 500,
	title: "<@i18nText key="ipallet.view.title.staffmanagement"/>",
	iconCls: "icon-silk-folder-user",	
	initComponent: function() {

		var staffStore = new Ext.data.Store({
			autoLoad: true,//{params:{start:0,limit:25}},
		 	proxy: new Ext.ux.data.DwrProxy({
		 		apiActionToHandlerMap : {
		 			read : {
		 				dwrFunction : RemoteStaffService.getAllStaff
						//,getDwrArgsFunction : function(trans) {
						//	return [
						//		staffStore.lastOptions.params.start,
						//		staffStore.lastOptions.params.limit];
						//}		 				
		 			}
		 		}
		 	}),
		 	reader: new Ext.data.JsonReader({
		 		//root : 'recordsData',
		 		fields : [ {name: 'id'},{name: 'staffNo'}, {name: 'fullName'}, 
		 			{name: 'email'}, {name: 'birthDate', type:'date',dateFormat:'Y.m.d'} ]
		 	})
		});
	
		Ext.apply(this, {
			items:[{
				xtype: "panel",				
				height: 400,
				width: 600,
				layout: "border",
				items: [{
					xtype: "form",
					region: "west",
					title: "<@i18nText key="ipallet.view.staffmanagement.label.searchcriteria"/>",
					cls: "wCls-ToolPanel",
					width: 250,
					defaultType: "textfield",
					padding: 10,
					labelAlign: "top",
					defaults: {
						width: '100%'
					},
					items: [{
						fieldLabel: "<@i18nText key="ipallet.view.staffmanagement.label.staffno"/>",
						name: "staffNo"
					},{
						fieldLabel: "<@i18nText key="ipallet.view.staffmanagement.label.namecontains"/>",
						name: "nameContains"
					},{
						fieldLabel: "<@i18nText key="ipallet.view.staffmanagement.label.email"/> (<@i18nText key="ipallet.view.staffmanagement.label.wildcard"/>)",
						name: "EmailContains"						
					}],
					buttons: [
						{text: "<@i18nText key="iarc.base.label.button.search"/>"},
						{text: "<@i18nText key="iarc.base.label.button.clear"/>"}
					]
				},{
					xtype: "grid",
					store: staffStore,					
					colModel: new Ext.grid.ColumnModel({
						columns: [
							new Ext.grid.RowNumberer(),
							{
								header: "<@i18nText key="ipallet.view.staffmanagement.label.staffno"/>", 
								dataIndex: "staffNo",
								sortable: true,
								width: 80
							},{
								header: "<@i18nText key="ipallet.view.staffmanagement.label.fullname"/>", 
								dataIndex: "fullName",
								sortable: true,
								width: 300
							},{
								header: "<@i18nText key="ipallet.view.staffmanagement.label.email"/>", 
								dataIndex: "email",
								sortable: true,
								width: 200
							},{
								header: "<@i18nText key="ipallet.view.staffmanagement.label.birthdate"/>", 
								dataIndex: "birthDate",
								sortable: true,
								width: 80
							}
						]
					}),

					sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
					region: "center",
					bbar: new Ext.PagingToolbar({
						pageSize: 25,
						store: staffStore,
						displayInfo: true,
						displayMsg: '<@i18nText key="iarc.base.text.listing.display.message"/>',
						emptyMsg: '<@i18nText key="iarc.base.text.listing.empty.message"/>'
					})					
				}],
				tbar: [
					{ 
						text: '<@i18nText key="ipallet.view.staffmanagement.action.createnewstaff"/>',
						iconCls: "icon-silk-user-add",
						listeners: {
							click: function (btn, evt) {
								new iPallet.Staff.Creation({
									appKey: 'ipallet.hr.staff.creation',
									className: 'iPallet.Staff.Creation'
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


