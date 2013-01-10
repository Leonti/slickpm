define([
	'jquery',
	'underscore',
	'backbone',
	'editable',
	'collections/Comments',
	'collections/TaskFiles',
	'collections/Dependencies',
	'collections/Users',
	'views/CommentList',
	'views/TaskFileList',
	'views/DependencyList',
	'views/UserSelector',
	'views/DiffsPopup',
	'text!templates/taskDetails.html',
	'text!templates/userItem.html',
	'bootstrap'
], function( $, _, Backbone, editable,
		CommentCollection, 
		TaskFileCollection, 
		DependencyCollection,
		UserCollection,
		CommentListView, 
		TaskFileListView,
		DependencyListView,
		UserSelectorView,
		DiffsPopupView,
		taskDetailsTemplate,
		userItemTemplate,
		bootstrap ) {
	
	var TaskDetailsView = Backbone.View.extend({
		 
	    template: _.template(taskDetailsTemplate),
	    userTemplate: _.template(userItemTemplate),
	    
	    initialize: function (options) {
	    	this.taskList = options.taskList;
	    		    	
	        this.model.bind("change", this.render, this);	        
	    },
	    
	    listFiles: function() {
	    	if (!this.fileList) {
	    		this.fileList = new TaskFileCollection(null, {taskId: this.model.attributes.id});
	    		
	    		var self = this;
	    		this.fileList.deferred.done(function() {
	    			var taskFileListView = new TaskFileListView({task: self.model, collection: self.fileList});
	    			$('#attachments').html(taskFileListView.render().el);	
	    		});
	    	}
	    },
	    
	    listComments: function() {
	    	
	    	if (!this.commentList) {
	    		this.commentList = new CommentCollection(null, {taskId: this.model.attributes.id});
	    		
	    		var self = this;   		
	    		this.commentList.deferred.done(function() {	    			
	    			var commentListView = new CommentListView({task: self.model, collection: self.commentList});
		    		$('#comments').html(commentListView.render().el);	    			
	    		});
	    	} 
	    },
	    
	    listDependencies: function() {
	    	if (!this.dependencyList) {
	    		this.dependencyList = new DependencyCollection(null, {taskId: this.model.attributes.id});
	    		
	    		var self = this;   		
	    		this.dependencyList.deferred.done(function() {	    			
	    			var dependencyListView = new DependencyListView({task: self.model, collection: self.dependencyList});
		    		$('#dependencies').html(dependencyListView.render().el);	    			
	    		});
	    	} 	    	
	    },
	 
	    render: function (eventName) {
	    	
	        $(this.el).html(this.template(this.model.toJSON()));

	        
	        if (this.model.get('user')) {	        	
		    	$(this.el).find('.assignedUser').html(this.userTemplate(this.model.toJSON().user));	        	
	        }
	        
	        var self = this;
	        $(this.el).find('.title.editable').editable({
	        	onSave: function() { self.saveTask(); }
	        });
	        $(this.el).find('.description').editable({
	        	type: 'textarea',
	        	onSave: function() { self.saveTask(); }
	        });
	        
	        this.listComments();
	        this.listFiles();
	        this.listDependencies();
	        return this;
	    },
	 
	    events:{
	        "click .assignedUser": "assignUser",
	        "click .vcs": "showCommits"
	    },
	 
	    saveTask: function () {
	        this.model.set({
	            title: $('.title.editable', $(this.el)).text(),
	            description: $('.description', $(this.el)).text()
	        });

	        this.model.save();
	        
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
	    
	    assignUser: function() {
	    	var userList = new UserCollection();
	    	var userSelectorView = new UserSelectorView({collection: userList});
	    	
	    	var self = this;
	    	userSelectorView.bind("userSelected", function(user) {
	    		
	    		self.model.set({
	    			user : user
	    		});
	    		self.model.save();
	    		
	    		$(self.el).find('.assignedUser').html(self.userTemplate(user.toJSON()));	
	    	});
	    	
	    	return false;	    	
	    },
	    
	    showCommits: function() {
	    	var diffsPopupView = new DiffsPopupView({model: this.model});
	    	diffsPopupView.render();
	    	
	    	return false;	    	
	    },
	 
	    close:function () {
	        $(this.el).unbind();
	        $(this.el).empty();
	    }
	 
	});
	
	return TaskDetailsView;
});