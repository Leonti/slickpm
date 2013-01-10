define([
	'jquery',
	'underscore',
	'backbone',
	'collections/projects',
	'collections/UserProjects',
	'views/UserProjectList',
	'views/UserDetails',
	'text!templates/dashboard.html'
], function( $, _, Backbone,
		ProjectCollection,
		UserProjectCollection,		
		UserProjectListView,
		UserDetailsView,
		dashboardTemplate ) {
	
	var DashboardView = Backbone.View.extend({
		 
	    tagName: 'div',
	    className: 'dashboard',
	    template: _.template(dashboardTemplate),
	    
	    initialize: function(options) {
	    },
	 
	    render: function (eventName) {   		    	
	        $(this.el).html(this.template());
	        	        
	        this.listUserProjects(window.user);
	        this.renderOverview(window.user);
	        
	        return this;
	    },
	    
	    listUserProjects: function(user) {
	    	
	    	if (!this.userProjectList) {
	    		
	    		this.userProjectList = new UserProjectCollection(null, {userId: user.id});
	    		this.projectList = new ProjectCollection();
	    		
    			new UserProjectListView(
    					{
    						collection: this.userProjectList, 
    						allProjects: this.projectList,
    						holder: '#userProjects'});    		
	    	}	    	
	    },
	    
	    renderOverview: function(user) {
	    	this.userDetailsView = new UserDetailsView({model: user});
	    	$(this.el).find('#userOverview').html(this.userDetailsView.render().el);
	    }
	 
	});
	
	return DashboardView;
});