define([
	'jquery',
	'underscore',
	'backbone',
	'collections/UserProjects',
	'views/UserProjectList',
	'text!templates/dashboard.html'
], function( $, _, Backbone,
		UserProjectCollection,		
		UserProjectListView,
		dashboardTemplate ) {
	
	var DashboardView = Backbone.View.extend({
		 
	    tagName: 'div',
	    className: 'dashboard',
	    template: _.template(dashboardTemplate),
	    
	    initialize: function(options) {
	    },
	 
	    render: function (eventName) {   	
	        $(this.el).html(this.template());
	        
	        this.listUserProjects();
	        return this;
	    },
	    
	    listUserProjects: function() {
	    	if (!this.projectList) {
	    		this.projectList = new UserProjectCollection(null, {userId: 4});
	    		
	    		var self = this;
	    		this.projectList.deferred.done(function() {
	    			var userProjectListView = new UserProjectListView({collection: self.projectList});
	    			$('#userProjects').html(userProjectListView.render().el);	
	    		});
	    	}	    	
	    }
	 
	});
	
	return DashboardView;
});