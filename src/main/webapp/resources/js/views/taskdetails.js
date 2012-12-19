define([
	'jquery',
	'underscore',
	'backbone',
	'collections/comments',
	'views/commentlist',
	'text!templates/taskDetails.html',
	'bootstrap'
], function( $, _, Backbone, CommentCollection, CommentListView, taskDetailsTemplate, bootstrap ) {
	
	var TaskDetailsView = Backbone.View.extend({
		 
	    template:_.template(taskDetailsTemplate),
	    
	    initialize: function (options) {
	    	this.taskList = options.taskList;
	    		    	
	        this.model.bind("change", this.render, this);	        
	    },
	    
	    listComments: function() {
	    	
	    	if (!this.commentList) {
	    		this.commentList = new CommentCollection(null, {taskId: this.model.attributes.id});
	    		
	    		var self = this;   		
	    		this.commentList.deferred.done(function() {	    			
	    			self.commentListView = new CommentListView({model: self.commentList});
		    		$('#comments').html(self.commentListView.render().el);	    			
	    		}, this);
	    	} 
	    },
	 
	    render: function (eventName) {
	        $(this.el).html(this.template(this.model.toJSON()));
	        this.listComments();
	        return this;
	    },
	 
	    events:{
	        "click .save": "saveTask",
	        "click .delete": "deleteTask"
	    },
	 
	    saveTask:function () {
	        this.model.set({
	            title: $('.title', $(this.el)).val(),
	            description: $('.description', $(this.el)).val()
	        });
	        if (this.model.isNew()) {
	        	
	        	var self = this;
	            this.taskList.create(this.model, {
	            	success: function() {	            		
	            		self.trigger("taskCreated", self.model.attributes.projectId, self.model.id);
	            	}
	            });
	        } else {
	            this.model.save();
	        }
	        return false;
	    },
	 
	    deleteTask: function () {
	        this.model.destroy({
	            success:function () {
	                window.history.back();
	            }
	        });
	        return false;
	    },
	 
	    close:function () {
	        $(this.el).unbind();
	        $(this.el).empty();
	    }
	 
	});
	
	return TaskDetailsView;
});