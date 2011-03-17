Ext.ns("WebtopDemo");
WebtopDemo.Subscriber = Ext.extend(Webtop.View, {	
	title: "Subscriber",
	width: 500,
	height: 225,
	plugins: ["webtop-messaging"],
	maxInstance: 3,
	initComponent: function() {
		var app = this;		
		app.TEXTAREA_ID = Ext.id(),
		
		Ext.apply(this,{
			items: [{
				xtype: "textarea",
				id: app.TEXTAREA_ID,
				width: "100%",
				height: 200
			}]
		});	
		this.supr().initComponent.call(this);
	},
	afterRender: function() {
		var app = this;
		app.supr().afterRender.call(this);
		
		var textarea = Ext.getCmp(app.TEXTAREA_ID);
		
		// Subscribe to subject
		app.subscribe("webtopdemo.pubsub.say", {
			fn: function(subject, msg) {
				var currText = textarea.getValue();							
				var saidText = "\n" + new Date().format("g:i A") + " >> " + msg;
				textarea.setValue(saidText + currText);
				return;
			}
		});
	}
});
