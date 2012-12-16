define([
	'underscore',
	'backbone'
], function( _, Backbone ) {

	var TaskModel = Backbone.Model.extend({
		
		urlRoot: "/task",
		
	    defaults:{
	        "id": null,
	        "title": "",
	        "description": ""
	    },
	    
	    initialize: function(options) {
	    	this.defaults.projectId = options.projectId;
	    }
	});
	
	return TaskModel;
});