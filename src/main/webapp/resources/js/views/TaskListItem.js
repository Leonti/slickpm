define([
	'jquery',
	'underscore',
	'backbone',
	'collections/Users',
	'views/UserSelector',
	'views/TaskPopup',
	'views/Points',
	'views/TaskTypes',
	'text!templates/taskListItem.html'
], function( $, _, Backbone, 
		UserCollection, 
		UserSelectorView, 
		TaskPopupView, 
		PointsView, 
		TaskTypesView, 
		taskListItemTemplate ) {
	
	var TaskListItemView = Backbone.View.extend({
		 
	    tagName: "div",
	 
	    template:_.template(taskListItemTemplate),

	    initialize:function () {
	        this.model.bind("change", this.render, this);
	        this.model.bind("destroy", this.close, this);
	    },	    
	    
	    render:function (eventName) {	
	    	
	        $(this.el).html(this.template(this.model.toJSON()));
	        
	        var pointsView = new PointsView({ model: this.model });
	        $(this.el).find('.points').html(pointsView.render().el);
	        
	        var taskTypesView = new TaskTypesView({ model: this.model });
	        $(this.el).find('.taskTypes').html(taskTypesView.render().el);
	        
	        return this;
	    },
	    
	    close:function () {
	        $(this.el).unbind();
	        $(this.el).remove();
	    },
	    
	    events:{
	        "click .avatar": "assignUser",
	        "click .title": "openTaskPopup"
	    },
	    
	    assignUser: function() {
	    	var userList = new UserCollection();
	    	var userSelectorView = new UserSelectorView({collection: userList});
	    	
	    	var self = this;
	    	userSelectorView.bind("userSelected", function(user) {
	    		
	    		self.model.set({
	    			user : user
	    		});
	    			    		
	    		self.model.save();	
	    	});
	    	
	    	return false;	    	
	    },
	    
	    openTaskPopup: function() {

	    	var taskPopupView = new TaskPopupView({model: this.model});
	    	taskPopupView.render();
	    	
	    	return false;
	    }
	 
	});
	
	return TaskListItemView;
});