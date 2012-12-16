define([
	'underscore',
	'backbone',
	'models/user'
], function( _, Backbone, UserModel ) {
	
	var UserCollection = Backbone.Collection.extend({
	    model: UserModel,
	    url: "/user",
	    
	    initialize: function() {
	    	this.deferred = this.fetch();
	    }
	});
	
	return UserCollection;
});