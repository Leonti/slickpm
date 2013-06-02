define([
	'jquery',
	'underscore',
	'backbone',
	'views/TaskListItem',
	'jqueryui/sortable'
], function( $, _, Backbone, TaskListItemView ) {
	
	var TaskListView = Backbone.View.extend({
		 
	    tagName: 'div',
	    className: 'taskList',
	 
	    initialize:function () {
	    	
	    	$(this.el).append('<div class="loading">Loading ...</div>');
	    	
	        this.model.bind("reset", this.render, this);
	        this.model.bind("add", function (task) {
	            $(self.el).append(new TaskListItemView({model: task}).render().el);
	        });
	        
	        $(this.el).sortable({
	            connectWith: ".taskList",
	            dropOnEmpty: true,
	            handle: ".marker",
	            
	        	update: function(event, ui) {
	        		var newOrder = [];
	        		$('.task', this).each(function(i, el) {
	        			newOrder.push($(el).data('id'));
	        		});
        			self.model.updateOrder(newOrder);
	        	}
	        });
	    },
	    	 
	    render: function (eventName) {
	    	
	    	$(this.el).find('.loading').remove();
	    		    	
	        _.each(this.model.models, function (task) {
	            $(this.el).append(new TaskListItemView({ model: task }).render().el);
	        }, this);
	  	        
	        return this;
	    }
	 
	});
	
	return TaskListView;
});