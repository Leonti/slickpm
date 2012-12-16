define([
	'underscore',
	'backbone'
], function( _, Backbone ) {

	var StageModel = Backbone.Model.extend({
		
		urlRoot: "/taskstage",
		
	    defaults:{
	        "id": null,
	        "title": "",
	        "description": ""
	    }
	});
	
	return StageModel;
});