define([
	'underscore',
	'backbone',
	'models/task'
], function( _, Backbone, TaskModel ) {
	
	var TaskCollection = Backbone.Collection.extend({
	    model: TaskModel,
	    url: "/project/{projectId}/task",
	    
	    initialize:function(models, options) {
	       this.url = this.url.replace('{projectId}', options.projectId);
	    },
	    
	    updateOrder: function(order) {
	    	console.log('updating order for the list');
	    	console.log(order);
	    }
	});
	
	return TaskCollection;
});