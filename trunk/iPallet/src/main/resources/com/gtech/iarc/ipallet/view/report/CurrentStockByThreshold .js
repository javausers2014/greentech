Ext.ns("iPallet.Report");
iPallet.Report.StockThreshold = Ext.extend(Webtop.View, {	
	title: "Sales",
	initComponent: function() {		
		
		var ownerStore = new Ext.data.Store({
			restful: true,
			autoLoad: true,
			autoSave: true,
			proxy: new Ext.data.HttpProxy({ url: XWT_BASE_PATH + "/services/rest/owner" }),
			reader: new Ext.data.JsonReader({
    			totalProperty: 'total',
    			successProperty: 'success',
    			idProperty: 'id',
    			root: 'data',
				messageProperty: 'message'
			},[
				{ name: "fullname" },
				{ name: "code" }
			])
		});
		
		var prodStore = new Ext.data.Store({
			restful: true,
			autoLoad: true,
			autoSave: true,
			proxy: new Ext.data.HttpProxy({ url: XWT_BASE_PATH + "/services/rest/stock" }),
			reader: new Ext.data.JsonReader({
    			totalProperty: 'total',
    			successProperty: 'success',
    			idProperty: 'id',
    			root: 'data',
				messageProperty: 'message'
			},[
				{ name: "product" },
				{ name: "uom" },
				{ name: "quantity" },
				{ name: "threshold" }
			])
		});
		
		
		var store = new Ext.data.JsonStore({
	        fields:['name', 'visits', 'views'],
	        data: [
	            {name:'Jul 07', visits: 245000, views: 3000000},
	            {name:'Aug 07', visits: 240000, views: 3500000},
	            {name:'Sep 07', visits: 355000, views: 4000000},
	            {name:'Oct 07', visits: 375000, views: 4200000},
	            {name:'Nov 07', visits: 490000, views: 4500000},
	            {name:'Dec 07', visits: 495000, views: 5800000},
	            {name:'Jan 08', visits: 520000, views: 6000000},
	            {name:'Feb 08', visits: 620000, views: 7500000}
	        ]
	    });
		
		Ext.apply(this, {
			iconCls:'chart',
        	width:500,
        	height:300,
        	layout:'fit',
        	items: [
        	{	xtype: 'columnchart',
	            store: store,
	            xField: 'name',
	            yAxis: new Ext.chart.NumericAxis({
	                displayName: 'Visits',
	                labelRenderer : Ext.util.Format.numberRenderer('0,0')
	            }),
	            tipRenderer : function(chart, record, index, series){
	                if(series.yField == 'visits'){
	                    return Ext.util.Format.number(record.data.visits, '0,0') + ' visits in ' + record.data.name;
	                }else{
	                    return Ext.util.Format.number(record.data.views, '0,0') + ' page views in ' + record.data.name;
	                }
	            },
	            chartStyle: {
	                padding: 10,
	                animationEnabled: true,
	                font: {
	                    name: 'Tahoma',
	                    color: 0x444444,
	                    size: 11
	                },
	                dataTip: {
	                    padding: 5,
	                    border: {
	                        color: 0x99bbe8,
	                        size:1
	                    },
	                    background: {
	                        color: 0xDAE7F6,
	                        alpha: .9
	                    },
	                    font: {
	                        name: 'Tahoma',
	                        color: 0x15428B,
	                        size: 10,
	                        bold: true
	                    }
	                },
	                xAxis: {
	                    color: 0x69aBc8,
	                    majorTicks: {color: 0x69aBc8, length: 4},
	                    minorTicks: {color: 0x69aBc8, length: 2},
	                    majorGridLines: {size: 1, color: 0xeeeeee}
	                },
	                yAxis: {
	                    color: 0x69aBc8,
	                    majorTicks: {color: 0x69aBc8, length: 4},
	                    minorTicks: {color: 0x69aBc8, length: 2},
	                    majorGridLines: {size: 1, color: 0xdfe8f6}
	                }
	            },
	            series: [{
	                type: 'column',
	                displayName: 'Page Views',
	                yField: 'views',
	                style: {
	                    color:0x99BBE8
	                }
	            },{
	                type:'line',
	                displayName: 'Visits',
	                yField: 'visits',
	                style: {
	                    color: 0x15428B
	                }
	            }]
	         }]
		});
	    
		this.supr().initComponent.call(this);
	}
});