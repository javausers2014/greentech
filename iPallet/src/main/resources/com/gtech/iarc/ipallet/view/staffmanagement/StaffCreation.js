Ext.ns('iPallet.Staff');

iPallet.Staff.Creation = Ext.extend(Webtop.View, {
	title: '<@i18nText key="ipallet.view.title.staffmanagement"/>',
	iconCls: 'icon-silk-user-add',
	width: 500,
	height: 420,
	initComponent: function() {
		var app = this;
		var roleds = new Ext.data.ArrayStore({
	        data: [
	            ['CTO', 'Chief Tech Office'], 
	            ['CEO', 'Chief Executive Office'], 
	            ['SNM', 'Senior Manager'], 
	            ['DPM', 'Department Manager'], 
	            ['OPT', 'Operator']
	           ],
	        fields: ['key','label']
	    });


	    var dptds = new Ext.data.ArrayStore({
	        data: [
	            ['HR', 'Human resource'], 
	            ['IFS', 'Infrustracure'], 
	            ['SDV', 'Soft.Development'],
	            ['APS', 'App. Support']
	           ],
	        fields: ['key','label']
	    });
    		
		Ext.apply(this, {
			layout: 'border',
			buttons: [{
				text: 'OK',
				listeners: {
					click: function (btn, evt) {
						//Adding remote call
					}
				}				
			}],
			items: [
				{
					xtype: 'panel',
					region: 'west',
					width: 200,
					height: 200,
					items: [
						{
							xtype: 'box',
							autoEl: {
								tag: 'div',
								children: [
									{
										tag: 'img',
										src: app.img('../images/uploadphoto.png')
									}
								]
							}
						}
					]
				},{
					xtype: 'tabpanel',
					region: 'center',
					activeTab: 0,
					items: [{ 
						title: 'General',
						xtype: 'form',
						id:'staffForm',
						padding: 10,						
						labelAlign: 'top',
						defaultType: 'textfield',
						defaults: {
							width: '100%'
						},
						items: [
							{ fieldLabel: 'First Name' },
							{ fieldLabel: 'Middle' },
							{ fieldLabel: 'Last Name' },
							{ fieldLabel: 'Email' },
							{
				            	xtype:'combo', 
				            	fieldLabel: 'Role',
				            	hiddenName: 'label',
		                     	store: roleds,
		                     	displayField: 'label',
		                     	valueField: 'key',
		                        typeAhead: true,
		                        mode: 'local',
		                     	triggerAction: 'all',
		                     	selectOnFocus:true
							},
							{
				            	xtype:'combo', 
				            	fieldLabel: 'Department',
				            	hiddenName: 'label',
		                     	store: dptds,
		                     	displayField: 'label',
		                     	valueField: 'key',
		                        typeAhead: true,
		                        mode: 'local',
		                     	triggerAction: 'all',
		                     	selectOnFocus:true
							},
							{
		                    	xtype:'datefield', 
		                        fieldLabel: 'Date of Birth',
		                        name: 'dob',
		                        width:190,
		                        allowBlank:false		
							}
						]						
					},
					{ 
						title: 'Contacts' 
					},
					{ 
						title: 'Others' 
					}
				]				
			}]
		});
		this.supr().initComponent.call(this);		
	}
});