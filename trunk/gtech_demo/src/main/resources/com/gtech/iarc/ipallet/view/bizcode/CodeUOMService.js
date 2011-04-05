Ext.ns("iPallet");
iPallet.CodeUOMService = Ext.extend(Webtop.View, {
	width: 500,
	height: 500,
	title: "Restful Services Demo",
	initComponent: function() {
		
		var store = new Ext.data.Store({
			restful: true,
			autoLoad: true,
			autoSave: true,
			proxy: new Ext.data.HttpProxy({ url: XWT_BASE_PATH + "/services/rest/uomcode" }),
			reader: new Ext.data.JsonReader({
    			totalProperty: 'total',
    			successProperty: 'success',
    			idProperty: 'id',
    			root: 'data',
				messageProperty: 'message'
			},[
				{ name: "id" },
				{ name: "name" },
				{ name: "category" },
				{ name: "code" },
				{ name: "active", type: "boolean" },
				{ name: "base", type: "boolean" },
				{ name: "factor" }
			]),
			writer: new Ext.data.JsonWriter({
				encode: false,
				writeAllFields: true
			})
		});

		var combo = new Ext.form.ComboBox({
		    typeAhead: true,
		    triggerAction: 'all',
		    lazyRender:true,
		    mode: 'local',
		    store: new Ext.data.ArrayStore({
		        id: 0,
		        fields: [
		            'myId',
		            'displayText'
		        ],
		        data: [[1, 'item1'], [2, 'item2']]
		    }),
		    valueField: 'myId',
		    displayField: 'displayText'
		});

		
		var editor = new Ext.ux.grid.RowEditor({ saveText: 'Update'});		
		var grid = new Ext.grid.GridPanel({
        	iconCls: 'icon-grid',
        	frame: true,
        	sm: new Ext.grid.RowSelectionModel({singleSelect:true}),
        	store: store,
        	plugins: [editor],
        	columns : [
    			{
    				header: "<@i18nText key="ipallet.view.uomcode.label.name" />", 
    			 	width: 100, 
    			 	sortable: true, 
    			 	dataIndex: 'name', 
    			 	editor: new Ext.form.TextField({})
    			 },
    			{
    				header: "<@i18nText key="ipallet.view.uomcode.label.category" />", 
    				width: 50, 
    				sortable: true, 
    				dataIndex: 'category', 
    				editor: new Ext.form.ComboBox({})
    			},
    			{
	    			header: "<@i18nText key="ipallet.view.uomcode.label.code" />", 
	    			width: 50, 
	    			sortable: true, 
	    			dataIndex: 'code', 
	    			editor: new Ext.form.TextField({})
    			},
    			{
	    			header: "<@i18nText key="ipallet.view.uomcode.label.factor" />", 
	    			width: 50, 
	    			sortable: true, 
	    			dataIndex: 'factor', 
	    			editor: new Ext.form.NumberField({})
    			},
    			{
	    			header: "<@i18nText key="ipallet.view.uomcode.label.active" />", 
	    			width: 50, 
	    			sortable: true, 
	    			dataIndex: 'contactNumber', 
	    			editor: new Ext.form.Checkbox({})
    			},
    			{
	    			header: "<@i18nText key="ipallet.view.uomcode.label.base" />", 
	    			width: 50, 
	    			sortable: true, 
	    			dataIndex: 'contactNumber', 
	    			editor: new Ext.form.Checkbox({})
    			}
			],
        	viewConfig: {
            	forceFit: true
        	}
    	});
		
		Ext.apply(this, {
			layout: "fit",
        	tbar: [
        	"->",
        	{
            	text: 'Add',
            	iconCls: 'icon-fugue-plus-circle-frame',
            	handler: function(btn, ev) {
        			var u = new grid.store.recordType({ firstName : '', lastName: '', email : '' });
        			editor.stopEditing();
        			grid.store.insert(0, u);
        			editor.startEditing(0);            			
            	}
        	}, '-', {
            	text: 'Delete',
            	iconCls: 'icon-fugue-cross-circle-frame',
            	handler: function() {
        			var rec = grid.getSelectionModel().getSelected();
        			if (!rec) {
            			return false;
        			}
        			grid.store.remove(rec);            		
            	}
        	}],			
			items: grid
		});
		
		this.supr().initComponent.call(this);
	}	
});

 
 
 