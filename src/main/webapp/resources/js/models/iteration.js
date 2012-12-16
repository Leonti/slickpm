define([
	'underscore',
	'backbone'
], function( _, Backbone ) {

	var IterationModel = Backbone.Model.extend({
	    urlRoot:"iteration",
	    defaults:{
	        "id": null,
	        "title": "",
	        "description": ""
	    },
	    
	    initialize: function(options) {
	    	this.defaults.projectId = options.projectId;
	    }
	});
	
	return IterationModel;
});