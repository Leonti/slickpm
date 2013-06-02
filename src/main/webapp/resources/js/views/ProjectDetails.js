define([
	'jquery',
	'underscore',
	'backbone',
	'jqBootstrapValidation',
	'models/Vcs',
	'text!templates/projectDetails.html'
], function( $, _, Backbone, jqBootstrapValidation, VcsModel, projectDetailsTemplate ) {
	
	var ProjectDetailsView = Backbone.View.extend({
		 
	    template:_.template(projectDetailsTemplate),
	    
	    initialize: function (options) {
	    	this.projectList = options.projectList;
	    	
	        this.model.bind("change", this.render, this);	        
	    },
	 
	    render:function (eventName) {
	    	
	        $(this.el).html(this.template(this.model.toJSON()));
	        
	        $(this.el).find('input').jqBootstrapValidation();	        
	        
	        return this;
	    },
	 
	    events:{
	        "click .save": "saveProject",
	        "click .delete": "deleteProject"
	    },
	 
	    saveProject:function () {
	    	
	    	$(this.el).find('input').triggerHandler("submit.validation");
	    	
	    	// don't proceed on validation error
	    	if ($(this.el).find('input').jqBootstrapValidation("collectErrors").size() > 0) {
	    		return false;
	    	}
	    	
	    	var uri = $.trim($('.uri', $(this.el)).val());
	    	
	    	this.checkVcs(uri, function() {
	    		
		    	var vcs = new VcsModel(this.model.get('vcs'));	    	
		    	vcs.set({uri : uri});
		    	
		        this.model.set({
		            title: $('.title', $(this.el)).val(),
		            description: $('.description', $(this.el)).val(),
		            vcs: uri.length > 0 ? vcs : null
		        });
		        
		        var self = this;
		        if (this.model.isNew()) {
		        	        	
		            this.projectList.create(this.model, {
		            	success: function() {
		            		self.trigger("projectCreated", self.model.id);
		            	}
		            });
		        } else {	        	
		            this.model.save();
		        }	    		
	    		
	    	});
	    	
	        return false;
	    },
	    
	    checkVcs: function(uri, callback) {
	    	
	    	if (uri.length == 0) {
	    		callback.apply(this);
	    		return;
	    	} 
	    			    	
	    	var self = this;
			$.get('/project/checkVcs', 
					{
						uri: uri,
					}, function(response) {
				console.log(response);
				
				if (response.result == "OK") {
					callback.apply(self);
				} else {
					alert(response.message);
				} 
				
			}, 'json');	    	
	    },
	 
	    deleteProject: function () {
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