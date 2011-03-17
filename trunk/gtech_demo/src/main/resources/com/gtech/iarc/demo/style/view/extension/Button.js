/**
 * @class Ext.ux.Button
 * @extends Ext.Button
 * 
 * Generic blue button to be used in all Emobility Driver Portal Views 
 */
Ext.ns('Ext.ux');

Ext.ux.Button = Ext.extend(Ext.Button, {	
	cls: 'iarc-btn'
});

Ext.reg('iarc-button', Ext.ux.Button);