define([
	'underscore',
	'backbone',
	'moment'
], function( _, Backbone, moment ) {

	var CommentModel = Backbone.Model.extend({
	    urlRoot: "task/{taskId}/comment",
	    defaults:{
	        "id": null,
	        "content": "",
	        "date": moment().toDate()
	    },
	    
	    initialize: function(options) {
	    	this.urlRoot = this.urlRoot.replace('{taskId}', options.taskId);
	    }
	});
	
	return CommentModel;
});