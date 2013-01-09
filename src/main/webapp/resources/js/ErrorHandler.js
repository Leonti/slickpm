define([
	'jquery'	
], function( $ ) {
	$(document).ajaxError( function(e, xhr, options) {
		if (xhr.status == 401) {
			window.location.href = '/auth/login';
		}
	});	
});