/**
 * Overrides the logout handler
 * 
 * @author Ankur Kapila
 */
Ext.onReady(function(){
 	$webtop.logout = function(/* Auto Logout without Confirmation */ bForceLogout){
 		var logoutUrl = $webtop.getCustomContext().url.logout;
 		
 		$_wt.logout();
 		
 		
 		// Fail Safe - Redirect to cas logout after 2 sec 
 		(function(){
 			document.location = logoutUrl;
 		}).defer(2000);
 		
 		LogoutService.logout({
 			callback: function(){
 				document.location = logoutUrl;
 			},
 			errorHandler: function(){
				document.location = logoutUrl;
 			}
 		});
 	};
});