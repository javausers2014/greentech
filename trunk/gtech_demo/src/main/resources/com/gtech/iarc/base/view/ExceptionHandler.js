/**
 *	Defines Global Exception & Error Handler
 */
 Ext.onReady(function(){
 	// Common method to handle exceptions across entire application
 	var exceptionHandler = function(msg, e) {
 		var genericMsg = "Oops, there was an error processing your request. Please try the operation again later.";
 		if(msg){
 			if(msg.toLowerCase() == "error"){
 				msg = genericMsg;
 			}else{
 			    msg = Ext.util.Format.htmlEncode(msg);
 			}
 		}else{
 			msg = genericMsg;
 		}
 		
 		e = e || {};
 		
 		var defaultErrorBox = function(){			
			Ext.Msg.show({
				title: 	 "Error",
				msg:   	 msg,
				buttons: Ext.Msg.OK,
				icon:    Ext.MessageBox.ERROR
			});
 		};
 		
 		// Reset view after error occurs
		if( Ext.isFunction(this.resetView) ) {
			// Reset buttons & form values
			this.resetView();
		}
 		
		// Display error message
 		switch(e.target){
			case "INLINE" :
			case "VIEW"   : 
	 						if(Ext.isFunction(this.showErrorMessage)){
	 							this.showErrorMessage(msg);
	 						}else{
	 							defaultErrorBox();
	 						}
	 						break;
 			case "NOTIFY" : 
	 						$webtop.notifyError(msg);
	 						break;
			case "GLOBAL" : 
 			default:							
							defaultErrorBox(); 			
 		}
 	};
 	
	// Error handler for all services via $webtop.DwrProxy
	Ext.data.DataProxy.on('exception', function(proxy, type, action, params, msg, e) {
		// TODO: Need to tweak $webtop.DwrProxy to support default exception scope		
		exceptionHandler.call(
			proxy.api[action].exceptionScope || this,
			msg, e
		);
	});
	
	// Error handler for all services via DWR
 	dwr.engine.setErrorHandler(function(msg, e){ 		
		exceptionHandler.call(
			this.handlers[0].exceptionScope || this, 
			msg, e
		);	
 	});
 	
 	// Error handler for session time out
	dwr.engine.setTextHtmlHandler(function(status, responseText, contentType) {
		// TODO Verify this
  		Ext.Msg.show({
			title: 	 "Timed Out",
			msg:   	 "Your session has expired, please login again.",
			buttons: Ext.Msg.OK,
			icon:    Ext.MessageBox.ERROR,
			fn: function(){ window.document.location = $webtop.getCustomContext().url.logout; }
		});
	});
 });
 