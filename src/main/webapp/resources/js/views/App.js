define([
	'jquery',
	'underscore',
	'backbone',
	'ErrorHandler',
	'collections/iterations',
	'models/iteration',
	'collections/backlog',
	'models/task',
	'models/project',
	'models/iteration',
	'models/user',
	'views/IterationList',
	'views/IterationDetails',
	'views/TaskList',
	'views/TaskDetails',
	'views/Taskboard',
	'views/Dashboard',
	'views/TaskAdd',
	'views/ProjectHeader'
	
], function( $, _, Backbone, ErrorHandler, 
		IterationCollection, 
		IterationModel, 
		BacklogTaskCollection,
		TaskModel,
		ProjectModel,
		IterationModel,
		UserModel,
		IterationListView, 
		IterationDetailsView,
		TaskListView,
		TaskDetailsView,
		TaskboardView,
		DashboardView,
		TaskAddView,
		ProjectHeaderView) {
	
	var AppView = Backbone.View.extend({
		
	    initialize: function(options) {
	    		    	
	    	this.router = options.router;
	        
	        this.loadedTasks = {};
	        this.loadedProjects = {};
	        
	        window.user = new UserModel($('#user').data('user'));
	        
	        self = this;
	    },
	    
	    dashboard: function () {
	    	$('#dashboard').show();
	    	$('#taskboard').hide();
	    	$('#projectView').hide();
	    	    	
	    	this.dashboardView = new DashboardView();
	    	$('#dashboard').html(this.dashboardView.render().el);	  
	    },
	    
	    listIterations: function(projectId) {
	    	
	    	var self = this;	    	
	    	if (!self.iterationList || this.currentIterationsProjectId != projectId) {
	    		self.iterationList = new IterationCollection(null, {projectId: projectId});	    		
	    		self.iterationListView = new IterationListView({model: self.iterationList});		
	    		self.iterationListView.bind("tasksLoaded", function(tasks) {

		    		_.each(tasks.models, function (task) {
		    			self.loadedTasks['task_' + task.id] = tasks.get(task.id);
		    		});
	    		});
	    		
		        $('#iterations').html(self.iterationListView.el);
		        $('.addIterationLink').show();
		        $('.addIterationLink').attr('href', '/#project/' + projectId + '/iteration/add'); 
		        
		        var project = new ProjectModel({id: projectId});
		        project.fetch();
		        		        
		        var projectHeaderView = new ProjectHeaderView({model: project});
		        $('#projectHeader').html(projectHeaderView.render().el);
		        
		        this.currentIterationsProjectId = projectId;
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
	    		    	
	    	if (!this.backlogTaskList || this.currentBacklogProjectId != projectId) {
	    		this.backlogTaskList = new BacklogTaskCollection(null, {projectId: projectId});
	    		
	    		var self = this;
	    		this.backlogTaskList.deferred.done(function(tasks) {
		    		_.each(tasks, function (task) {
		    			self.loadedTasks['task_' + task.id] = self.backlogTaskList.get(task.id);
		    		});
	    		});	
	    		
	    		var taskListView = new TaskListView({model: this.backlogTaskList});
		        $('#backlog').html(taskListView.el);
		        $('.addTaskLink').show();
		        $('.addTaskLink').attr('href', '/#project/' + projectId + '/task/add');
		        this.currentBacklogProjectId = projectId;
	    	}
	    },

	    addTask: function(projectId) {
	    	
	    	this.backlog(projectId);
	    	
	    	if (self.taskAddView) self.taskAddView.close();

	    	var renderAddTask = function() {
		    	self.taskAddView = new TaskAddView({ 
		    		model: new TaskModel(), 
		    		collection: self.backlogTaskList, 
		    		project: self.loadedProjects[projectId]});
		    	
		        self.taskAddView.bind("taskCreated", self.taskCreated, self);
		        self.taskAddView.bind("cancel", function() {
		        	window.history.back();
		        }, self);
		        $('#addTask').html(self.taskAddView.render().el);	    		
	    	};
	    	
	    	if (!this.loadedProjects[projectId]) {
	    		this.loadedProjects[projectId] = new ProjectModel({id: projectId});
	    		this.loadedProjects[projectId].fetch({ success: renderAddTask});
	    	} else {
	    		renderAddTask();
	    	}
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
		    	task = new TaskModel({id: id});
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
	    	
	    	var self = this;
	    	var renderIteration = function(iteration) {
        		self.taskboardView = new TaskboardView({iteration: iteration});
    	        $('#taskboard').html(self.taskboardView.render().el);	    		
	    	};
	    	
	    	if (this.iterationList) {
	    		renderIteration(this.iterationList.get(iterationId));
	    	} else {
	    		var iteration = new IterationModel({id: iterationId});
	    		iteration.fetch({success: function() {
	    			renderIteration(iteration);	
	    		}});
	    	}	    	
	    }	    
	});
	
	return AppView;
});