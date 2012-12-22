define([
	'jquery',
	'underscore',
	'backbone',
	'collections/iterations',
	'models/iteration',
	'collections/backlog',
	'models/task',
	'views/IterationList',
	'views/IterationDetails',
	'views/TaskList',
	'views/TaskDetails',
	'views/Taskboard'
	
], function( $, _, Backbone, 
		IterationCollection, 
		IterationModel, 
		BacklogTaskCollection,
		TaskModel,
		IterationListView, 
		IterationDetailsView,
		TaskListView,
		TaskDetailsView,
		TaskboardView) {
	
	var AppView = Backbone.View.extend({
		
	    initialize: function(options) {
	    		    	
	    	this.router = options.router;
	        
	        this.loadedTasks = {};
	        
	        self = this;
	    },
	    
	    listIterations: function(projectId) {
	    	
	    	if (!self.iterationList) {
	    		self.iterationList = new IterationCollection(null, {projectId: projectId});	 
	    		
	    		self.iterationListView = new IterationListView({model: self.iterationList});
	    		
	    		self.iterationListView.bind("tasksLoaded", function(tasks) {

		    		_.each(tasks.models, function (task) {
		    			self.loadedTasks['task_' + task.id] = tasks.get(task.id);
		    		});
	    		});
		        $('#iterations').html(self.iterationListView.render().el);		    		
	    	}	    	
	    },
	    
	    iterationDetails: function(projectId, id) {

	    	this.listIterations(projectId);
	    	
	    	self.iterationList.deferred.done(function() {    		
		        self.iteration = self.iterationList.get(id);
		        if (self.iterationView) self.iterationView.close();
		        self.iterationView = new IterationDetailsView({ model: self.iteration, iterationList: self.iterationList});
		        $('#content').html(self.iterationView.render().el);	
	    	}, id);
	    },
	    
	    addIterationCallback: function(projectId) {
	        self.router.navigate('project/' + projectId + '/iteration/add', true);
	    },
	    
	    addIteration: function(projectId) {

	    	this.listIterations(projectId);
	    	
	        if (self.iterationView) self.iterationView.close();

	        self.iterationView = new IterationDetailsView({model: new IterationModel({projectId: projectId}), iterationList: self.iterationList});
	        self.iterationView.bind("iterationCreated", self.iterationCreated);
	        $('#content').html(self.iterationView.render().el);	    	
	    },
	    
	    iterationCreated: function(projectId, id) {
	    	self.router.navigate('project/' + projectId + '/iteration/' + id, false);
	    },
	    
	    
	    backlog: function(projectId) {

	    	if (!self.backlogTaskList) {
	    		self.backlogTaskList = new BacklogTaskCollection(null, {projectId: projectId});
	    		self.backlogTaskList.deferred.done(function(tasks) {
		    		_.each(tasks, function (task) {
		    			self.loadedTasks['task_' + task.id] = self.backlogTaskList.get(task.id);
		    		});
	    		});
	    		
	    		var taskListView = new TaskListView({model: self.backlogTaskList});
		        $('#backlog').html(taskListView.render().el);
	    	}
	    },

	    addTask: function(projectId) {

	    	this.backlog(projectId);
	    	
	    	if (self.taskView) self.taskView.close();

	    	self.taskView = new TaskDetailsView({ model: new TaskModel({projectId: projectId}), taskList: self.backlogTaskList});
	        self.taskView.bind("taskCreated", self.taskCreated);
	        $('#content').html(self.taskView.render().el);	    	
	    },	    

	    taskCreated: function(projectId, id) {
	    	self.router.navigate('project/' + projectId + '/task/' + id, false);
	    },	    
	    
	    taskDetails: function(projectId, id) {

	    	if (self.taskView) self.taskView.close();
	    	var task = self.loadedTasks['task_' + id];
	    	if (task) {
	    		self.taskView = new TaskDetailsView({ model: task, taskList: self.backlogTaskList});
	    		$('#task').html(self.taskView.render().el);
	    	} else {
		    	task = new TaskModel({projectId: projectId, id: id});
		    	task.fetch({
		    		success: function() {
				        
				        self.taskView = new TaskDetailsView({ model: task});
				        $('#task').html(self.taskView.render().el);	    			
		    		}
		    	});	    		
	    	}		        
	    },
	    
	    taskboard: function(iterationId) {
	    	
    		self.taskboardView = new TaskboardView({iterationId: iterationId});
	        $('#taskboard').html(self.taskboardView.render().el);	  
	    }	    
	});
	
	return AppView;
});