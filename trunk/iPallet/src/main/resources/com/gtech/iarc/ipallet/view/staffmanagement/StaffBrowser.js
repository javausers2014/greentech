Ext.ns("iPallet.Staff");

iPallet.Staff.Browser = Ext.extend(Webtop.View, {
	maxInstance: 1,
	
	width: 700,
	height: 400,
	resizable: true,
	
	title: '<@i18nText key="ipallet.view.title.staffmanagement"/>',
	iconCls: 'icon-silk-folder-user',	
	initComponent: function() {
		var view = this;
		
		var staffStore = new Ext.data.Store({
			autoLoad: false,
		 	proxy: new Ext.ux.data.DwrProxy({
		 		apiActionToHandlerMap : {
		 			read : {
		 				dwrFunction : RemoteStaffService.searchStaff
						,getDwrArgsFunction : function(trans) {
							var firstResult = 0;
							if(staffStore.lastOptions.params!=null){
								firstResult = staffStore.lastOptions.params.start;
							}
							
							var maxResult = 25;
							if(staffStore.lastOptions.params!=null){
								maxResult = staffStore.lastOptions.params.limit;
							}
							
							var staffNoValue = ' ';
							if(Ext.getCmp('staffNo').getValue()!=null){
								staffNoValue = Ext.getCmp('staffNo').getValue();
							}
							
							var emailValue = ' ';
							if(Ext.getCmp('emailContains').getValue()!=null){
								emailValue = Ext.getCmp('emailContains').getValue();
							}
							
							var nameValue = ' ';
							if(Ext.getCmp('nameContains').getValue()!=null){
								nameValue = Ext.getCmp('nameContains').getValue();
							}
							
							return [
								staffNoValue,
								nameValue,
								emailValue,
								firstResult,
								maxResult
							];
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
						id: 'staffNo'
					},{
						fieldLabel: '<@i18nText key="ipallet.view.staffmanagement.label.namecontains"/>',
						id: 'nameContains'
					},{
						fieldLabel: '<@i18nText key="ipallet.view.staffmanagement.label.email"/> (<@i18nText key="ipallet.view.staffmanagement.label.wildcard"/>)',
						id: 'emailContains'						
					}],
					buttons: [
						{	text: '<@i18nText key="iarc.base.label.button.search"/>',
							iconCls: 'icon-x16-search',
							handler: function() {			
								staffStore.load();
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
						id: 'pagingBar',
						pageSize: 25,
						store: staffStore,
						displayInfo: true,
						displayMsg: '<@i18nText key="iarc.base.text.listing.display.message"/>',
						emptyMsg: '<@i18nText key="iarc.base.text.listing.empty.message"/>'
					})					
				}],
				tbar: [
        		"->",
				{ 
					text: '<@i18nText key="ipallet.view.staffmanagement.action.createnewstaff"/>',
					iconCls: 'icon-fugue-plus-circle-frame',
					listeners: {
						click: function (btn, evt) {
							$webtop.showWindow('ipallet.hr.staff.creation', {}, null, $_wt.getWorkspaceContext().getInteractionModel());
							return;								
						}
					}
				}]
			}]
		});
		this.supr().initComponent.call(this);
	}	
});


