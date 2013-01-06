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
	'views/Taskboard',
	'views/Dashboard',
	'views/TaskAdd'
	
], function( $, _, Backbone, 
		IterationCollection, 
		IterationModel, 
		BacklogTaskCollection,
		TaskModel,
		IterationListView, 
		IterationDetailsView,
		TaskListView,
		TaskDetailsView,
		TaskboardView,
		DashboardView,
		TaskAddView) {
	
	var AppView = Backbone.View.extend({
		
	    initialize: function(options) {
	    		    	
	    	this.router = options.router;
	        
	        this.loadedTasks = {};
	        
	        self = this;
	    },
	    
	    dashboard: function () {
	    	$('#dashboard').show();
	    	$('#taskboard').hide();
	    	$('#projectView').hide();
	    	    	
	    	self.dashboardView = new DashboardView();
	    	$('#dashboard').html(self.dashboardView.render().el);	  
	    },
	    
	    listIterations: function(projectId) {
	    	
	    	var self = this;	    	
	    	if (!self.iterationList) {
	    		self.iterationList = new IterationCollection(null, {projectId: projectId});	    		
	    		self.iterationListView = new IterationListView({model: self.iterationList});		
	    		self.iterationListView.bind("tasksLoaded", function(tasks) {

		    		_.each(tasks.models, function (task) {
		    			self.loadedTasks['task_' + task.id] = tasks.get(task.id);
		    		});
	    		});
	    		
		        $('#iterations').html(self.iterationListView.render().el);
		        $('.addIterationLink').show();
		        $('.addIterationLink').attr('href', '/#project/' + projectId + '/iteration/add');   	    		
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
	    
	    addIteration: function(projectId) {

	    	this.listIterations(projectId);
	    	
	        if (self.iterationView) self.iterationView.close();

	        self.iterationView = new IterationDetailsView({model: new IterationModel({projectId: projectId}), collection: self.iterationList});
	        self.iterationView.bind("iterationCreated", self.iterationCreated, self);
	        self.iterationView.bind("cancel", function() {
	        	window.history.back();
	        }, self);
	        $('#addIteration').html(self.iterationView.render().el);	    	
	    },
	    
	    iterationCreated: function(id) {
	    	window.history.back();
	    	this.iterationView.close();
	    },
	    
	    
	    backlog: function(projectId) {
	    	
	    	$('#taskboard').hide();
	    	$('#projectView').show();
	    	$('#dashboard').hide();
	    	
	    	var self = this;
	    	if (!this.backlogTaskList) {
	    		this.backlogTaskList = new BacklogTaskCollection(null, {projectId: projectId});
	    		this.backlogTaskList.deferred.done(function(tasks) {
	    			self.backlogLoaded = true;
		    		_.each(tasks, function (task) {
		    			self.loadedTasks['task_' + task.id] = self.backlogTaskList.get(task.id);
		    		});
	    		});	
	    		
	    		var taskListView = new TaskListView({model: this.backlogTaskList});
		        $('#backlog').html(taskListView.render().el);
		        $('.addTaskLink').show();
		        $('.addTaskLink').attr('href', '/#project/' + projectId + '/task/add');	    		
	    	}
	    },

	    addTask: function(projectId) {
	    	
	    	this.backlog(projectId);
	    	
	    	if (self.taskAddView) self.taskAddView.close();

	    	self.taskAddView = new TaskAddView({ model: new TaskModel({projectId: projectId}), collection: self.backlogTaskList});
	        self.taskAddView.bind("taskCreated", self.taskCreated, self);
	        self.taskAddView.bind("cancel", function() {
	        	window.history.back();
	        }, self);
	        $('#addTask').html(self.taskAddView.render().el);	    	
	    },	    

	    taskCreated: function(id) {
	    	window.history.back();
	    	this.taskAddView.close();
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
	    	
	    	$('#taskboard').show();
	    	$('#projectView').hide();
	    	$('#dashboard').hide();
	    	
    		self.taskboardView = new TaskboardView({iterationId: iterationId});
	        $('#taskboard').html(self.taskboardView.render().el);	  
	    }	    
	});
	
	return AppView;
});