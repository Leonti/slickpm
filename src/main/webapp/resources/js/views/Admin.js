define([
	'jquery',
	'underscore',
	'backbone',
	'ErrorHandler',
	'collections/projects',
	'models/project',
	'collections/stages',
	'models/stage',
	'views/projectlist',
	'views/ProjectDetails',
	'views/stagelist',
	'views/StageDetails',
	
], function( $, _, Backbone, ErrorHandler, 
		ProjectCollection, 
		ProjectModel, 
		StageCollection,
		StageModel,
		ProjectListView, 
		ProjectDetailsView, 
		StageListView,
		StageDetailsView) {
	
	var AdminView = Backbone.View.extend({
		
	    initialize: function(options) {
	    	
	    	this.router = options.router;	        
	        self = this;
	    },

	    listProjects: function() {
	    	
	    	if (!self.projectList) {
	    		self.projectList = new ProjectCollection();	    		
	    		self.projectListView = new ProjectListView({model: self.projectList});
		        $('#admin .projects .listContainer').html(self.projectListView.render().el);	    		
	    	}	    	
	    },
	    
	    projectDetails: function(id) {

	    	this.listProjects();	    	
	    	this.projectList.deferred.done(function() {
	    		
		        self.project = self.projectList.get(id);
		        if (self.projectView) self.projectView.close();
		        self.projectView = new ProjectDetailsView({ model: self.project, projectList: self.projectList});
		        $('#admin .projects .details').html(self.projectView.render().el);	
	    	}, id);
	    },
	    
	    addProject: function() {

	    	this.listProjects();
	    	
	        if (self.projectView) self.projectView.close();

	        self.projectView = new ProjectDetailsView({model: new ProjectModel(), projectList: self.projectList});
	        self.projectView.bind("projectCreated", self.projectCreated);
	        $('#admin .projects .details').html(self.projectView.render().el);	    	
	    },
	    
	    projectCreated: function(id) {
	    	self.router.navigate('project/' + id, false);
	    },	    

	    listStages: function() {
	    	if (!self.stageList) {
	    		self.stageList = new StageCollection();	    		
	    		self.stageListView = new StageListView({model: self.stageList});
	    		$('#admin .stages .listContainer').html(self.stageListView.render().el);	    		
	    	}
	    },
	    
	    stageDetails: function(id) {

	    	this.listStages();	    	
	    	this.stageList.deferred.done(function() {
	    		
		        self.stage = self.stageList.get(id);
		        if (self.stageView) self.stageView.close();
		        self.stageView = new StageDetailsView({ model: self.stage, stageList: self.stageList});
		        $('#admin .stages .details').html(self.stageView.render().el);	
	    	}, id);
	    },
	    
	    addStage: function() {

	    	this.listStages();	    	
	    	if (self.stageView) self.stageView.close();

	        self.stageView = new StageDetailsView({model: new StageModel(), stageList: self.stageList});
	        self.stageView.bind("stageCreated", self.stageCreated);
	        $('#admin .stages .details').html(self.stageView.render().el);	    	
	    },
	    
	    stageCreated: function(id) {
	    	self.router.navigate('stage/' + id, false);
	    }	    
	    
	});
	
	return AdminView;
});