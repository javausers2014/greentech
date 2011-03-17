Ext.ns("WebtopDemo");
WebtopDemo.EventsDashlet = Ext.extend(Webtop.View, {
	title: "Events",
	height: 300,
	plugins: ["webtop-messaging"],
	_LOGGER: Ext.id(), 
	initComponent: function() {
		var app = this;
		
		Ext.apply(this, {
			items:[{
				xtype: "textarea",
				id: app._LOGGER,
				width: "100%",
				height: "100%"		
			}]
		});
				
		this.supr().initComponent.call(this);
	},
	log: function (msg) {
		var app = this;
		var logger = Ext.getCmp(app._LOGGER);
		var oldMsg = logger.getValue();
		var newMsg = msg + "\n" + oldMsg;
		
		logger.setValue(newMsg);		
	},
	afterRender: function () {
		var app = this;
		app.supr().afterRender.call(this);		
		
		app.subscribe("webtop.os.relayout",{
			fn: function(subject, msg) {	
				app.log("[webtop.os.relayout] width: " + msg.width + ", height: " + msg.height );
			}
		});
				
		app.subscribe("webtop.os.loaded",{
			fn: function(subject, msg) {
				app.log("[webtop.os.loaded] Webtop has started up!");
			}
		});
		
		app.subscribe("webtop.os.shutdown", {
			fn: function(subject, msg) {
				app.log("[webtop.os.shutdown] Webtop shutting down.");
			}
		});
		
		app.subscribe("webtop.os.view.opened", {
			fn: function(subject, msg) {
				app.log("[webtop.os.view.opened] " + msg.title);
			}
		});
		
		app.subscribe("webtop.os.view.minimized", {
			fn: function(subject, msg) {
				app.log("[webtop.os.view.minimized] " + msg.title);
			}
		});
		
		app.subscribe("webtop.os.view.closed", {
			fn: function(subject, msg) {
				app.log("[webtop.os.view.closed] " + msg.title);
			}
		});
	}
});
