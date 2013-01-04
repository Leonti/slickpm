define([
	'underscore',
	'backbone'
], function( _, Backbone ) {

	var TaskModel = Backbone.Model.extend({
		
		urlRoot: "/task",
		
	    defaults:{
	        "id": null,
	        "title": "",
	        "description": ""
	    },
	    
	    initialize: function(options) {
	    	this.defaults.projectId = options.projectId;
	    	this.set({ fullTitle: '#' + this.id + ' - ' + this.get('title')});
	    },
	    
	    addDependency: function(dependencyId) {
	    	
	    	var self = this;
			$.post('/task/' + this.id + '/addDependency', 
					{
						dependencyId: dependencyId,
					}, function(response) {
				self.trigger("dependencyAdded");
			}, 'json');
	    },
	    
	    removeDependency: function(dependencyId) {
	    	
	    	var self = this;
			$.post('/task/' + this.id + '/removeDependency', 
					{
						dependencyId: dependencyId,
					}, function(response) {
				self.trigger("dependencyRemoved");
			}, 'json');
	    },
	});
	
	return TaskModel;
});