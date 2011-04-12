Ext.ns("iPallet.Staff");

iPallet.Staff.Creation = Ext.extend(Webtop.View, {
	title: "<@i18nText key="ipallet.view.title.staffmanagement"/>",
	iconCls: "icon-silk-user-add",
	width: 500,
	height: 420,
	initComponent: function() {
		var app = this;
		var rolesDataStore = new Ext.data.ArrayStore({
	        data: [
	            ['1', 'Administrator'], 
	            ['2', 'Data Manager'], 
	            ['3', 'Approver'], 
	            ['4', 'Common User'], 
	            ['5', 'Project Owner']
	           ],
	        fields: ['value','text'],
	        sortInfo: {
	            field: 'value',
	            direction: 'ASC'
	        }
	    });
    
	    var groupsDataStore = new Ext.data.ArrayStore({
	        data: [
	            ['1', 'GWB Project Owner'], 
	            ['2', 'GWB User'], 
	            ['3', 'Asia-Pacific User']
	           ],
	        fields: ['value','text'],
	        sortInfo: {
	            field: 'value',
	            direction: 'ASC'
	        }
	    });
    		
		Ext.apply(this, {
			layout: "border",
			buttons: [{
				text: "OK",
				listeners: {
					click: function (btn, evt) {
						var request = {
							name: "Zubair Hamed",
							email: "zubair.mohammad@sg.bosch.com"
						};

						WebtopAction.call (
							"com.innovations.webtop.kitchensink.views.usermanagement.UserManagementAction", 
							"addUser", 
							request, {
							callback: function(data) {
								console.debug (data);
							}
						});
					}
				}				
			}],
			items: [{
				xtype: "panel",
				region: "west",
				width: 152,
				height: 150,
				items: [
					{
						xtype: "box",
						autoEl: {
							tag: "div",
							children: [
								{
									tag: "img",
									src: app.img("images/uploadphoto.png"),
								}
							]
						}
					}
				]
			},{
				xtype: "tabpanel",
				region: "center",
				activeTab: 0,
				items: [
					{ 
						title: "General",
						xtype: "form",
						padding: 10,						
						labelAlign: 'top',
						defaultType: 'textfield',
						defaults: {
								width: "100%"
						},
						items: [
							{
								xtype: "combo",
								value: "Zubair Hamed",
								fieldLabel: "Username" 
							},
							{ fieldLabel: "First Name" },
							{ fieldLabel: "Middle" },
							{ fieldLabel: "Last Name" },
							{ fieldLabel: "Email" }					
						]						
					},/*{ 
						title: "Roles",
						items: [
							{
								xtype: "itemselector",
								imagePath: 'images/ux',
								multiselects: [{
									width: 150,
									height: 250,
									store: rolesDataStore,
									displayField: 'text',
									valueField: 'value'
								},{
									width: 150,
									height: 250,
									store: [['10','Common User']]
								}]								
							}
						]
					},{ 
						title: "Groups",
						items: [
							{
								xtype: "itemselector",
								imagePath: 'images/ux',
		            multiselects: [{
		                width: 150,
		                height: 250,
		                store: groupsDataStore,
		                displayField: 'text',
		                valueField: 'value'
		            },{
		                width: 150,
		                height: 250,
		                store: [['10','Asia Pacific User']]
		            }]								
							}
						]						
					},*/{ 
						title: "Preferences" 
					}
				]				
			}]
		});
		this.supr().initComponent.call(this);		
	}
});