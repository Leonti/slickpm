define([
	'jquery',
	'underscore',
	'backbone',
	'collections/TaskTypes',
	'text!templates/taskTypes.html'
], function( $, _, Backbone, TaskTypesCollection, taskTypesTemplate ) {
	
	var TaskTypesView = Backbone.View.extend({
	 
	    template:_.template(taskTypesTemplate),

	    initialize:function () {
	        this.model.bind("destroy", this.close, this);
	        
	        var self = this;
	        $('html').on('click', function() {
	        	self.hideSelect();
	        });
	    },	    
	    
	    render:function (eventName) {
	        $(this.el).html(this.template(this.model.toJSON()));

	        if (window.taskTypesCache) {
	        	this.taskTypes = window.taskTypesCache;
	        } else {
	        	this.taskTypes = new TaskTypesCollection();	        	
	        	window.taskTypesCache = this.taskTypes;	        	
	        }

        	var self = this;
	        this.taskTypes.deferred.done(function() {
	        	self.fillSelect();	
	        });	        
	        
	        return this;
	    },

	    fillSelect: function() {
	    	var typeSelect = $(this.el).find('.typeSelect');
	    	
	    	_.each(this.taskTypes.models, function(taskType) {
	    		var option = $('<option>');
	    		option.val(taskType.id).text(taskType.get('title'));
	    		typeSelect.append(option);
	    	}, this);
	    },
	    
	    events:{
	        "click .currentType": "showSelect",
	        "change .typeSelect": "saveTaskType",
	        "click ": "viewClick" 
	    },
	    
	    showSelect: function () {
	    	$(this.el).find('.currentType').hide();
	    	
	    	var typeSelect = $(this.el).find('.typeSelect');
	    	typeSelect.show();
	    	var taskType = this.model.get('taskType');
	    	if (taskType != null) {
	    		typeSelect.val(taskType.id);
	    	}
	    },
	    
	    saveTaskType: function() {
	    	this.hideSelect();

	    	var taskType = this.taskTypes.get($(this.el).find('.typeSelect').val());
	    	if (!taskType) {
	    		taskType = null;
	    	}
	    	
	    	this.model.set({taskType: taskType});
	    	this.model.save();
	    },
	    
	    hideSelect: function() {
	    	$(this.el).find('.currentType').show();
	    	$(this.el).find('.typeSelect').hide();	    	
	    },
	    
	    viewClick: function(event) {
	    	event.stopPropagation();
	    },
	    
	    close: function () {
	        $(this.el).unbind();
	        $(this.el).remove();
	    }
	 
	});
	
	return TaskTypesView;
});