Ext.ns("WebtopDemo");
WebtopDemo.Controls = Ext.extend(Webtop.View, {	
	title: "Controls list",
	width: 900,
	height: 500,
	initComponent: function() {
		var app = this;		
		
		// Treepanel
	    var tree = new Ext.tree.TreePanel({
	        title: 'TreePanel',
	        autoScroll: true,
	        enableDD: true
	    });
	
	    var root = new Ext.tree.TreeNode({
	        text: 'Root Node',
	        expanded: true
	    });
	    tree.setRootNode(root);
	
	    root.appendChild(new Ext.tree.TreeNode({text: 'Item 1'}));
	    root.appendChild(new Ext.tree.TreeNode({text: 'Item 2'}));
	    var node = new Ext.tree.TreeNode({text: 'Folder'});
	    node.appendChild(new Ext.tree.TreeNode({text: 'Item 3'}));
	    root.appendChild(node);
	    
	    var accConfig = {
	        title: 'Accordion and TreePanel',
	        layout: 'accordion',
	        x: 690, y: 830,
	        width: 450,
	        height: 240,
	        bodyStyle: {
	            'background-color': '#eee'
	        },
	        defaults: {
	            border: false
	        },
	        items: [tree, {
	            title: 'Item 2',
	            html: 'Some content'
	        },{
	            title: 'Item 3',
	            html: 'Some content'
	        }]
	    };		
		
		
		Ext.apply(this,{
			items: {
				xtype: "tabpanel",
				activeTab: 0,
				items: [{
			        xtype: 'form',
			        title: 'Form',
			        width: 630,
			        height: 700,
			        frame: true,
			        x: 50, y: 260,
			        tools: [
			            {id:'toggle'},{id:'close'},{id:'minimize'},{id:'maximize'},{id:'restore'},{id:'gear'},{id:'pin'},
			            {id:'unpin'},{id:'right'},{id:'left'},{id:'up'},{id:'down'},{id:'refresh'},{id:'minus'},{id:'plus'},
			            {id:'help'},{id:'search'},{id:'save'},{id:'print'}
			        ],
			        bodyStyle: {
			            padding: '10px 20px'
			        },
			        defaults: {
			            anchor: '98%',
			            msgTarget: 'side',
			            allowBlank: false
			        },
			        items: [{
			            xtype: 'label',
			            text: 'Plain Label'
			        },{
			            fieldLabel: 'TextField',
			            xtype: 'textfield',
			            emptyText: 'Enter a value',
			            itemCls: 'x-form-required'
			        },{
			            fieldLabel: 'ComboBox',
			            xtype: 'combo',
			            store: ['Foo', 'Bar'],
			            itemCls: 'x-form-required',
			            resizable: true
			        },{
			            fieldLabel: 'DateField',
			            itemCls: 'x-form-required',
			            xtype: 'datefield'
			        },{
			            fieldLabel: 'TimeField',
			            itemCls: 'x-form-required',
			            xtype: 'timefield'
			        },{
			            fieldLabel: 'NumberField',
			            emptyText: '(This field is optional)',
			            allowBlank: true,
			            xtype: 'numberfield'
			        },{
			            fieldLabel: 'TextArea',
			            //msgTarget: 'under',
			            itemCls: 'x-form-required',
			            xtype: 'textarea',
			            cls: 'x-form-valid',
			            value: 'This field is hard-coded to have the "valid" style (it will require some code changes to add/remove this style dynamically)'
			        },{
			            fieldLabel: 'Checkboxes',
			            xtype: 'checkboxgroup',
			            columns: [100,100],
			            items: [{boxLabel: 'Foo', checked: true},{boxLabel: 'Bar'}]
			        },{
			            fieldLabel: 'Radios',
			            xtype: 'radiogroup',
			            columns: [100,100],
			            items: [{boxLabel: 'Foo', checked: true, name: 'radios'},{boxLabel: 'Bar', name: 'radios'}]
			        },{
			            hideLabel: true,
			            xtype: 'htmleditor',
			            value: 'Mouse over toolbar for tooltips.<br /><br />The HTMLEditor IFrame requires a refresh between a stylesheet switch to get accurate colors.',
			            height: 110,
			            handler: function(){
			                Ext.get('styleswitcher').on('click', function(e){
			                    Ext.getCmp('form-widgets').getForm().reset();
			                });
			            }
			        },{
			            title: 'Plain Fieldset',
			            xtype: 'fieldset',
			            height: 50
			        },{
			            title: 'Collapsible Fieldset',
			            xtype: 'fieldset',
			            collapsible: true,
			            height: 50
			        },{
			            title: 'Checkbox Fieldset',
			            xtype: 'fieldset',
			            checkboxToggle: true,
			            height: 50
			        }],
			        buttons: [{
			            text:'Toggle Enabled',
			            cls: 'x-icon-btn',
			            iconCls: 'x-icon-btn-toggle',
			            handler: function(){
			                Ext.getCmp('form-widgets').getForm().items.each(function(ctl){
			                    ctl.setDisabled(!ctl.disabled);
			                });
			            }
			        },{
			            text: 'Reset Form',
			            cls: 'x-icon-btn',
			            iconCls: 'x-icon-btn-reset',
			            handler: function(){
			                Ext.getCmp('form-widgets').getForm().reset();
			            }
			        },{
			            text:'Validate',
			            cls: 'x-icon-btn',
			            iconCls: 'x-icon-btn-ok',
			            handler: function(){
			                Ext.getCmp('form-widgets').getForm().isValid();
			            }
			        }]
			    },{
					title: "Panels",
					items: [{
				        xtype: 'panel',
				        height: 150,
				        title: 'Basic Panel',
				        bodyStyle: {padding: '5px'},
				        html: 'Some content',
				        collapsible: true
				    },{
				        xtype: 'panel',
				        height: 150,
				        title: 'Masked Panel',
				        bodyStyle: {padding: '5px'},
				        html: 'Some content',
				        collapsible: true,
				        listeners: {
				            'render': function(p){
				                p.body.mask('Loading...');
				            },
				            delay: 50
				        }
				    },{
				        xtype: 'panel',
				        height: 150,
				        title: 'Framed Panel',
				        html: 'Some content',
				        frame: true,
				        collapsible: true	
				    }]
				},{
			        xtype: 'grid',
			        title: 'Grid',
			        store: new Ext.data.SimpleStore({
				        fields: [
				           {name: 'company'},
				           {name: 'price', type: 'float'},
				           {name: 'change', type: 'float'},
				           {name: 'pctChange', type: 'float'},
				           {name: 'lastChange', type: 'date', dateFormat: 'n/j h:ia'}
				        ],
				        sortInfo: {
				            field: 'company', direction: 'ASC'
				        },
				        data: [
					        ['3m Co',71.72,0.02,0.03,'9/1 12:00am'],
					        ['Alcoa Inc',29.01,0.42,1.47,'9/1 12:00am'],
					        ['Altria Group Inc',83.81,0.28,0.34,'9/1 12:00am'],
					        ['American Express Company',52.55,0.01,0.02,'9/1 12:00am'],
					        ['American International Group, Inc.',64.13,0.31,0.49,'9/1 12:00am'],
					        ['AT&T Inc.',31.61,-0.48,-1.54,'9/1 12:00am'],
					        ['Boeing Co.',75.43,0.53,0.71,'9/1 12:00am'],
					        ['Caterpillar Inc.',67.27,0.92,1.39,'9/1 12:00am'],
					        ['Citigroup, Inc.',49.37,0.02,0.04,'9/1 12:00am'],
					        ['E.I. du Pont de Nemours and Company',40.48,0.51,1.28,'9/1 12:00am']
					    ]
				    }),
			        columns: [
			            {id:'company',header: "Company", width: 160, sortable: true, dataIndex: 'company'},
			            {header: "Price", width: 75, sortable: true, renderer: 'usMoney', dataIndex: 'price'},
			            {header: "Change", width: 75, sortable: true, dataIndex: 'change'},
			            {header: "% Change", width: 75, sortable: true, dataIndex: 'pctChange'},
			            {header: "Last Updated", width: 85, sortable: true, renderer: Ext.util.Format.dateRenderer('m/d/Y'), dataIndex: 'lastChange'}
			        ],
			        stripeRows: true,
			        autoExpandColumn: 'company',
			        loadMask: true,
			        height: 200,
			        width: 450,
			        tbar: [
			            { text: 'Toolbar' },'->',
			            new Ext.form.TwinTriggerField({
			                xtype: 'twintriggerfield',
			                trigger1Class: 'x-form-clear-trigger',
			                trigger2Class: 'x-form-search-trigger'
			            })
			         ]
			    },
			    accConfig
				,{
					title: "Progressbar and Sliders",
			        width: 450,
			        height: 200,
			        bodyStyle: {padding: '15px'},
			        items: [{
			            xtype: 'progress',
			            value: .5,
			            text: 'Progress text...'
			        },{
			            xtype: 'slider',
			            value: 50
			        },{
			            xtype: 'slider',
			            vertical: true,
			            value: 50,
			            height: 100
			        }]					
				},{
					title: "Etc"
				}]			
			}
		});	
		this.supr().initComponent.call(this);
	}
});