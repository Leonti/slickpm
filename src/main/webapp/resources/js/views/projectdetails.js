define([
	'jquery',
	'underscore',
	'backbone',
	'text!templates/projectDetails.html'
], function( $, _, Backbone, projectDetailsTemplate ) {
	
	var ProjectDetailsView = Backbone.View.extend({
		 
	    template:_.template(projectDetailsTemplate),
	    
	    initialize: function (options) {
	    	this.projectList = options.projectList;
	    	
	        this.model.bind("change", this.render, this);	        
	    },
	 
	    render:function (eventName) {
	        $(this.el).html(this.template(this.model.toJSON()));
	        return this;
	    },
	 
	    events:{
	        "click .save": "saveProject",
	        "click .delete": "deleteProject"
	    },
	 
	    saveProject:function () {
	        this.model.set({
	            title: $('.title', $(this.el)).val(),
	            description: $('.description', $(this.el)).val()
	        });
	        if (this.model.isNew()) {
	        	
	        	var self = this;
	            this.projectList.create(this.model, {
	            	success: function() {
	            		self.trigger("projectCreated", self.model.id);
	            	}
	            });
	        } else {
	            this.model.save();
	        }
	        return false;
	    },
	 
	    deleteProject:function () {
	    	var self = this;
	        this.model.destroy({
	            success:function () {
	                window.history.back();
	                self.close();
	            }
	        });
	        return false;
	    },
	 
	    close:function () {
	        $(this.el).unbind();
	        $(this.el).empty();
	    }
	 
	});
	
	return ProjectDetailsView;
});