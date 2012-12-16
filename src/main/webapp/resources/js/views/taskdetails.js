define([
	'jquery',
	'underscore',
	'backbone',
	'text!templates/taskDetails.html'
], function( $, _, Backbone, taskDetailsTemplate ) {
	
	var TaskDetailsView = Backbone.View.extend({
		 
	    template:_.template(taskDetailsTemplate),
	    
	    initialize: function (options) {
	    	this.taskList = options.taskList;
	    	
	        this.model.bind("change", this.render, this);	        
	    },
	 
	    render: function (eventName) {
	        $(this.el).html(this.template(this.model.toJSON()));
	        return this;
	    },
	 
	    events:{
	        "click .save": "saveTask",
	        "click .delete": "deleteTask"
	    },
	 
	    saveTask:function () {
	        this.model.set({
	            title: $('#title').val(),
	            description: $('#description').val()
	        });
	        if (this.model.isNew()) {
	        	
	        	var self = this;
	            this.taskList.create(this.model, {
	            	success: function() {	            		
	            		self.trigger("taskCreated", self.model.attributes.projectId, self.model.id);
	            	}
	            });
	        } else {
	            this.model.save();
	        }
	        return false;
	    },
	 
	    deleteTask: function () {
	        this.model.destroy({
	            success:function () {
	                window.history.back();
	            }
	        });
	        return false;
	    },
	 
	    close:function () {
	        $(this.el).unbind();
	        $(this.el).empty();
	    }
	 
	});
	
	return TaskDetailsView;
});