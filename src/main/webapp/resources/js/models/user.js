define([
	'underscore',
	'backbone'
], function( _, Backbone ) {

	var UserModel = Backbone.Model.extend({
	    urlRoot: "user",
	    defaults:{
	        "id": null,
	        "name": "",
	        "avatarId": null
	    }
	});
	
	return UserModel;
});