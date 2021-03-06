define([
	'jquery',
	'jqueryui/datepicker',
	'moment',
	'underscore',
	'backbone',
	'jqBootstrapValidation',
	'text!templates/iterationDetails.html'
], function( $, datepicker, moment, _, Backbone, jqBootstrapValidation, iterationDetailsTemplate ) {
	
	var IterationDetailsView = Backbone.View.extend({
		 
	    template:_.template(iterationDetailsTemplate),
	    
	    initialize: function (options) {
	    	
	        this.model.bind("change", this.render, this);	        
	    },
	 
	    render:function (eventName) {
	        
	    	$(this.el).html(this.template(_.extend(this.model.toJSON(), {moment: moment})));
	        
	        if (this.model.isNew()) {
	        	$(this.el).find('.delete').remove();
	        }
	        
	        $(this.el).find('.startDate').datepicker();
	        $(this.el).find('.endDate').datepicker();
	        
	        $(this.el).find('input').jqBootstrapValidation();
	        
	        return this;
	    },
	 
	    events:{
	        "click .save": "saveIteration",
	        "click .cancel": "cancel",
	        "click .delete": "deleteIteration"
	    },
	 
	    saveIteration:function () {
	    	
	    	$(this.el).find('input').triggerHandler("submit.validation");

	    	// don't proceed on validation error
	    	if ($(this.el).find('input[aria-invalid="true"]').size() > 0) {
	    		return false;
	    	} 
	    	
	        this.model.set({
	            title: $(this.el).find('.title').val(),
	            description: $(this.el).find('.description').val(),
	            startDate: $(this.el).find('.startDate').datepicker("getDate"),
	            endDate: $(this.el).find('.endDate').datepicker("getDate")
	        });
	        if (this.model.isNew()) {
	        	
	        	var self = this;
	        	this.model.save(null, {
	        		success: function() {
	        			self.trigger("iterationCreated", self.model.id);
	        			self.collection.add(self.model);
	        			self.collection.sort();
	        		}
	        	});
	        	
	        } else {
	        	this.trigger("iterationSave");
	            this.model.save();
	        }
	        return false;
	    },
	 
	    deleteIteration:function () {
	    	this.trigger("iterationDelete");
	        this.model.destroy();
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
	
	return IterationDetailsView;
});