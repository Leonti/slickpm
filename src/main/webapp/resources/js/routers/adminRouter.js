define([
	'jquery',
	'backbone',
	'views/admin'	
], function( $, Backbone, AdminView ) {

	var Workspace = Backbone.Router.extend({
		 
	    routes:{
	        "projects": "projectList",
	        "project/add": "addProject",
	        "project/:id": "projectDetails",
	        "stages" : "taskStages",
	        "stage/add" : "addStage",
	        "stage/:id" : "stageDetails",
	        "users" : "userList",
	        "user/add" : "addUser",
	        "user/:id" : "userDetails"
	    },

	    initialize:function () {	        
	    	this.adminView = new AdminView({router: this});
	    },	    
	    
	    projectList: function () {
	    	
	    	this.adminView.listProjects();
	    },
	 
	    addProject: function() {
	    	this.adminView.addProject();
	    },
	    
	    projectDetails: function (id) {
	    	
	    	this.adminView.projectDetails(id);
	    },
	    
	    taskStages: function() {
	    	this.adminView.listStages();
	    },
	    
	    stageDetails: function(stageId) {
	    	this.adminView.stageDetails(stageId);
	    },

	    addStage: function() {
	    	this.adminView.addStage();
	    },
	    
	    taskboard: function(iterationId) {
	    	this.appView.taskboard(iterationId);
	    },
	    
	    userList: function() {
	    	this.adminView.listUsers();
	    },
	    
	    userDetails: function(userId) {
	    	this.adminView.userDetails(userId);
	    },
	    
	    addUser: function() {
	    	this.adminView.addUser();
	    }
	});
	
	return Workspace;
});