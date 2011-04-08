Ext.ns("EMobility", "EMobility.CustomerView", "EMobility.CustomerView.Billing");

EMobility.CustomerView.Billing.ImportantInformation = Ext.extend(Webtop.View, {	

	initComponent : function() {

		// declaring of variables.
		var balance_amt=210;

		var updated_date = "01-10-2010";
	
	
		/*BillService.getAmount({
		callback:function(amt){
			//Ext.Msg.alert(balance_amt);
			
			Ext.getCmp('ref').update("SGD " + amt
						+ " Balance on last statement as of " + updated_date);
		}
		
		});*/

		Ext.apply(this, {
			layout: 'fit',
			header: false,
			items: {
				xtype: 'fieldset',
				title : "Important Information",
				defaults: { border: false },
				items : {
				 	html: "Pending means that we receive some trigger, but CTG has not received the money yet"
				 	   	+	"</br></br>Settlement will only happen when we receive the report from CTG. We will receive the report at the end of each month "
				 	   	+	"</br></br>GIRO collection will be performed at 5th day of the month"
				 	   	+	"</br></br>eNETS will do settlement to RBSI at the 5th and the 20th day of the month"
				 	   	+	"</br></br>CTG email address:ctg@bosch.com"
				 	   
				}
			}						
		});
		
		this.supr().initComponent.call(this);
	}
});


Ext.reg('emo-view-billing-important-information', EMobility.CustomerView.Billing.ImportantInformation);