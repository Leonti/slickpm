define([
	'underscore',
	'backbone',
	'models/task'
], function( _, Backbone, TaskModel ) {
	
	var DependencyCollection = Backbone.Collection.extend({
	    model: TaskModel,
	    url: "/task/{taskId}/dependency",
	    
	    initialize:function(models, options) {
	       this.url = this.url.replace('{taskId}', options.taskId);
	       this.deferred = this.fetch();
	    }
	});
	
	return DependencyCollection;
});