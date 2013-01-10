define([
	'jquery',
	'backbone',
	'views/Admin'	
], function( $, Backbone, AdminView ) {

	var Workspace = Backbone.Router.extend({
		 
	    routes:{
	    	"": "projectList",
	        "projects": "projectList",
	        "project/add": "addProject",
	        "project/:id": "projectDetails",
	        "stages" : "taskStages",
	        "stage/add" : "addStage",
	        "stage/:id" : "stageDetails"
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
	    }
	});
	
	return Workspace;
});