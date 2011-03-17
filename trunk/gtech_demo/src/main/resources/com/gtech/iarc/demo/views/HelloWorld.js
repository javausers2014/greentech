Ext.ns("WebtopDemo");
WebtopDemo.HelloWorld = Ext.extend(Webtop.View, {	
	title: "<@i18nText key="view.helloworld" />",
	width: 400,
	height: 300,	
	maxInstance: 2,
	initComponent: function() {
		var app = this;		
				
		Ext.apply(this,{
			items: new WebtopDemo.TestLab()
		});	
		this.supr().initComponent.call(this);
	}
});

function frameBox(title, url) {
	alert("!");
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