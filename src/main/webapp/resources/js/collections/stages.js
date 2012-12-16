define([
	'underscore',
	'backbone',
	'models/stage'
], function( _, Backbone, StageModel ) {
	
	var StageCollection = Backbone.Collection.extend({
	    model: StageModel,
	    url: "/taskstage",
	    
	    initialize: function(models, options) {
	       this.deferred = this.fetch();
	    },
	    
	    updateOrder: function(order) {
			$.post('/taskstage/updateOrder', 
					{
						idList: order.join(','),
					}, function(response) {
				console.log(response);
			}, 'json');
	    }		    
	});
	
	return StageCollection;
});