define([
	'jquery',
	'underscore',
	'backbone',
	'text!templates/taskAdd.html'
], function( $, _, Backbone, taskAddFormTemplate ) {
	
	var TaskAddView = Backbone.View.extend({
		 
	    template:_.template(taskAddFormTemplate),
	 
	    render:function (eventName) {
	    	
	        $(this.el).html(this.template(this.model.toJSON()));
	        
	        return this;
	    },
	 
	    events:{
	        "click .save": "saveTask",
	        "click .cancel": "cancel"
	    },
	 
	    saveTask:function () {
	        this.model.set({
	            title: $('.title', $(this.el)).val(),
	            description: $('.description', $(this.el)).val(),
	            user: null
	        });
	        	
        	var self = this;
            this.collection.create(this.model, {
            	success: function() {
            		self.trigger("taskCreated", self.model.id);
            	}
            });
	        return false;
	    },
	    
	    cancel: function() {
	    	this.close();
	    	this.trigger("cancel");
	    },
	 
	    close:function () {
	        $(this.el).unbind();
	        $(this.el).empty();
	    }
	 
	});
	
	return TaskAddView;
});