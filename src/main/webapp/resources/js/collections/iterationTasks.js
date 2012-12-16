define([
	'underscore',
	'backbone',
	'models/task'
], function( _, Backbone, TaskModel ) {
	
	var IterationTaskCollection = Backbone.Collection.extend({
	    model: TaskModel,
	    url: "/iteration/{iterationId}/task",
	    
	    initialize: function(models, options) {
	       this.url = this.url.replace('{iterationId}', options.iterationId);
	       this.deferred = this.fetch();
	       this.iterationId = options.iterationId;
	    },
	    
	    updateOrder: function(order) {
			$.post('/project/updateIteration/' + this.iterationId, 
					{
						idList: order.join(','),
					}, function(response) {
				console.log(response);
			}, 'json');
	    }		    
	});
	
	return IterationTaskCollection;
});