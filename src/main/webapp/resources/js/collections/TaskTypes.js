define([
	'underscore',
	'backbone',
	'models/TaskType'
], function( _, Backbone, TaskTypeModel ) {
	
	var TaskTypeCollection = Backbone.Collection.extend({
	    model: TaskTypeModel,
	    url: "/taskType",
	    
	    initialize:function(models, options) {
	       this.deferred = this.fetch();
	    }
	});
	
	return TaskTypeCollection;
});