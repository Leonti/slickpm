define([
	'jquery',
	'underscore',
	'backbone',
	'text!templates/dashboard.html'
], function( $, _, Backbone,  
		dashboardTemplate ) {
	
	var DashboardView = Backbone.View.extend({
		 
	    tagName: 'div',
	    className: 'dashboard',
	    template: _.template(dashboardTemplate),
	    
	    initialize: function(options) {
	    },
	 
	    render: function (eventName) {

	        $(this.el).html(this.template());
	        return this;
	    }
	 
	});
	
	return DashboardView;
});