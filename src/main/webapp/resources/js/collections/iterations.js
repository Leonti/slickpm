define([
	'underscore',
	'backbone',
	'models/iteration'
], function( _, Backbone, IterationModel ) {
	
	var IterationCollection = Backbone.Collection.extend({
	    model: IterationModel,
	    url: "/project/{projectId}/iteration",
	    
	    initialize:function(models, options) {
	       this.url = this.url.replace('{projectId}', options.projectId);
	       this.deferred = this.fetch();
	    }
	});
	
	return IterationCollection;
});