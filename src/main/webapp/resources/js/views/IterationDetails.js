define([
	'jquery',
	'jqueryui/datepicker',
	'moment',
	'underscore',
	'backbone',
	'text!templates/iterationDetails.html'
], function( $, datepicker, moment, _, Backbone, iterationDetailsTemplate ) {
	
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
	        
	        return this;
	    },
	 
	    events:{
	        "click .save": "saveIteration",
	        "click .cancel": "cancel",
	        "click .delete": "deleteIteration"
	    },
	 
	    saveIteration:function () {
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