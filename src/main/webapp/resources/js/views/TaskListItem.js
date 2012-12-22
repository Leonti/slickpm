define([
	'jquery',
	'underscore',
	'backbone',
	'collections/Users',
	'views/UserSelector',
	'text!templates/taskListItem.html'
], function( $, _, Backbone, UserCollection, UserSelectorView, taskListItemTemplate ) {
	
	var TaskListItemView = Backbone.View.extend({
		 
	    tagName: "div",
	 
	    template:_.template(taskListItemTemplate),

	    initialize:function () {
	        this.model.bind("change", this.render, this);
	        this.model.bind("destroy", this.close, this);
	    },	    
	    
	    render:function (eventName) {	
	    	
	        $(this.el).html(this.template(this.model.toJSON()));
	        return this;
	    },
	    
	    close:function () {
	        $(this.el).unbind();
	        $(this.el).remove();
	    },
	    
	    events:{
	        "click .avatar": "assignUser"
	    },
	    
	    assignUser: function() {
	    	var userList = new UserCollection();
	    	var userSelectorView = new UserSelectorView({collection: userList});
	    	
	    	var self = this;
	    	userSelectorView.bind("userSelected", function(user) {
	    		
	    		self.model.set({
	    			userDTO : user
	    		});
	    			    		
	    		self.model.save();	
	    	});
	    	
	    	return false;	    	
	    }	    
	 
	});
	
	return TaskListItemView;
});