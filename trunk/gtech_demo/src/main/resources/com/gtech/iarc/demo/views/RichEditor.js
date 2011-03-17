Ext.ns("WebtopDemo");
WebtopDemo.RichEditor = Ext.extend(Webtop.View, {	
	title: "Rich Editor",
	width: 508,
	height: 358,	
	maxInstance: 2,
	initComponent: function() {
		var app = this;		
		
		Ext.apply(this,{
			tbar: ["->", {
				text: "Save as PDF"			
			}],
			items: [{
				xtype: "htmleditor"
			}]
		});	
		this.supr().initComponent.call(this);
	}
});