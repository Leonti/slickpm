define([
	'jquery',
	'underscore',
	'backbone',
	'text!templates/taskListItem.html'
], function( $, _, Backbone, taskListItemTemplate ) {
	
	var TaskListItemView = Backbone.View.extend({
		 
	    tagName: "div",
	 
	    template:_.template(taskListItemTemplate),

	    initialize:function () {
	        this.model.bind("change", this.render, this);
	        this.model.bind("destroy", this.close, this);
	    },	    
	    
	    render:function (eventName) {	
	    	
	        $(this.el).html(this.template(this.model.toJSON()));
	        return this;
	    },
	    
	    close:function () {
	        $(this.el).unbind();
	        $(this.el).remove();
	    }
	 
	});
	
	return TaskListItemView;
});