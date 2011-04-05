Ext.ns('Ext.ux.data');

/**
 * Simple Array Reader reads values to the effect [ 1, 2, 3 ]
 * while using ArrayStore would Require [ [1], [2], [3] ]
 * 
 * @author Ankur Kapila
 * @class Ext.ux.data.SimpleArrayReader
 * @extends Ext.data.DataReader
 */
Ext.ux.data.SimpleArrayReader = Ext.extend(Ext.data.ArrayReader, {
	readRecords : function(o){
		Ext.each(o, function(item,i){
			o[i] = [o[i]];
		});
		
		return Ext.ux.data.SimpleArrayReader.superclass.readRecords.call(this, o);
	}
});
	
/**
 * Store using Simple Array Reader 
 * 
 * @class Ext.ux.data.SimpleArrayStore
 * @extends Ext.data.Store
 */
Ext.ux.data.SimpleArrayStore = Ext.extend(Ext.data.Store, {
    constructor: function(config){
        Ext.ux.data.SimpleArrayStore.superclass.constructor.call(this, Ext.apply(config, {
            reader: new Ext.ux.data.SimpleArrayReader(config)
        }));
    }
});