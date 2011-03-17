Ext.ns("WebtopDemo");
WebtopDemo.VisualRulesClient = Ext.extend(Webtop.View, {	
	title: "Visual Rules Hello World for Webtop",
	width: 400,
	height: 300,
	maxInstance: 1,
	initComponent: function() {
	
	
		this.supr().initComponent.call(this);
		
		VRService.callIt({
			callback: function(data) {
				alert("Server said " + data);
			}
		});
	
	
	
	}
});