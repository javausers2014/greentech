Ext.ns("EMobility");
/*
 * Generic view to be extended by all Emobility View
 * */
EMobility.View = Ext.extend(Webtop.View, {
	title: "E-Mobility View",
	iconCls: "icon-x16-chargingactions",
	cls: 'emo-view',
	
	minWidth: 500,
	minHeight: 400,
	
	width: 850,
	height: 500,
	
	maximizable: true,
	resizable: true	
});