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
	    
		function addnewstaff() {
		  var firstName = Ext.getCmp('firstName').getValue();//dwr.util.getValue("firstName");
		  var lastName = Ext.getCmp('lastName').getValue();//dwr.util.getValue("lastName");
		  var email = Ext.getCmp('email').getValue();//dwr.util.getValue("email");
		  var bdate = Ext.getCmp('birthdate').getValue();//dwr.util.getValue("birthdate");
		  var role = Ext.getCmp('role').getValue();//dwr.util.getValue("role");
		  var department = Ext.getCmp('dept').getValue();//dwr.util.getValue("dept");
		  /*
		   * private String staffNo;
	private String fullName;
	private String firstName;
	private String lastName;
	private String email;
	private Date birthDate;
		   */
		  var newStaff = {
		  	'fullName': firstName+lastName,
		  	'firstName':firstName,
		  	'lastName':lastName,
		  	'email':email,
		  	'role':role,
		  	'department':department,
		  	'staffNo':role+'094'
		  	};
		  RemoteStaffService.addStaff(newStaff, function(){});
		}
    		
		Ext.apply(this, {
			layout: 'border',
			buttons: [{
				text: 'OK',
				listeners: {
					click: function (btn, evt) {
						addnewstaff();
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
							{ 	fieldLabel: 'First Name',
								id:'firstName',
								allowBlank:false,
            					blankText:"Please enter the first name"
							},
							{ 	fieldLabel: 'Last Name',
								id:'lastName',
								allowBlank:false,
            					blankText:"Please enter the last name"
            				},
							{   fieldLabel: 'Email',
								id:'email',
								vtype:'email',
								allowBlank:false,
            					blankText:"Please enter valid email" 
								
							},
							{
				            	xtype:'combo', 
				            	id:'role',
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
				            	id:'dept',
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
		                    	id:'birthdate',
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