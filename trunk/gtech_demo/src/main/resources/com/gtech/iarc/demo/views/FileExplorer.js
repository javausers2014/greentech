Ext.ns("WebtopDemo");
WebtopDemo.FileExplorer = Ext.extend(Webtop.View, {	
	title: "File Explorer",
	width: 800,
	height: 500,
	initComponent: function() {
		var app = this;		
		var imgUri = app.img("classpath:com.innovations.webtop.demo.images.fileexplorer.large/{extension}.png", true);
		
		var view = this;
		view._JSONPURL = "/webtop/services/dwr/jsonp/FileExplorerService";
		
		view._FILESTORE = new Ext.data.Store({
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
					read: view._JSONPURL + "/getEntries"
				}
			})			
		});
						
		Ext.apply(this, {
			tbar: [
			"->",
			{ 
				text: "Delete",
				iconCls: "icon-silk-database-delete"
			}],
			layout: "border",
			items: [{
				xtype: "grid",
				region: "center",
				height: 420,
				width: 790,
	        	store: view._FILESTORE,													
		        columns: [
		            {id: 'name', header: 'Name', sortable: true, dataIndex: 'name'},
		            {header: 'Modified', width: 120, sortable: true, renderer: Ext.util.Format.dateRenderer('Y-m-d H:i'), dataIndex: 'lastModified'},
		            {header: 'Type', width: 120, sortable: true, dataIndex: 'extension'},
		            {header: 'Size', width: 120, sortable: true, dataIndex: 'length', align: 'right', renderer: Ext.util.Format.fileSize}
		        ],
		        viewConfig: {
		          rowTemplate: new Ext.Template(
		          	'<div class="x-grid3-row ux-explorerview-item ux-explorerview-large-item" unselectable="on">'+
		          	'<table class="ux-explorerview-icon" cellpadding="0" cellspacing="0">'+
		          	'<tr><td align="center"><img src="' + imgUri + '"></td></tr></table>'+
		          	'<div class="ux-explorerview-text"><div class="x-grid3-cell x-grid3-td-name" unselectable="on">{name}</div></div></div>'
		          ),
		          forceFit: true
		        },	
		        enableDragDrop: true,	
		        autoExpandColumn: 'name',
		        frame: true,
		        listeners: {
		        	rowdblclick: function(grid, rowIndex, e) {
		        		var record = grid.getStore().getAt(rowIndex);
		        		var fileName = record.data.name;
		        		
		        		FileExplorerService.downloadFile(fileName, {
		        			callback: function (dl) {
		        				dwr.engine.openInDownload(dl);
		        			}
		        		});
		        	}
		        }
			}],
			bbar: [{ 
				xtype: "label",
				text: "Upload File: "
			},{
				xtype: "textfield",
				id: "FileExplorerUploadFileInput",
				inputType: "file",
				width: "100%"
			},{
				xtype: "button",
				text: "Upload",
				handler: function() {
					FileExplorerService.uploadFile(dwr.util.getValue('FileExplorerUploadFileInput'), { 
						callback: function() {
							alert("File has been uploaded..");
						}
					});					
				}
			}]
		});
		this.supr().initComponent.call(this);
	}	
});