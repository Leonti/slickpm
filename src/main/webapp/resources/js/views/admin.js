define([
	'jquery',
	'underscore',
	'backbone',
	'collections/projects',
	'models/project',
	'collections/stages',
	'models/stage',
	'collections/Users',
	'models/user',
	'views/projectlist',
	'views/projectdetails',
	'views/stagelist',
	'views/stagedetails',
	'views/userlist',
	'views/userdetails'
	
], function( $, _, Backbone, 
		ProjectCollection, 
		ProjectModel, 
		StageCollection,
		StageModel,
		UserCollection,
		UserModel,
		ProjectListView, 
		ProjectDetailsView, 
		StageListView,
		StageDetailsView,
		UserListView,
		UserDetailsView) {
	
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
	    
	    listUsers: function() {

	    	if (!self.userList) {

	    		self.userList = new UserCollection();	    		
	    		self.userListView = new UserListView({model: self.userList});
		        $('#admin .users .listContainer').html(self.userListView.render().el);	    		
	    	}	    	
	    },
	    
	    userDetails: function(id) {
	    	this.listUsers();	    	
	    	this.userList.deferred.done(function() {
	    		
		        self.user = self.userList.get(id);
		        if (self.userView) self.userView.close();
		        self.userView = new UserDetailsView({ model: self.user, userList: self.userList});
		        $('#admin .users .details').html(self.userView.render().el);	
	    	}, id);	    	
	    },
	    
	    addUser: function() {

	    	this.listUsers();
	    	
	    	if (self.userView) self.userView.close();

	    	self.userView = new UserDetailsView({ model: new UserModel(), userList: self.userList});
	        self.userView.bind("userCreated", self.userCreated);
	        $('#admin .users .details').html(self.userView.render().el);	    	
	    },
	    
	    userCreated: function(id) {
	    	self.router.navigate('user/' + id, false);
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