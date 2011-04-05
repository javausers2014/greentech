Ext.ns("WebtopDemo");
WebtopDemo.FileExplorer = Ext.extend(EMobility.MessageView, {	
	title: "File Explorer",
	
	width: 600,
	height: 400,
	
	alert: function(s){
		Ext.Msg.alert(s);
	},
	
	initId: function() {
		this.supr().initId.call(this);
		
		return Ext.apply(this._ID, {
			// Tab
			TABPANEL	: Ext.id(),
			
			// Grids
			GRID_UPLOAD	: Ext.id(),
			GRID_AGING	: Ext.id(),
			GRID_REPORT	: Ext.id(),
			
			// Upload
			FILE		: Ext.id()
		});
	},
	
	getToken: function() {
		view = this,
			c = this.findById(this._ID.TABPANEL).getActiveTab(),
			id = c ? c.id : false;
		
		switch(id) {
			case this._ID.GRID_UPLOAD:
				return "upload";
			
			case this._ID.GRID_AGING:
				return "aging";
			
			case this._ID.GRID_REPORT:
				return "report";
		}
		
		return false;
	},
	
	viewMsg: "Please manage files using the table below.",
	
	initStores: function() {
		var view = this;
		
		// Upload
		view._STORE_UPLOAD = new Ext.data.Store({
			autoLoad: true,
			reader: new Ext.data.JsonReader({
				root: "reply",			
				fields : [
					{name: 'isDirectory', type: "boolean"},
					{name: 'name', type: "string"},
					{name: 'length', type: "long"},
					{name: 'lastModified', type: "long"},
					{name: 'extension', type: "string"}
				]
			}),
			proxy: new Ext.data.ScriptTagProxy({
				api: {
					//alert('read the upload');
					read: view._JSONPURL + "/getUpload"
				}
			})			
		});
		
		// Aging
		view._STORE_AGING = new Ext.data.Store({
			autoLoad: true,
			reader: new Ext.data.JsonReader({
				root: "reply",
				fields : [
					{name: 'isDirectory', type: "boolean"},
					{name: 'name', type: "string"},
					{name: 'length', type: "long"},
					{name: 'lastModified', type: "long"},
					{name: 'extension', type: "string"}
				]
			}),
			proxy: new Ext.data.ScriptTagProxy({
				api: {
					read: view._JSONPURL + "/getAging"
				}
			})			
		});
		
		// Report
		view._STORE_REPORT = new Ext.data.Store({
			autoLoad: true,
			reader: new Ext.data.JsonReader({
				root: "reply",			
				fields : [
					{name: 'isDirectory', type: "boolean"},
					{name: 'name', type: "string"},
					{name: 'length', type: "long"},
					{name: 'lastModified', type: "long"},
					{name: 'extension', type: "string"}
				]
			}),
			proxy: new Ext.data.ScriptTagProxy({
				api: {
					read: view._JSONPURL + "/getReport"
				}
			})			
		});	
	},
	
	reloadStore: function() {
		var view = this,
			c = this.findById(this._ID.TABPANEL).getActiveTab();
		
		if (c) {
			// Reload store
			c.getStore().reload();
		}
	},
	
	deleteFile: function() {
		var view = this,
			token = this.getToken(),
			s = this.findById(this._ID.TABPANEL).getActiveTab().getSelectionModel().getSelected(),		
			filename = s ? s.data.name : false;
		
		if (filename) {
			view.showLoadingMessage("Deleting file <b>" + filename + "</b>");
			FileExplorerService.deleteFile(token, filename, {
				callback: function(success) {
					if (success) {
						view.showSuccessMessage("File <b>" + filename + "</b> has been deleted successfully !");											
						// Reload
						view.reloadStore();
					} 
					else {
						view.showErrorMessage("Failed to delete file <b>" + filename + "</b>");
					}
				},
				exceptionScope: view
			});
		}
		else {
			view.showErrorMessage("Please select a file to delete.");
		}
	},
	
	uploadFile: function() {
		var view = this,
			token = this.getToken(),
			file = Ext.getDom(this._ID.FILE), 
			filename = file.value;
		
		// Get selected tab
		if (token) {
			if (file) {
				view.showLoadingMessage("Uploading file <b>" + filename + "</b>..");
			
				FileExplorerService.uploadFile(token, file, { 
					callback: function() {
						view.showSuccessMessage("File <b>" + filename + "</b> uploaded successfully.");
						
						// Reload
						view.reloadStore();
					},
					exceptionScope: view
				});	
			}
			else {
				view.showErrorMessage("Please select a file to upload.");
			}
		}
	},
	
	downloadFile: function(filename) {
		var view = this,
			token = this.getToken();
	
		// TODO: Callback needs another param called : "token" to specify
		FileExplorerService.downloadFile(token, filename, {
			callback: function (dl) {				
				dwr.engine.openInDownload(dl);
			},
			exceptionScope: view
		});
	},
	
	initComponent: function() {
		var view = this,
			ID = this.initId(),			
			imgUri = view.img("classpath:com.innovations.webtop.demo.images.fileexplorer.large/{extension}.png", true);
		
		view._JSONPURL = XWT_BASE_PATH + "/services/dwr/jsonp/FileExplorerService";
		
		// Init stores
		
		view.initStores();
		Ext.apply(this, {

			style: 'background: #fff; padding: 8px;',
			layout: 'border',
			border: false,			
			defaults: { border: false },
			items: [
				this.buildMessagePanel({
					defaultMsg: 'Please enter account info in the form below.'
				}),
				{
					region: 'center',
					id: ID.TABPANEL,
					xtype: "tabpanel",
					border: false,
					defaults: { 
						border: false,
						xtype: "grid",
						enableDragDrop: true,	
						autoExpandColumn: 'name',
						frame: true,
						columns: [
							{id: 'name', header: 'Name', sortable: true, dataIndex: 'name'},
							{header: 'Modified', width: 120, sortable: true, fixed: true, renderer: Ext.util.Format.dateRenderer('Y-m-d H:i'), dataIndex: 'lastModified'},
							{header: 'Type', width: 80, sortable: true, fixed: true, dataIndex: 'extension'},
							{header: 'Size', width: 80, sortable: true, dataIndex: 'length', align: 'right', renderer: Ext.util.Format.fileSize}
						],
						listeners: {
							rowdblclick: function(grid, rowIndex, e) {
								var record = grid.getStore().getAt(rowIndex);
								var fileName = record.data.name;
								
								this.downloadFile(fileName);
							},
							show: function(grid) {
								grid.getStore().reload();
							},
							scope: this
						}
					},
					activeTab: 0,
					items: [
						{
							title: "Upload",												
							id: view._ID.GRID_UPLOAD,
							store: view._STORE_UPLOAD,
							viewConfig: {
								rowTemplate: new Ext.Template(
									'<div class="x-grid3-row ux-explorerview-item ux-explorerview-large-item" unselectable="on">'+
									'<table class="ux-explorerview-icon" cellpadding="0" cellspacing="0">'+
									'<tr><td align="center"><img src="' + imgUri + '"></td></tr></table>'+
									'<div class="ux-explorerview-text"><div class="x-grid3-cell x-grid3-td-name" unselectable="on">{name}</div></div></div>'
								  ),
								forceFit: true
							},
							sm: new Ext.grid.RowSelectionModel({singleSelect:true})
						},
						{
							title: "Aging",												
							id: view._ID.GRID_AGING,
							store: view._STORE_AGING,							
							viewConfig: {
								rowTemplate: new Ext.Template(
									'<div class="x-grid3-row ux-explorerview-item ux-explorerview-large-item" unselectable="on">'+
									'<table class="ux-explorerview-icon" cellpadding="0" cellspacing="0">'+
									'<tr><td align="center"><img src="' + imgUri + '"></td></tr></table>'+
									'<div class="ux-explorerview-text"><div class="x-grid3-cell x-grid3-td-name" unselectable="on">{name}</div></div></div>'
								  ),
								forceFit: true
							},
							sm: new Ext.grid.RowSelectionModel({singleSelect:true})
						},
						{
							title: "Report",							
							id: view._ID.GRID_REPORT,
							store: view._STORE_REPORT,
							viewConfig: {
								rowTemplate: new Ext.Template(
									'<div class="x-grid3-row ux-explorerview-item ux-explorerview-large-item" unselectable="on">'+
									'<table class="ux-explorerview-icon" cellpadding="0" cellspacing="0">'+
									'<tr><td align="center"><img src="' + imgUri + '"></td></tr></table>'+
									'<div class="ux-explorerview-text"><div class="x-grid3-cell x-grid3-td-name" unselectable="on">{name}</div></div></div>'
								  ),
								forceFit: true
							},
							sm: new Ext.grid.RowSelectionModel({singleSelect:true})
						}
					]
				}
			],			
			bbar: {
				style: 'padding: 3px 0px',
				defaults: { style: 'padding: 0px 7px;' },
				items: [
					{ 
						xtype: "label",
						text: "Upload File: "
					},{
						xtype: "textfield",
						id: ID.FILE,						
						inputType: "file"
					},{					
						text: "Upload",
						iconCls: 'icon-silk-bullet_go',
						handler: function() {
							this.uploadFile();		
						},
						scope: this
					}, "->",
					{
						text: "Delete",
						iconCls: "icon-x16-clear",
						handler: function() {
							this.deleteFile();		
						},
						scope: this						
					}
				]	
			}
		});
		
		this.supr().initComponent.call(this);
	}	
});