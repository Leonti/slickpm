define([
	'jquery',
	'backbone',
	'views/app',	
], function( $, Backbone, AppView ) {

	var Workspace = Backbone.Router.extend({
		 
	    routes:{
	        "project/:projectId/iteration": "iterationList",
	        "project/:projectId/iteration/add": "addIteration",
	        "project/:projectId/iteration/:id": "iterationDetails",
	        "project/:projectId/backlog": "backlog",
	        "project/:projectId/task/add": "addTask",
	        "project/:projectId/task/:taskId": "taskDetails",
	        "taskboard/:id" : "taskboard"
	    },

	    initialize:function () {	        
	    	this.appView = new AppView({router: this});
	    },	    
	    
	    iterationList: function (projectId) {
	    	this.appView.listIterations(projectId);
	    },

	    addIteration: function(projectId) {
	    	this.appView.addIteration(projectId);
	    },	    
	    
	    iterationDetails: function (projectId, id) {
	    	this.appView.iterationDetails(projectId, id);
	    },
	    
	    backlog: function(projectId) {
	    	this.appView.listIterations(projectId);
	    	this.appView.backlog(projectId);
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