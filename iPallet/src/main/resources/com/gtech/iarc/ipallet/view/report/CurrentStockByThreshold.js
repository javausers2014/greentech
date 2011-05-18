/*
 * http://stackoverflow.com/questions/4864359/can-the-colors-on-charts-in-extjs-yui-charts-be-changed-dynamically
 * 
 */
Ext.ns("iPallet.Report");
iPallet.Report.StockThreshold = Ext.extend(Webtop.View, {	
	title: "Current Stock",
	initComponent: function() {		
		
		var ownerStore = new Ext.data.Store({
			id:'ownerStore',
			restful: true,
			autoLoad: false,
			proxy: new Ext.data.HttpProxy({ url: XWT_BASE_PATH + "/services/rest/owner" }),
			reader: new Ext.data.JsonReader({
    			totalProperty: 'total',
    			successProperty: 'success',
    			idProperty: 'id',
    			root: 'data',
				messageProperty: 'message'
			},[
				{ name: "fullName" },
				{ name: "code" }
			])
		});


//		var prodStore = new Ext.data.Store({
//			restful: true,
//			autoLoad: false,
//			autoSave: true,
//			proxy: new Ext.data.HttpProxy({ url: XWT_BASE_PATH + "/services/rest/stock/owner" + getOwnerSelection() }),
//			reader: new Ext.data.JsonReader({
//    			totalProperty: 'total',
//    			successProperty: 'success',
//    			idProperty: 'id',
//    			root: 'data',
//				messageProperty: 'message'
//			},[
//				{ name: "product" },
//				{ name: "uom" },
//				{ name: "basicquantity" },
//				{ name: "diffquantity" },
//				{ name: "warning", type: 'boolean' },
//				{ name: "highlight", type: 'boolean' },
//				{ name: "alarm", type: 'boolean' }
//			])
//		});

		function getOwnerSelection(){
			
		}
	    function generateData(owner){
		    var data_PHILIPS = [
	            {ownerfullname: 'Philips Electrics Pte Ltd', product:'IRON', uom: 'UNT',qty:3430,safeqty:0,alarmqty:0,warnqty:3430,hlqty:0,warning:'true',highlight:'false',alarm:'false',threshold:3200,tolerance:200},
	            {ownerfullname: 'Philips Electrics Pte Ltd', product:'ePOT', uom: 'UNT',qty:1235,safeqty:1235,alarmqty:0,warnqty:0,hlqty:0,warning:'false',highlight:'false',alarm:'false',threshold:500,tolerance:50},
	            {ownerfullname: 'Philips Electrics Pte Ltd', product:'BULB', uom: 'BOX',qty:5789,safeqty:0,alarmqty:5789,warnqty:0,hlqty:0,warning:'false',highlight:'false',alarm:'true',threshold:6000,tolerance:300},
	            {ownerfullname: 'Philips Electrics Pte Ltd', product:'COOKER', uom: 'UNT',qty:298,safeqty:0,alarmqty:0,warnqty:298,hlqty:0,warning:'true',highlight:'false',alarm:'false',threshold:280,tolerance:'20'},
	            {ownerfullname: 'Philips Electrics Pte Ltd', product:'LIGHT', uom: 'UNT',qty:211,safeqty:0,alarmqty:0,warnqty:0,hlqty:211,warning:'false',highlight:'true',alarm:'false',threshold:200,tolerance:'10'}
	        ];
	         var data_dell =[
	            {ownerfullname: 'Dell Computers Ltd', product:'HDD', uom: 'UNT',qty:1430,safeqty:1430,alarmqty:0,warnqty:0,hlqty:0,warning:'false',highlight:'false',alarm:'false',threshold:1000,tolerance:100},
	            {ownerfullname: 'Dell Computers Ltd', product:'MEM', uom: 'UNT',qty:2235,safeqty:2235,alarmqty:0,warnqty:0,hlqty:0,warning:'false',highlight:'false',alarm:'false',threshold:2000,tolerance:50},
	            {ownerfullname: 'Dell Computers Ltd', product:'CASE', uom: 'BOX',qty:1589,safeqty:1589,alarmqty:0,warnqty:0,hlqty:0,warning:'false',highlight:'false',alarm:'false',threshold:1000,tolerance:300},
	            {ownerfullname: 'Dell Computers Ltd', product:'MICE', uom: 'UNT',qty:2398,safeqty:2389,alarmqty:0,warnqty:0,hlqty:0,warning:'false',highlight:'false',alarm:'false',threshold:1800,tolerance:20},
	            {ownerfullname: 'Dell Computers Ltd', product:'KEYBOARD', uom: 'UNT',qty:1211,safeqty:0,alarmqty:289,warnqty:0,hlqty:0,warning:'false',highlight:'false',alarm:'true',threshold:1500,tolerance:10}
	        ];
	        if(owner=='DELL'){
	        	return data_dell;
	        }else{
	        	return data_PHILIPS;
	        }
		    
		}
		
		var prodStore = new Ext.data.JsonStore({
	        fields:['ownerfullname','product', 'uom','qty','safeqty','alarmqty','warnqty','hlqty','warning','highlight','alarm','threshold','tolerance'],
	        data: generateData('PHILIPS')
	    });	
	    
		var prodStore1 = new Ext.data.JsonStore({
	        fields:['ownerfullname', 'product', 'uom','qty','safeqty','alarmqty','warnqty','hlqty','warning','highlight','alarm','threshold','tolerance'],
	        data: [
	            {ownerfullname: 'Philips Electrics Pte Ltd', product:'IRON', uom: 'UNT',qty:3430,safeqty:0,alarmqty:0,warnqty:3430,hlqty:0,warning:'true',highlight:'false',alarm:'false',threshold:3200,tolerance:200},
	            {ownerfullname: 'Philips Electrics Pte Ltd', product:'ePOT', uom: 'UNT',qty:1235,safeqty:1235,alarmqty:0,warnqty:0,hlqty:0,warning:'false',highlight:'false',alarm:'false',threshold:500,tolerance:50},
	            {ownerfullname: 'Philips Electrics Pte Ltd', product:'BULB', uom: 'BOX',qty:5789,safeqty:0,alarmqty:5789,warnqty:0,hlqty:0,warning:'false',highlight:'false',alarm:'true',threshold:6000,tolerance:300},
	            {ownerfullname: 'Philips Electrics Pte Ltd', product:'COOKER', uom: 'UNT',qty:298,safeqty:0,alarmqty:0,warnqty:298,hlqty:0,warning:'true',highlight:'false',alarm:'false',threshold:280,tolerance:'20'},
	            {ownerfullname: 'Philips Electrics Pte Ltd', product:'LIGHT', uom: 'UNT',qty:211,safeqty:0,alarmqty:0,warnqty:0,hlqty:211,warning:'false',highlight:'true',alarm:'false',threshold:200,tolerance:'10'}
	        ]
	    });

		var prodStore2 = new Ext.data.JsonStore({
	        fields:['ownerfullname', 'product', 'uom','qty','safeqty','alarmqty','warnqty','hlqty','warning','highlight','alarm','threshold','tolerance'],
	        data: [
	            {ownerfullname: 'Dell Computers Ltd', product:'HDD', uom: 'UNT',qty:1430,safeqty:1430,alarmqty:0,warnqty:0,hlqty:0,warning:'false',highlight:'false',alarm:'false',threshold:1000,tolerance:100},
	            {ownerfullname: 'Dell Computers Ltd', product:'MEM', uom: 'UNT',qty:2235,safeqty:2235,alarmqty:0,warnqty:0,hlqty:0,warning:'false',highlight:'false',alarm:'false',threshold:2000,tolerance:50},
	            {ownerfullname: 'Dell Computers Ltd', product:'CASE', uom: 'BOX',qty:1589,safeqty:1589,alarmqty:0,warnqty:0,hlqty:0,warning:'false',highlight:'false',alarm:'false',threshold:1000,tolerance:300},
	            {ownerfullname: 'Dell Computers Ltd', product:'MICE', uom: 'UNT',qty:2398,safeqty:2389,alarmqty:0,warnqty:0,hlqty:0,warning:'false',highlight:'false',alarm:'false',threshold:1800,tolerance:20},
	            {ownerfullname: 'Dell Computers Ltd', product:'KEYBOARD', uom: 'UNT',qty:1211,safeqty:0,alarmqty:289,warnqty:0,hlqty:0,warning:'false',highlight:'false',alarm:'true',threshold:1500,tolerance:10}
	        ]
	    });

	    var ownerSelect = new Ext.form.ComboBox({
	            	id:'ownerselect',
                 	store: ownerStore,
                 	mode: 'remote',
                 	displayField: 'fullName',
                 	valueField: 'code',
                 	typeAhead: false,
                 	triggerAction: 'all',
                 	lazyRender: false,
               	    listeners: {
					    select: function(combo, record, index) {
					    	var mainchart = Ext.getCmp('mainChart');
					    	mainchart.store.loadData(generateData(combo.getValue()));	
					    	mainchart.yAxis.title = record.get('fullName');
					    },
					    afterrender:function(combo) {
					    	combo.store.load();
							combo.store.on('load',function(store, records,options ){
								var ownerSelect = Ext.getCmp('ownerSelect');
								var firstowner = records[0].get('code');
								//combo.select(0,true);
								combo.value=firstowner;
								var mainchart = Ext.getCmp('mainChart');
					    		mainchart.store.loadData(generateData(firstowner));
							});					    	
					    	
					    }
					  }				
        	});

	    
		Ext.apply(this, {
        	width:500,
        	height:300,
        	layout:'border', 
        	tbar:[ownerSelect],
			items:[{
					xtype: 'stackedcolumnchart',
					width: '400',
					height: '200',
					id:'mainChart',
		            store: prodStore2,
		            region:'center',
		            xField: 'product',
		            yAxis: new Ext.chart.NumericAxis({
		            	fieldLabel: 'Stocks',
		            	stackingEnabled: true,
		                labelRenderer : Ext.util.Format.numberRenderer('0,0')
		            }),
		            xAxis:{
		            	fieldLabel:'Product'		         
		            },
		            tipRenderer : function(chart, record, index, series){
		            	
		                if(record.data.alarm=='true'){
		                    return 'Stock: '+Ext.util.Format.number(record.data.alarmqty, '0,0') + ' ' + record.data.uom 
		                    + ' * SHORT: '+Ext.util.Format.number(record.data.threshold-record.data.alarmqty, '0,0') + ' ' + record.data.uom;
		                }else {
		                    return 'Stock: '+Ext.util.Format.number(record.data.qty, '0,0') + ' ' + record.data.uom 
		                    + ' * Safe Level: '+Ext.util.Format.number(record.data.threshold, '0,0') + ' ' + record.data.uom;              	
		                }
		            },		            
		            series: [
		            	{		
								yField: 'safeqty',
								style: { 
									fillColor   : 0x00BB00,
									borderColor : 0x33AA33,
		                			lineColor   : 0x33AA33
								}
							},
							{
								yField: 'alarmqty',
								style: { 
									fillColor   : 0xAA0000,
									borderColor : 0x33AA33,
		                			lineColor   : 0x33AA33						
								}
							},{
								yField: 'warnqty',
								style: { 
									fillColor   : 0xAAFFAA,
									borderColor : 0x33AA33,
		                			lineColor   : 0x33AA33						
								}
							},{
								yField: 'hlqty',
								style: { 
									fillColor   : 0x0000AA,
									borderColor : 0x33AA33,
		                			lineColor   : 0x33AA33						
								}
							}		        
			            ]
				}]
				
		});	    
		this.supr().initComponent.call(this);
	}
});