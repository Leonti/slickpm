define([
	'underscore',
	'backbone',
	'models/task'
], function( _, Backbone, TaskModel ) {
	
	var BacklogTaskCollection = Backbone.Collection.extend({
	    model: TaskModel,
	    url: "/project/{projectId}/backlog",
	    
	    initialize: function(models, options) {
	    	
	    	this.projectId = options.projectId;
	    	this.url = this.url.replace('{projectId}', options.projectId);
	    	this.deferred = this.fetch();
	    },
	    
	    updateOrder: function(order) {
			$.post('/project/' + this.projectId + '/updateBacklog', 
					{
						idList: order.join(','),
					}, function(response) {
				console.log(response);
			}, 'json');	    	
	    }	    
	});
	
	return BacklogTaskCollection;
});