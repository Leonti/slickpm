define([
	'underscore',
	'backbone',
	'models/project'
], function( _, Backbone, ProjectModel ) {
	
	var ProjectCollection = Backbone.Collection.extend({
	    model: ProjectModel,
	    url: "/project",
	    
	    initialize: function() {
	    	this.deferred = this.fetch();
	    }
	});
	
	return ProjectCollection;
});