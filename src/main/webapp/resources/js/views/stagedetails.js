define([
	'jquery',
	'underscore',
	'backbone',
	'text!templates/stageDetails.html'
], function( $, _, Backbone, stageDetailsTemplate ) {
	
	var StageDetailsView = Backbone.View.extend({
		 
	    template:_.template(stageDetailsTemplate),
	    
	    initialize: function (options) {
	    	this.stageList = options.stageList;
	    	
	        this.model.bind("change", this.render, this);	        
	    },
	 
	    render:function (eventName) {
	        $(this.el).html(this.template(this.model.toJSON()));
	        return this;
	    },
	 
	    events:{
	        "click .save": "saveStage",
	        "click .delete": "deleteStage"
	    },
	 
	    saveStage:function () {
	        this.model.set({
	            title: $('.title', $(this.el)).val(),
	            description: $('.description', $(this.el)).val()
	        });
	        if (this.model.isNew()) {
	        	
	        	var self = this;
	            this.stageList.create(this.model, {
	            	success: function() {
	            		self.trigger("stageCreated", self.model.id);
	            	}
	            });
	        } else {
	            this.model.save();
	        }
	        return false;
	    },
	 
	    deleteStage:function () {
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
	
	return StageDetailsView;
});