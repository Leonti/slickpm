define([
	'jquery',
	'underscore',
	'backbone',
	'views/tasklistitem',
	'jqueryui/sortable'
], function( $, _, Backbone, TaskListItemView ) {
	
	var TaskListView = Backbone.View.extend({
		 
	    tagName: 'div',
	    className: 'taskList',
	 
	    initialize:function () {
	        this.model.bind("reset", this.render, this);
	        var self = this;
	        this.model.bind("add", function (task) {
	            $(self.el).append(new TaskListItemView({model: task}).render().el);
	        });
	        
	        $(this.el).sortable({
	            connectWith: ".taskList",
	            dropOnEmpty: true,
	            
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
	        _.each(this.model.models, function (task) {
	            $(this.el).append(new TaskListItemView({ model: task }).render().el);
	        }, this);
	        return this;
	    }
	 
	});
	
	return TaskListView;
});