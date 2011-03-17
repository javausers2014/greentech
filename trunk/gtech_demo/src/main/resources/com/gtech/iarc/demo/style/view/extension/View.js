Ext.ns("EMobility.Intuity");
/*
 * Generic view to be extended by all Emobility Driver Portal View
 * */
EMobility.Intuity.View = Ext.extend(Webtop.View, {	
	baseCls: 'x-plain gtech-blue',
	
//	width: 982,
	width: 996,
//	width: 1008,
	height: 542,
	
	// To be removed in new version of Webtop
	resizable: false,
	minimizable: false,
	// maximizable: false,
	maximized: true,
	
	initView: Ext.emptyFn,
	
	// private
	buildToolbarTitle: function() {
		return [
			// Title
			{
				xtype: 'tbtext',
				cls: 'iarc-view-header-toolbar-text',
				text: this.toolbarTitle
			}, '->'
		];
	},
	
	buildToolbarRight: Ext.emptyFn,
	
	// private
	buildToolbar: function() {
		var tr = this.buildToolbarRight();		
		
		return tr ? this.buildToolbarTitle().concat(tr)
				  :	this.buildToolbarTitle();
	},
	
	initComponent: function() {
		
		var view = this;
		
		Ext.apply(this, {
			layout:'vbox',
			layoutConfig: {
				align : 'stretch',
				pack  : 'start'
			},
			listeners: {
				afterrender: {
					fn: this.initView.createDelegate(this),
					buffer: 500
				}
			},
			style: 'padding-top: 10px; background: #fff;',
			defaults: { border: false, header: false },
			items: [
				{
					height	: 50,
					style	: 'padding: 10px',
					cls		: 'iarc-view-header-toolbar',						
					xtype	: 'toolbar',
					items	: this.buildToolbar()
				},
				{
					height: 10
				},
				{
					flex: 1,
					layout: 'hbox',
					layoutConfig: { align : 'stretch', pack  : 'start' },
					defaults: { border: false },
					items: [
						Ext.apply(this.buildMainLeft(), {
							width: 643
						}),
						Ext.apply(this.buildMainRight(), {
								region		: 'east',
								width		: 339,
								style		: 'padding-left: 10px; background: #fff;',								
								defaults	: { border: false }
							}
						)
					]
				}
			]
		});
		
		EMobility.Intuity.View.superclass.initComponent.call(this);
	}
});

function frameBox(title, url) {
	new Ext.Window({ 
		title: title,
		width: 750, 
		height: (Ext.lib.Dom.getViewHeight() * 0.8), 
		modal: true, 
		draggable: false,
		layout: 'fit',
		items: {
			xtype: "box",
			autoEl: {
				tag: "iframe",
				src: url,
				frameborder: 0
			}
		}
	}).show();
}