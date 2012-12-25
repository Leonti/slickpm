define([
	'jquery',
	'underscore',
	'backbone',
	'views/IterationListItem'
], function( $, _, Backbone, IterationListItemView ) {
	
	var IterationListView = Backbone.View.extend({
		 
	    tagName: 'div',
	 
	    initialize:function () {
	        this.model.bind("reset", this.render, this);
	    },
	 
	    render:function (eventName) {
	    	
	    	var list = $('<div>');
	        _.each(this.model.models, function (iteration) {
	        	
	        	var iterationListItemView = new IterationListItemView({ model: iteration });
	        	
	        	var self = this;
	        	iterationListItemView.bind("tasksLoaded", function(taskList) {
	        		self.trigger("tasksLoaded", taskList);	    	
	        	});
	        	
	            list.append(iterationListItemView.render().el);
	        }, this);
	        
	        $(this.el).empty();
	        $(this.el).append(list);
	        return this;
	    }
	 
	});
	
	return IterationListView;
});