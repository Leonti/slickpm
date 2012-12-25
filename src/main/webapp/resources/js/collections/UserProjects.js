define([
	'underscore',
	'backbone',
	'models/project'
], function( _, Backbone, ProjectModel ) {
	
	var UserProjectCollection = Backbone.Collection.extend({
	    model: ProjectModel,
	    url: "/project/user/{userId}",
	    
	    initialize: function(models, options) {
	    	this.url = this.url.replace('{userId}', options.userId);
	    	this.deferred = this.fetch();
	    }
	});
	
	return UserProjectCollection;
});