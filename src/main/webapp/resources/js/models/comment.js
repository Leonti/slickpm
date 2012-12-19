define([
	'underscore',
	'backbone'
], function( _, Backbone ) {

	var CommentModel = Backbone.Model.extend({
	    urlRoot: "task/{taskId}/comment",
	    defaults:{
	        "id": null,
	        "content": "",
	        "date": null
	    },
	    
	    initialize: function(options) {
	    	this.urlRoot = this.urlRoot.replace('{taskId}', options.taskId);
	    }
	});
	
	return CommentModel;
});