define([
	'underscore',
	'backbone'
], function( _, Backbone ) {

	var VcsModel = Backbone.Model.extend({
	    urlRoot:"vcs",
	    defaults: {
	        "id": null,
	        "uri": ""
	    }
	});
	
	return VcsModel;
});