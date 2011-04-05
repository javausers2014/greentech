/**
 * @class Ext.ux.AnimatedLoadMask
 * @extends Ext.LoadMask
 * Extended version of Ext.LoadMask that provides smooth hide animation for the LoadMask
 * @author Totti Anh Nguyen
</code></pre>
 * @constructor
 * Create a new LoadMask
 * @param {Mixed} el The element or DOM node, or its id
 * @param {Object} config The config object
 */
Ext.ns('Ext.ux');
Ext.ux.AnimatedLoadMask = Ext.extend(Ext.LoadMask, {
	/**
     * Hide this LoadMask.
     * The configs of endOpacity and duration are fixed for VRModeler.  	 
     */
    hide: function(){
        var mask = Ext.Element.data(this.el.dom, 'mask');
		if(mask){
			mask.fadeOut({
				endOpacity	: .2,
				easing		: 'easeOut',
				duration	: .3,
				remove		: false,
				useDisplay	: false,
				callback	: this.onLoad,
				scope		: this
			});							
		}
    }
});