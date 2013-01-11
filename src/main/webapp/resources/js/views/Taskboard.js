define([
	'jquery',
	'moment',
	'underscore',
	'backbone',
	'collections/stages',
	'collections/stageTasks',
	'views/TaskList',
	'text!templates/taskboard.html',
	'text!templates/taskboardStage.html'
], function( $, moment, _, Backbone, 
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
	        this.iteration = options.iteration;
	        
	        this.stageList = new StageCollection();
	        
	        var self =this;
	        this.stageList.deferred.done(function(stages) {
	        	_.each(stages, function(stage) {
	        		var stageTasks = new StageTaskCollection(null, {iterationId: options.iteration.id, stageId: stage.id});
	        		
		    		var taskListView = new TaskListView({model: stageTasks});
		    		
		    		var stageHtml = self.stageTemplate({
		    			stageTitle: stage.title
		    		});
		    		
		    		stageHtml = $(stageHtml).append(taskListView.el);		    		
			        $(self.el).find('.stages').append(stageHtml);
	        	});
	        });
	    },
	 
	    render: function (eventName) {

	        $(this.el).html(this.template({
	        	iteration: this.iteration.toJSON(), 
	        	moment: moment
	        }));
	        return this;
	    }
	 
	});
	
	return TaskboardView;
});