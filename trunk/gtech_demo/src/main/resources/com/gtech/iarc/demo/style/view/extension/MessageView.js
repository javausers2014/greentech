Ext.ns("EMobility");
/**
 * Generic view containing message panel to be extended by all Emobility View
 * 
 * @author sgp0308
 *  
 * @class EMobility.View
 * @extends Webtop.View
 */
EMobility.MessageView = Ext.extend(Ext.Panel, {
	/**
     * Show a message on a view. By default, no icon is displayed next to the text.
	 * @param {String} text Text to be displayed as the message.
     * @return {Object} this._ID
     */
	showMessage: function(text) {
		this.setMessage('iarc-view-msg-no-icon', text);
	},
	
	/**
     * Show an info message on a view. By default, info (I) icon is displayed next to the text.
	 * @param {String} text Text to be displayed as the message.
     * @return {Object} this._ID
     */
	showInfoMessage: function(text) {
		this.setMessage('icon-silk-information', text);
	},	
	
	/**
     * Show a success message on a view. By default, green Success-Tick icon is displayed next to the text.
	 * @param {String} text Text to be displayed as the message.
     * @return {Object} this._ID
     */
	showSuccessMessage: function(text) {
		this.setMessage('icon-silk-accept', text);
	},
	
	/**
     * Show an error message on a view. By default, stop icon is displayed next to the text.
	 * @param {String} text Text to be displayed as the message.
     * @return {Object} this._ID
     */
	showErrorMessage: function(text) {
		this.setMessage('icon-silk-exclamation', text);		
	},
	
	/**
     * Show a loading message on a view. By default, ajax-loading icon is displayed next to the text.
	 * @param {String} text Text to be displayed as the message.
     * @return {Object} this._ID
     */
	showLoadingMessage: function(text) {
		this.setMessage('iarc-view-msg-loading-icon', text);
	},
	
	/**
     * Hide the currently displayed message on a view.
     */
	hideMessage: function() {
		var p = this.findById(this._ID.PANELMSG);
		if (!p.hidden) {
			p.hide();			
		}
	},

	/**
     * Initializes message IDs used in the view. To be used by subclasses.
	 * By default, view._ID.MSG is used for message panel
     * @return {Object} this._ID
     */
	initId: function() {
		return this._ID = {
			MSG 		: Ext.id(),
			PANELMSG 	: Ext.id()
		};
	},
	
	/**
     * Returns config object of Message Panel to be used in EMobility View	 
     * @return {Object} this._ID
     */
	buildMessagePanel: function(config) {
		var msg = "&nbsp;";
		
		if (config) {
			msg = config.defaultMsg || msg;
		}
		
		return {
			id: this._ID.PANELMSG,
			region: 'north',
			height: 45,
			bodyStyle: 'border-bottom: 1px solid #C3C3C4',
			style: 'padding: 10px 0px;',			
			html: "<div class='iarc-view-msg iarc-view-msg-no-icon' id='" + this._ID.MSG + "'>" +
						"<div class='iarc-view-msg-body'>" + msg + "</div>" +						
				  "</div>"
				  
		};
	},
	
	/**
     * Returns config object of Message Panel to be used in EMobility View	 
     * @return {Object} this._ID
     */
	buildErrorPanel: function() {
		return {
			id: this._ID.PANELMSG,
			region: 'north',
			hidden: true,			
			height: 52,			
			bodyStyle: 'border-bottom: 1px solid #D0D0D0; padding: 6px;',
			listeners: {
				hide: function() {
					this.doLayout(true);
				},
				afterrender: function(p) {
					var c = p.body.first().first();
					c.on('click', function() {
						this.hide();
					}, p);
				},
				beforeDestroy: function(p) {
					if (p.rendered) {
						var c = p.body.first().first();
						c.removeAllListeners();	
					}
				},
				scope: this
			},
			html: "<div class='iarc-view-msg iarc-view-msg-no-icon' id='" + this._ID.MSG + "'>" +						
						"<div class='x-tool x-tool-close'>&nbsp;</div>" +
						"<div class='iarc-view-exception-msg-body'>&nbsp;</div>" +						
				  "</div>"
				  
		};
	},
	
	// private
	msgCls: 'iarc-view-msg ',

	// private
	setMessage: function(css, text) {
		var msg = Ext.get(this._ID.MSG),
			p = this.findById(this._ID.PANELMSG);
		
		// Set icon
		msg.dom.className = this.msgCls + css;
		
		// Set text
		msg.last().update(text);
		
		// Display the panel
		if (p.hidden) {
			p.show();
			
			this.doLayout(true);
		}
	}
});