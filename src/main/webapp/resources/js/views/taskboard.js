define([
	'jquery',
	'underscore',
	'backbone',
	'collections/stages',
	'collections/stageTasks',
	'views/tasklist',
	'text!templates/taskboard.html',
	'text!templates/taskboardStage.html'
], function( $, _, Backbone, 
		StageCollection, 
		StageTaskCollection, 
		TaskListView, 
		taskboardTemplate, 
		taskboardStageTemplate ) {
	
	var TaskboardView = Backbone.View.extend({
		 
	    tagName: 'div',
	    className: 'taskboard',
	    template: _.template(taskboardTemplate),
	    stageTemplate: _.template(taskboardStageTemplate),
	    
	    initialize: function(options) {
	        this.iterationId = options.iterationId;
	        
	        this.stageList = new StageCollection();
	        
	        var self =this;
	        this.stageList.deferred.done(function(stages) {
	        	_.each(stages, function(stage) {
	        		var stageTasks = new StageTaskCollection(null, {iterationId: options.iterationId, stageId: stage.id});
	        		
		    		var taskListView = new TaskListView({model: stageTasks});
		    		
		    		var stageHtml = self.stageTemplate({
		    			stageTitle: stage.title
		    		});
		    		
		    		stageHtml = $(stageHtml).append(taskListView.render().$el);		    		
			        $(self.el).find('.stages').append(stageHtml);
	        	});
	        });
	    },
	 
	    render: function (eventName) {

	        $(this.el).html(this.template({id: this.iterationId}));
	        return this;
	    }
	 
	});
	
	return TaskboardView;
});