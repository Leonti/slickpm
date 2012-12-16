define([
	'jquery',
	'underscore',
	'backbone',
	'views/userlistitem',
	'text!templates/userList.html'	
], function( $, _, Backbone, UserListItemView, userListTemplate ) {
	
	var UserListView = Backbone.View.extend({
		 
	    template: _.template(userListTemplate),
	    className: 'list',
	 
	    initialize:function () {
	        this.model.bind("reset", this.render, this);
	        this.model.bind("add", function (user) {
	        	$(this.el).find('ul').append(new UserListItemView({model: user}).render().el);
	        }, this);
	    },
	 
	    render:function (eventName) {
	    	
	    	$(this.el).html(this.template());
	    	
	        _.each(this.model.models, function (user) {
	        	$(this.el).find('ul').append(new UserListItemView({ model: user }).render().el);
	        }, this);
	        return this;
	    }
	 
	});
	
	return UserListView;
});