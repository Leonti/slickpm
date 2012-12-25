define([
	'underscore',
	'backbone',
	'models/iteration'
], function( _, Backbone, IterationModel ) {
	
	var IterationCollection = Backbone.Collection.extend({
	    model: IterationModel,
	    url: "/project/{projectId}/iteration",
	    
	    initialize: function(models, options) {
	       this.url = this.url.replace('{projectId}', options.projectId);
	       this.deferred = this.fetch();
	    },
	    
	    comparator: function(iteration1, iteration2) {
	    	if (iteration1.id > iteration2.id)
	    		return -1;
	    	
	    	return 1;
	    }
	});
	
	return IterationCollection;
});