define([
	'jquery',
	'underscore',
	'backbone',
	'text!templates/iterationDetails.html'
], function( $, _, Backbone, iterationDetailsTemplate ) {
	
	var IterationDetailsView = Backbone.View.extend({
		 
	    template:_.template(iterationDetailsTemplate),
	    
	    initialize: function (options) {
	    	this.iterationList = options.iterationList;
	    	
	        this.model.bind("change", this.render, this);	        
	    },
	 
	    render:function (eventName) {
	        $(this.el).html(this.template(this.model.toJSON()));
	        return this;
	    },
	 
	    events:{
	        "click .save": "saveIteration",
	        "click .delete": "deleteIteration"
	    },
	 
	    saveIteration:function () {
	        this.model.set({
	            title: $('#title').val(),
	            description: $('#description').val()
	        });
	        if (this.model.isNew()) {
	        	
	        	var self = this;
	            this.iterationList.create(this.model, {
	            	success: function() {	            		
	            		self.trigger("iterationCreated", self.model.attributes.projectId, self.model.id);
	            	}
	            });
	        } else {
	            this.model.save();
	        }
	        return false;
	    },
	 
	    deleteIteration:function () {
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
	
	return IterationDetailsView;
});