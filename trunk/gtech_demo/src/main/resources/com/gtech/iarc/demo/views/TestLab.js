Ext.ns("WebtopDemo");
WebtopDemo.TestLab = Ext.extend(Webtop.View, {	
	title: "Testing Space",
	width: 400,
	height: 300,	
	maxInstance: 2,
	initComponent: function() {
		var app = this;		
		
		Ext.apply(this,{			
			buttons: [{
				text: "Set title",
				handler: function() {
					app.setTitle("ABC");
				}				
			}]			
		});	
		this.supr().initComponent.call(this);
	}
});