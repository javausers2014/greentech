Ext.ns("gtech.iarc");
/*
 * Generic view to be extended by all gTech View
 * */
gtech.iarc.View = Ext.extend(Webtop.View, {
	iconCls: "icon-x16-chargingactions",
	
	minWidth: 500,
	minHeight: 400,
	
	width: 850,
	height: 500,
	
	maximizable: true,
	resizable: true	
});