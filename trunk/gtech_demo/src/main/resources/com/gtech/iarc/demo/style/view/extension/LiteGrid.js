Ext.ns("EMobility.Intuity");
/**
 * @class Ext.grid.GridPanel
 * @extends Ext.Panel
 * 
 * Generic simplified grid to be used in all Emobility Driver Portal Views 
 */
Ext.ns('Ext.ux.grid');

Ext.ux.grid.LiteGrid = Ext.extend(Ext.grid.GridPanel, {	
	trackMouseOver: false,
	enableHdMenu: false,
	enableColumnResize: false,
	enableColumnMove: false,
	autoScroll: true
});

Ext.reg('lite-grid', Ext.ux.grid.LiteGrid);