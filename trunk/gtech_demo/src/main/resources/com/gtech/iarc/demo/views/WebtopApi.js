Ext.ns("WebtopDemo");
WebtopDemo.WebtopApi = Ext.extend(Webtop.View, {	
	title: "Webtop API",
	width: 400,
	height: 110,	
	maxInstance: 2,
	initComponent: function() {
		var app = this;		
		
		Ext.apply(this,{
			layout: "form",
			items: [{
				html: "Hello World"
			}]
		});	
		this.supr().initComponent.call(this);
	}
});