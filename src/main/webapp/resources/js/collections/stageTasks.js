define([
	'underscore',
	'backbone',
	'models/task'
], function( _, Backbone, TaskModel ) {
	
	var StageTaskCollection = Backbone.Collection.extend({
	    model: TaskModel,
	    url: "/iteration/{iterationId}/stagetasks/{stageId}",
	    
	    initialize: function(models, options) {
	       this.url = this.url.replace('{iterationId}', options.iterationId);
	       this.url = this.url.replace('{stageId}', options.stageId);
	       this.deferred = this.fetch();
	       this.iterationId = options.iterationId;
	       this.stageId = options.stageId;
	    },
	    
	    updateOrder: function(order) {
			$.post('/iteration/' + this.iterationId + '/updateStage/' + this.stageId, 
					{
						idList: order.join(','),
					}, function(response) {
				console.log(response);
			}, 'json');
	    }		    
	});
	
	return StageTaskCollection;
});