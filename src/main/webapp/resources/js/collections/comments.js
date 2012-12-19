define([
	'underscore',
	'backbone',
	'models/comment'
], function( _, Backbone, CommentModel ) {
	
	var CommentCollection = Backbone.Collection.extend({
	    model: CommentModel,
	    url: "/task/{taskId}/comment",
	    
	    initialize:function(models, options) {
	       this.url = this.url.replace('{taskId}', options.taskId);
	       this.deferred = this.fetch();
	    }
	});
	
	return CommentCollection;
});