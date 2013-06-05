define([
	'jquery',
	'underscore',
	'backbone',
	'views/UserProjectListItem',
	'text!templates/userProjectList.html'
], function( $, _, Backbone, UserProjectListItemView, userProjectListTemplate ) {
	
	var UserProjectListView = Backbone.View.extend({
		
	    template: _.template(userProjectListTemplate),
	    className: 'userProjects',
	    
	    initialize:function (options) {
	    	
	    	this.allProjects = options.allProjects;
	    	this.allProjects.bind("reset", this.renderAllProjects, this);
	        this.collection.bind("reset", this.renderUserProjects, this);
        
	        this.holder = options.holder;
	        $(this.el).html(this.template());
	    },
	 
	    renderUserProjects: function (eventName) {

	        _.each(this.collection.models, function (project) {
	        		        	
	        	$(this.el).find('.userProjectList').append(new UserProjectListItemView({ model: project }).render().el);
	        	
	        }, this);	    	
	   	    
	        // click on "All projects" tab so we don't display empty tab to user
	        if (this.collection.models.length === 0) {
	        	$('a[href="#allProjectList"]').click();
	        }
	        
	        $(this.holder).html(this.el);
	    },
	    
	    renderAllProjects: function (eventName) {

	        _.each(this.allProjects.models, function (project) {
	        		        	
	        	$(this.el).find('.allProjectList').append(new UserProjectListItemView({ model: project }).render().el);
	        	
	        }, this);	    	
    
	        $(this.holder).html(this.el);
	    },	    
	});
	
	return UserProjectListView;
});