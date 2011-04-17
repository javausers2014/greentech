Ext.ns("Ext.ux.form");

/**
 * Provides traditional Select dropdown
 * 
 * @class Ext.ux.form.SelectBox
 * @extends Ext.form.ComboBox
 */
Ext.ux.form.SelectBox = Ext.extend(Ext.form.ComboBox,{
	editable: false,
	typeAhead: false,
	forceSelection : true,
	triggerAction : "all"
});
Ext.reg("select", Ext.ux.form.SelectBox);