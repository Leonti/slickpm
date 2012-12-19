define([
	'jquery',
	'backbone',
	'views/app',	
], function( $, Backbone, AppView ) {

	var Workspace = Backbone.Router.extend({
		 
	    routes:{
	    	
	        "project/:projectId": "project",
	        "project/:projectId/iteration/add": "addIteration",
	        "project/:projectId/iteration/:id": "iterationDetails",
	        "project/:projectId/task/add": "addTask",
	        "project/:projectId/task/:taskId": "taskDetails",
	        "taskboard/:id" : "taskboard"
	    },

	    project: function(projectId) {
	    	this.appView.listIterations(projectId);
	    	this.appView.backlog(projectId);
	    },	    
	    
	    initialize:function () {	        
	    	this.appView = new AppView({router: this});
	    },	    

	    addIteration: function(projectId) {
	    	this.appView.addIteration(projectId);
	    },	    
	    
	    iterationDetails: function (projectId, id) {
	    	this.appView.iterationDetails(projectId, id);
	    },

	    addTask: function(projectId) {
	    	this.appView.addTask(projectId);
	    },
	    
	    taskDetails: function(projectId, taskId) {
	    	this.appView.taskDetails(projectId, taskId);
	    },
	    
	    taskboard: function(iterationId) {
	    	this.appView.taskboard(iterationId);
	    }
	});
	
	return Workspace;
});