Ext.ns("WebtopDemo");
WebtopDemo.DwrIntegration = Ext.extend(Webtop.View, {	
	title: "DWR Services",	
	maxInstance: 1,
	width: 850,
	height: 500,
	initComponent: function() {
		var app = this;
				
		var gridStore = new Ext.data.Store({
			autoLoad: true,
			autoSave: true,
			reader: new Ext.data.JsonReader({				
				fields : [
					{name: 'firstName', type: "string"},
					{name: 'lastName', type: "string"},
					{name: 'personnelNumber', type: "string"},
					{name: 'city', type: "string"},
					{name: 'mobile', type: "string"},
					{name: 'email', type: "string"},
					{name: 'hireDate', type: "date"},
					{name: 'department', type: "string"}
				]
			}),
			writer: new Ext.data.JsonWriter(),
			proxy: new Ext.ux.data.DwrProxy({
				apiActionToHandlerMap: {
					read: { dwrFunction: PersonnelService.getPersonnels },
					create:  { dwrFunction: PersonnelService.createPersonnel },
					update: { dwrFunction: PersonnelService.updatePersonnel },
					destroy: { dwrFunction: PersonnelService.deletePersonnel }					
				},
				listeners: {
					beforeload: function() {
						console.debug ("beforeload");
					},
					beforewrite: function (proxy, action, data, response, records, options) {
						console.debug (action);
					}
				}
			})
		});		
		
		var editor = new Ext.ux.grid.RowEditor({
			saveText: 'Update',
			listeners : {
				hide: function() {
 					var record = gridStore.getAt(0);
					if (record.phantom && !record.dirty) {
						gridStore.remove(record);
					}					
				}
			}
		});
		
		Ext.apply(this, {
			layout: "fit",		
			items: [{
				xtype: "grid",
				store: gridStore,
				columns: [
					new Ext.grid.RowNumberer(),
					{ header: "First Name", width: 30, dataIndex: "firstName", editor: new Ext.form.TextField() },
					{ header: "Last Name", width: 30, dataIndex: "lastName", editor: new Ext.form.TextField() },
					{ header: "Number", width: 30, dataIndex: "personnelNumber", editor: new Ext.form.TextField()},
					{ header: "City", width: 30, dataIndex: "city", editor: new Ext.form.TextField()},
					{ header: "Mobile", width: 30, dataIndex: "mobile", editor: new Ext.form.TextField()},
					{ header: "Hire Date", width: 30, dataIndex: "hireDate", renderer: Ext.util.Format.dateRenderer('d/m/Y'), editor: new Ext.form.DateField() },
					{ header: "E-mail", width: 30, dataIndex: "email", editor: new Ext.form.TextField({ vtype: "email" })},
					{ header: "Department", width: 30, dataIndex: "department", editor: new Ext.form.TextField()}
				],
				stripeRows: true,
				plugins: [editor],
				loadMask: true,
				autoScroll: true,
				viewConfig: {
					forceFit: true
				},
				tbar: [
					"->",
					{
						text: "Add",
						iconCls: "icon-fugue-plus",
						handler: function(btn, evt) {
							var newRec = new gridStore.recordType({
								name: "",
								dateOfJoining: new Date(),
								department: ""
							});
							editor.stopEditing();
							gridStore.insert(0, newRec);
							editor.startEditing(0, true);
						}
					},{
						text: "Delete",
						iconCls: "icon-fugue-cross",
						handler: function(btn, evt) {
							var grid = app.findByType("grid")[0];
 							gridStore.autoSave = false;
							gridStore.batch = true;
							var records = grid.getSelectionModel().getSelections();
							Ext.each(records, function(record) {
								gridStore.remove(record);
							});
							gridStore.save();
							gridStore.autoSave = true;
							gridStore.batch = false;
						}
					}
				]
			}]
		});	
		this.supr().initComponent.call(this);
	}
});
