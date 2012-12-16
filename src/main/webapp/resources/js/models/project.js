define([
	'underscore',
	'backbone'
], function( _, Backbone ) {

	var ProjectModel = Backbone.Model.extend({
	    urlRoot:"project",
	    defaults:{
	        "id": null,
	        "title": "",
	        "description": ""
	    }
	});
	
	return ProjectModel;
});