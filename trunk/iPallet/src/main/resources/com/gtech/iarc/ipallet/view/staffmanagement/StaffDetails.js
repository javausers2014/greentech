Ext.ns("KitchenSink.UserManagement");
KitchenSink.UserManagement.UserInfo = Ext.extend(Webtop.View, {
	title: "User Information",	
	initComponent: function() {
		var app = this;
		Ext.apply(this,{
			height: 50,
			layout: "border",
			items: [{
				xtype: "panel",
				width: 62,
				height: 50,
				border: false,
				region: "west",
				items: [{
					xtype: "box",					
					autoEl: {
						tag: "img",
						src: app.img("images/zoobmug.png"),
						style: "margin: 5px;"
					}					
				}]				
			},{
				xtype: "panel",
				region: "center",
				border: false,
				items: [{
					xtype: "box",
					autoEl: {						
						tag: "div",
						style: "margin-left: 10px; margin-top: 10px; margin-right:10px;",
						children: [{
							tag: "div",
							style: "font-size: 14px; font-weight: bold;",						
							html: "Zubair Hamed"
						},{
							tag: "div",
							html: "INST/EPW-SG"
						},{
							tag: "div",
							style: "border-top: 1px solid #cccccc; margin-top: 5px;",
							children: [{
								tag: "img",									
								src: app.img("classpath:com/innovations/webtop/web/resources/iconsets/silk/email_error.png", true),
								style: "float: left;"
							},{
								tag: "div",
								html: "&nbsp;<b>5</b> tasks in inbox"
							}]
						}]
					}
				}]
			}],
			bbar: [
				"->",
				{
					text: "<b>Preferences</b>",
					iconCls: "icon-silk-heart"				
				},{ 
					text: "<b>Log Out</b>",
					iconCls: "icon-silk-lock-go"
				}
			]
		});	
		this.supr().initComponent.call(this);
	}
});


