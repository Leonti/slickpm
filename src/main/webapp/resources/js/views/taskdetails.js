define([
	'jquery',
	'underscore',
	'backbone',
	'collections/comments',
	'collections/taskFiles',
	'views/commentlist',
	'views/taskfilelist',
	'text!templates/taskDetails.html',
	'bootstrap'
], function( $, _, Backbone, 
		CommentCollection, 
		TaskFileCollection, 
		CommentListView, 
		TaskFileListView, 
		taskDetailsTemplate, 
		bootstrap ) {
	
	var TaskDetailsView = Backbone.View.extend({
		 
	    template:_.template(taskDetailsTemplate),
	    
	    initialize: function (options) {
	    	this.taskList = options.taskList;
	    		    	
	        this.model.bind("change", this.render, this);	        
	    },
	    
	    listFiles: function() {
	    	if (!this.fileList) {
	    		this.fileList = new TaskFileCollection(null, {taskId: this.model.attributes.id});
	    		
	    		var self = this;
	    		this.fileList.deferred.done(function() {
	    			var taskFileListView = new TaskFileListView({task: self.model, model: self.fileList});
	    			$('#attachments').html(taskFileListView.render().el);	
	    		});
	    	}
	    },
	    
	    listComments: function() {
	    	
	    	if (!this.commentList) {
	    		this.commentList = new CommentCollection(null, {taskId: this.model.attributes.id});
	    		
	    		var self = this;   		
	    		this.commentList.deferred.done(function() {	    			
	    			var commentListView = new CommentListView({task: self.model, model: self.commentList});
		    		$('#comments').html(commentListView.render().el);	    			
	    		});
	    	} 
	    },
	 
	    render: function (eventName) {
	        $(this.el).html(this.template(this.model.toJSON()));
	        this.listComments();
	        this.listFiles();
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