define([
	'jquery',
	'underscore',
	'backbone',
	'jqBootstrapValidation',
	'text!templates/taskAdd.html'
], function( $, _, Backbone, jqBootstrapValidation, taskAddFormTemplate ) {
	
	var TaskAddView = Backbone.View.extend({
		 
	    template:_.template(taskAddFormTemplate),
	 
	    initialize: function(options) {
	    	
	    	this.project = options.project;
	    },
	    
	    render: function (eventName) {
	    	
	        $(this.el).html(this.template(this.model.toJSON()));
	    	
	        $(this.el).find('input').jqBootstrapValidation();
	        
	        return this;
	    },
	 
	    events:{
	        "click .save": "saveTask",
	        "click .cancel": "cancel"
	    },
	 
	    saveTask:function () {
	    	
	    	$(this.el).find('input').triggerHandler("submit.validation");
	    	for (var entry in $(this.el).find('input').jqBootstrapValidation("collectErrors")) {
	    		
	    		// don't proceed on validation error
	    		return false;
	    	}	    	
	    	
	        this.model.set({
	            title: $('.title', $(this.el)).val(),
	            description: $('.description', $(this.el)).val(),
	            project: this.project,
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