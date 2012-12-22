define([
	'jquery',
	'underscore',
	'backbone',
	'views/iterationlistitem'
], function( $, _, Backbone, IterationListItemView ) {
	
	var IterationListView = Backbone.View.extend({
		 
	    tagName: 'div',
	 
	    initialize:function () {
	        this.model.bind("reset", this.render, this);
	        var self = this;
	        this.model.bind("add", function (iteration) {
	            $(self.el).append(new IterationListItemView({model: iteration}).render().el);
	        });
	    },
	 
	    render:function (eventName) {
	        _.each(this.model.models, function (iteration) {
	        	
	        	var iterationListItemView = new IterationListItemView({ model: iteration });
	        	
	        	var self = this;
	        	iterationListItemView.bind("tasksLoaded", function(taskList) {
	        		self.trigger("tasksLoaded", taskList);	    	
	        	});
	        	
	            $(this.el).append(iterationListItemView.render().el);
	        }, this);
	        return this;
	    }
	 
	});
	
	return IterationListView;
});