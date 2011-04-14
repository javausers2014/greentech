Ext.ns("iPallet.Staff");

iPallet.Staff.Browser = Ext.extend(Webtop.View, {
	maxInstance: 1,
	
	width: 700,
	height: 400,
	resizable: true,
	
	title: '<@i18nText key="ipallet.view.title.staffmanagement"/>',
	iconCls: "icon-silk-folder-user",	
	initComponent: function() {
		var view = this;
		
		var staffStore = new Ext.data.Store({
			autoLoad: false,
		 	proxy: new Ext.ux.data.DwrProxy({
		 		apiActionToHandlerMap : {
		 			read : {
		 				dwrFunction : RemoteStaffService.getAllStaff
						,getDwrArgsFunction : function(trans) {
							return [
								searchForm.staffNo,
								searchForm.nameContains,
								searchForm.emailContains,
								staffStore.lastOptions.params.start,
								staffStore.lastOptions.params.limit];
						}		 				
		 			}
		 		}
		 	}),
		 	reader: new Ext.data.JsonReader({
				totalProperty: 'totalSize',	
				root : 'rawResultList',
		 		fields : [ {name: 'id'},{name: 'staffNo'}, {name: 'fullName'}, 
		 			{name: 'email'}, {name: 'birthDate', type:'date',dateFormat:'Y.m.d'} ]
		 	})
		});
	
		Ext.apply(this, {
			layout: 'border',
			defaults: { border: false },
			items:[{
				xtype: 'panel',				
				width: '750',
				height: '480',
				region: 'center',
				layout: 'border',
				items: 
				[{
					xtype: 'form',
					id: 'searchForm',
					region: 'west',
					title: '<@i18nText key="ipallet.view.staffmanagement.label.searchcriteria"/>',
					cls: "wCls-ToolPanel",
					width: '25%',
					defaultType: "textfield",
					padding: 10,
					labelAlign: "top",
					items: [{
						fieldLabel: '<@i18nText key="ipallet.view.staffmanagement.label.staffno"/>',
						name: "staffNo"
					},{
						fieldLabel: '<@i18nText key="ipallet.view.staffmanagement.label.namecontains"/>',
						name: "nameContains"
					},{
						fieldLabel: '<@i18nText key="ipallet.view.staffmanagement.label.email"/> (<@i18nText key="ipallet.view.staffmanagement.label.wildcard"/>)',
						name: "emailContains"						
					}],
					buttons: [
						{	text: '<@i18nText key="iarc.base.label.button.search"/>',
							iconCls: 'icon-x16-search',
							handler: function() {			
								this.staffStore.load();
							}
						},
						{text: '<@i18nText key="iarc.base.label.button.clear"/>',
							iconCls: 'icon-x16-clear',
							handler: function() {
								view.resetView();
							}
						}
					]
				},
				{
					xtype: "grid",
					store: staffStore,
					region: 'center',
					layout: 'fit',
					colModel: new Ext.grid.ColumnModel({						
						columns: [
							new Ext.grid.RowNumberer(),
							{
								header: '<@i18nText key="ipallet.view.staffmanagement.label.staffno"/>', 
								dataIndex: "staffNo",
								sortable: true,
								width: '20%'
							},{
								header: '<@i18nText key="ipallet.view.staffmanagement.label.fullname"/>', 
								dataIndex: "fullName",
								sortable: true,
								width: '30%'
							},{
								header: '<@i18nText key="ipallet.view.staffmanagement.label.email"/>', 
								dataIndex: "email",
								sortable: true,
								width: '30%'
							},{
								header: '<@i18nText key="ipallet.view.staffmanagement.label.birthdate"/>', 
								dataIndex: "birthDate",
								sortable: true,
								width: '20%'
							}
						]
					}),

					sm: new Ext.grid.RowSelectionModel({singleSelect: true}),					
					bbar: new Ext.PagingToolbar({
						pageSize: 25,
						store: staffStore,
						displayInfo: true,
						displayMsg: '<@i18nText key="iarc.base.text.listing.display.message"/>',
						emptyMsg: '<@i18nText key="iarc.base.text.listing.empty.message"/>'
					})					
				}],
				tbar: 
				[{ 
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
				}]
			}]
		});
		this.supr().initComponent.call(this);
	}	
});


