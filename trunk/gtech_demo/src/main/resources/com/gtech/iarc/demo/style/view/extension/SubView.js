Ext.ns("EMobility.Intuity");

EMobility.Intuity.SubView = Ext.extend(EMobility.MessageView, {
	
	getUserForm: function(basic) {
		var f = this.findById(this._ID.FORM);
		return basic ? f.getForm() : f;
	},
	
	resetView: function() {
		// Reset form
		var d = this.savedData;
		if (d) {
			this.setFormValues(d);	
		}
		
		// Show default status
		this.showInfoMessage(this.viewMsg);
	},
	
	buildSection: function(title, height, items, last) {
		return {
			layout:'hbox',
			height: height,
			cls: 'iarc-profile-section' + (last ? ' iarc-profile-section-last' : ''),
			layoutConfig: {
				align : 'stretch',
				pack  : 'start'
			},
			defaults: { border: false },
			items: [
				{
					cls: 'iarc-profile-section-title',
					html: title,
					width: 120
				},
				{
					flex: 1,
					defaults: { border: false },
					bodyStyle: 'padding-right: 35px',
					layout:'vbox',
					layoutConfig: {
						align : 'stretch',
						pack  : 'start'
					},
					items: items
				}
			]
		};
	},
	
	buildButtons: function(items) {
		return {
			height: 40,
			cls: 'iarc-accordion-buttons',								
			layout: 'form',
			buttons: items
		};
	}
});