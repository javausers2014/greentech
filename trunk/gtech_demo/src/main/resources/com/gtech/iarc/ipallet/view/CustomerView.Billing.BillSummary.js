Ext.ns("EMobility", "EMobility.CustomerView", "EMobility.CustomerView.Billing");

EMobility.CustomerView.Billing.BillSummary = Ext.extend(Webtop.View, {
	title : "Billing Details",
	iconCls : "icon-x16-billing",

	resizable : true,

	width : 850,
	height : 350,

	initComponent : function() {

		
		var _p = this;
		var userId = null;
		
		if(_p.model && _p.model.loginName){
			userId = _p.model.loginName;
		}

        var ctx = $webtop.getCustomContext();

		var enets_login_url 		= ctx.url.enets_login;
		var giro_form_download_url  = ctx.url.giro_download;		
		
		Ext.apply(this, {
			
			frame : true,
			
			bodyStyle : 'padding:10px 10px',

			
			items : [
			{
				html : "SGD " + "..." + " Balance on last statement as of " + "...",
						id:'ref'
						
			},  {
				html : "<br><br>"
						+ "The details provided above are for your information only and are not a substitute for your monthly bill."
						+ "</br>"
						+ "Please allow 2-4 weeks time to process your payment."
						+ "</br>"
						+ "Payment made after 20th day of the month will be reflected in the following month statement."
						+ "</br></br>"						
						+ "<a href='"+giro_form_download_url+"'>GIRO Application</a>"
			}

			]
		});
		this.supr().initComponent.call(this);
		this.on("afterrender", function(){
			BillsService.getBillInfo(userId,{
				callback:function(array){
					if(array[0]!=null)
	        		{
	        	Ext.getCmp('ref').update("<font size='4'>SGD "+array[1]+" Balance on last statement as of "+array[0]+". </font>");
	        		}
	        	else
	        		{
	        		Ext.getCmp('ref').update("<font size='4'> Sorry.Your billing information is not available now.</font>");
	        		}
				}
			})			
		});
	}
});


Ext.reg('emo-view-billing-summary', EMobility.CustomerView.Billing.BillSummary);