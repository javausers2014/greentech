Ext.ns("WebtopDemo");
WebtopDemo.ReverseAjax = Ext.extend(Webtop.View, {	
	title: "Reverse Ajax",
	width: 400,
	height: 110,	
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