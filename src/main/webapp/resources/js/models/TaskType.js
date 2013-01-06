define([
	'underscore',
	'backbone'
], function( _, Backbone ) {

	var TaskTypeModel = Backbone.Model.extend({
		
		urlRoot: "/taskType",
		
	    defaults:{
	        "id": null,
	        "title": "",
	        "description": ""
	    }
	});
	
	return TaskTypeModel;
});