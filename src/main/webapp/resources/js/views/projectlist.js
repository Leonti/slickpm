define([
	'jquery',
	'underscore',
	'backbone',
	'views/projectlistitem',
	'text!templates/projectList.html'
], function( $, _, Backbone, ProjectListItemView, projectListTemplate ) {
	
	var ProjectListView = Backbone.View.extend({
		
	    template: _.template(projectListTemplate),
	    className: 'list',
	    
	    initialize:function () {
	        this.model.bind("reset", this.render, this);
	        this.model.bind("add", function (project) {
	            $(this.el).find('ul').append(new ProjectListItemView({model: project}).render().el);
	            
	        }, this);
	    },
	 
	    render:function (eventName) {
	    	
	    	$(this.el).html(this.template());
	    	
	        _.each(this.model.models, function (project) {
	            $(this.el).find('ul').append(new ProjectListItemView({ model: project }).render().el);
	        }, this);
	        	        
	        return this;
	    }
	 
	});
	
	return ProjectListView;
});