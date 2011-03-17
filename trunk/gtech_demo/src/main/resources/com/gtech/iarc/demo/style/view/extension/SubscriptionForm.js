Ext.ns("EMobility.Intuity");

EMobility.Intuity.SubscriptionForm = Ext.extend(Ext.FormPanel, {
	labelAlign: 'top',
	labelSeparator: '',
	
	resetView: function() {
		// Reset form
		var d = this.savedData;
		if (d) {
			this.setFormValues(d);	
		}
		
		// Show default status
		this.showInfoMessage(this.viewMsg);
	}
});