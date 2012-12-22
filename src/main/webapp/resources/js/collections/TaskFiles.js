define([
	'underscore',
	'backbone',
], function( _, Backbone, CommentModel ) {
	
	var TaskFileCollection = Backbone.Collection.extend({
	    url: "/task/{taskId}/file",
	    
	    initialize:function(models, options) {
	       this.url = this.url.replace('{taskId}', options.taskId);
	       this.deferred = this.fetch();
	    }
	});
	
	return TaskFileCollection;
});