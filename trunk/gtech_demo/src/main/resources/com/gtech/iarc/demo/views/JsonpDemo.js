Ext.ns("WebtopDemo");
WebtopDemo.JSONPDemo = Ext.extend(Webtop.View, {	
	title: "JSONP Services",
	width: 850,
	height: 500,
	initComponent: function() {
		var view = this;
		
		view._JSONPURL = "/services/dwr/jsonp/PersonnelService";
		
		var gridStore = new Ext.data.Store({
			autoLoad: true,
			autoSave: true,
			writer: new Ext.data.JsonWriter(),
			reader: new Ext.data.JsonReader({
				root: "reply",			
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
			proxy: new Ext.data.ScriptTagProxy({
				api: {
					read: view._JSONPURL + "/getPersonnels",
					create: view._JSONPURL + "/createPersonnel",
					update: view._JSONPURL + "/updatePersonnel",
					destroy: view._JSONPURL + "/deletePersonnel"
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
	
		var app = this;				
		Ext.apply(this,{
			layout: "fit",			
			items: [{
				xtype: "grid",
				store: gridStore,
				plugins: [editor],
				columns: [
					new Ext.grid.RowNumberer(),
					{ header: "First Name", width: 30, dataIndex: "firstName", editor: new Ext.form.TextField() },
					{ header: "Last Name", width: 30, dataIndex: "lastName", editor: new Ext.form.TextField() },
					{ header: "Number", width: 30, dataIndex: "personnelNumber", editor: new Ext.form.TextField()},
					{ header: "City", width: 30, dataIndex: "city", editor: new Ext.form.TextField()},
					{ header: "Mobile", width: 30, dataIndex: "mobile", editor: new Ext.form.TextField()},
					{ header: "Hire Date", width: 30, dataIndex: "hireDate", renderer: Ext.util.Format.dateRenderer('d/m/Y'), editor: new Ext.form.DateField() },
					{ header: "E-mail", width: 30, dataIndex: "email", editor: new Ext.form.TextField()},
					{ header: "Department", width: 30, dataIndex: "department", editor: new Ext.form.TextField()}
				],
				stripeRows: true,
				loadMask: true,
				autoScroll: true,
				viewConfig: {
					forceFit: true
				},
				tbar: [
					"->", {
						text	: "Get Records Count",
						handler: function() {
							Ext.Ajax.request({
								url: view._JSONPURL + "/getPersonnels",
								isJSON: true,
								proxied: {
									callbackParam: "callback"
								},
								callback: function (options,success,response) {
									var responseJSON;
									try {
										responseJSON = Ext.util.JSON.decode(response.responseText);
									}
									catch (err) {
										Ext.MessageBox.alert('ERROR', 'Could not decode ' + response.responseText);
									}						
									alert("Got " + responseJSON.reply.length + " records");
								}							
							});
						}
					},{
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
