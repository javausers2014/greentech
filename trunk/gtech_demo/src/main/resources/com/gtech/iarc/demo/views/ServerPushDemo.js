Ext.ns("WebtopDemo");
WebtopDemo.ServerPushDemo = Ext.extend(Webtop.View, {	
	title: "Server Push Demo",
	width: 600,
	height: 400,	
	plugins: ["webtop-messaging"],
	TEXTAREA_ID: Ext.id(),
	initComponent: function() {
		var app = this;

		Ext.apply(this,{
			layout: "fit",
			tbar: [ "->",
			{
				text: "Start Push",
				iconCls: "icon-fugue-status",
				handler: function() {
					ServerPushService.startPush();

				}
			},{
				text: "Stop Push",
				iconCls: "icon-fugue-status-busy",
				handler: function() {
					ServerPushService.stopPush();
				}
			}],
			items: [{
				xtype: "textarea",
				readOnly: true,
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
		app.subscribe("webtopdemo.serverpush", {
			fn: function(subject, msg) {
				var currText = textarea.getValue();							
				var saidText = "\n" + msg;
				textarea.setValue(saidText + currText);
				
				return;
			}
		});
	}
});
/*
	Toolbar to show publisher
		Textfield to send text
		Send button
		
	Toolbar to show additional subscriber
		Textarea to show log
		
	Textarea to show log
*/