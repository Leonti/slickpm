define([
	'jquery',
	'underscore',
	'backbone',
	'collections/iterationTasks',
	'views/TaskList',
	'text!templates/iterationListItem.html'
], function( $, _, Backbone, IterationTaskCollection, TaskListView, iterationListItemTemplate ) {
	
	var IterationListItemView = Backbone.View.extend({
		 
	    tagName: "div",
	 
	    template:_.template(iterationListItemTemplate),

	    initialize:function () {
	        this.model.bind("change", this.render, this);
	        this.model.bind("destroy", this.close, this);
	    },	    
	    
	    render:function (eventName) {
	        $(this.el).html(this.template(this.model.toJSON()));
	        return this;
	    },
	    
	    events: {
	    	"click .tasks" : "showTasks"
	    },
	    
	    close: function() {
	        $(this.el).unbind();
	        $(this.el).remove();
	    },
	    
	    showTasks: function() {

	    	if (!this.taskList) {
	    		this.taskList = new IterationTaskCollection(null, {iterationId: this.model.attributes.id});	    		
	    		this.taskListView = new TaskListView({model: this.taskList});
	    		
	    		$(this.el).find('.taskList').html(this.taskListView.render().el);
	    		
	    		var self = this;
	    		this.taskList.deferred.done(function() {
		    		self.trigger("tasksLoaded", self.taskList);	    			
	    		});

	    	}	    	
	    }
	 
	});
	
	return IterationListItemView;
});