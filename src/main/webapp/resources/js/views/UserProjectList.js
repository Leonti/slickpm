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
	    
	    initialize:function () {
	        this.collection.bind("reset", this.render, this);
	        this.collection.bind("add", function (project) {
	            $(this.el).find('ul').append(new UserProjectListItemView({model: project}).render().el);
	            
	        }, this);
	    },
	 
	    render:function (eventName) {
	    	
	    	$(this.el).html(this.template());
	    	
	        _.each(this.collection.models, function (project) {
	            $(this.el).find('ul').append(new UserProjectListItemView({ model: project }).render().el);
	        }, this);
	        	        
	        return this;
	    }	 
	});
	
	return UserProjectListView;
});