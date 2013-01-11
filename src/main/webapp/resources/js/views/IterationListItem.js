define([
	'jquery',
	'moment',
	'underscore',
	'backbone',
	'collections/iterationTasks',
	'views/TaskList',
	'views/IterationPopup',
	'text!templates/iterationListItem.html'
], function( $, moment, _, Backbone, 
		IterationTaskCollection, 
		TaskListView, 
		IterationPopupView, 
		iterationListItemTemplate ) {
	
	var IterationListItemView = Backbone.View.extend({
		 
	    tagName: "div",
	 
	    template:_.template(iterationListItemTemplate),

	    initialize:function () {
	        this.model.bind("change", this.render, this);
	        this.model.bind("destroy", this.close, this);
	    },	    
	    
	    render:function (eventName) {
	    	$(this.el).html(this.template(_.extend(this.model.toJSON(), {moment: moment})));
	        
	        return this;
	    },
	    
	    events: {
	    	"click .tasks" : "showTasks",
	    	"click .details": "openIterationPopup"
	    },
	    
	    close: function() {
	        $(this.el).unbind();
	        $(this.el).remove();
	    },
	    
	    showTasks: function() {

	    	if (!this.taskList) {
	    		this.taskList = new IterationTaskCollection(null, {iterationId: this.model.attributes.id});
	    	}
	    			    	
	    	if (!this.taskListView) {
	    		this.taskListView = new TaskListView({model: this.taskList});
	    		
	    		$(this.el).find('.taskListHolder').html(this.taskListView.el);
	    		
	    		var self = this;
	    		this.taskList.deferred.done(function() {
		    		self.trigger("tasksLoaded", self.taskList);	    			
	    		});

	    	} else {
	    		$(this.el).find('.taskListHolder').toggle();
	    	} 	    	
	    },
	    
	    openIterationPopup: function(event) {

	    	var iterationPopupView = new IterationPopupView({model: this.model});
	    	iterationPopupView.render();
	    	
	    	return false;
	    }
	 
	});
	
	return IterationListItemView;
});