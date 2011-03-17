Ext.ns("WebtopDemo");
WebtopDemo.PublisherSubscriberDemo = Ext.extend(Webtop.View, {	
	title: "Publish-Subscribe",
	width: 500,
	height: 300,	
	plugins: ["webtop-messaging"],
	TEXTAREA_ID: Ext.id(),
	subscriberCount: 0,
	initComponent: function() {
		var app = this;		
		app.PUBLISHER_TEXTFIELD_ID = Ext.id();
		
		Ext.apply(this,{
			tbar: [ "->", 
			{
				text: "Open subscribers",
				iconCls: "icon-fugue-application-blog",
				handler: function() {
					app.subscriberCount++;
					
					var win = $webtop.getViewAsWindow({
						id: "webtopdemo.view.subscribe",
						config: {
							title: "Subscriber #" + app.subscriberCount
						}
					});
					
					if (win) {
						win.show();
					} else {
						$webtop.notifyError("Pub Sub Demo", "Max number of subscriber windows reached!");
						return;
					}
				}
			}],
			items: [{
				xtype: "textarea",
				readOnly: true,
				id: app.TEXTAREA_ID,
				width: "100%",
				height: 200
			},{
				items: [{
					xtype: "textfield",
					width: "100%",
					id: app.PUBLISHER_TEXTFIELD_ID
				},{
					xtype: "button",
					iconCls: "icon-fugue-arrow",
					text: "Publish!",
					handler: function() {
						var textField = Ext.getCmp(app.PUBLISHER_TEXTFIELD_ID);					
						var val = textField.getValue(); 
						app.publish("webtopdemo.pubsub.say", val);
					}				
				}]				
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
