define([
	'jquery',
	'underscore',
	'backbone',	
	'jqBootstrapValidation',
	'models/comment',
	'views/CommentListItem',
	'text!templates/commentList.html'
], function( $, _, Backbone, jqBootstrapValidation, CommentModel, CommentListItemView, commentListTemplate ) {
	
	var CommentListView = Backbone.View.extend({
		 
		template: _.template(commentListTemplate),
		className: 'list',
	 
	    initialize: function(options) {
	        this.collection.bind("reset", this.render, this);
	        this.collection.bind("add", function (comment) {
	            $(this.el).find('ul').append(new CommentListItemView({model: comment}).render().el);
	        }, this);
	        
	        this.task = options.task;
	    },
	 
	    render:function (eventName) {
	    	
	    	$(this.el).html(this.template());
	    	
	        _.each(this.collection.models, function (comment) {
	        	
	        	var commentListItemView = new CommentListItemView({ model: comment });	        	
	            $(this.el).find('ul').append(commentListItemView.render().el);	            
	        }, this);
	        
	        $(this.el).find('textarea').jqBootstrapValidation();
	        
	        return this;
	    },
	    
	    events:{
	        "click .addComment": "addComment"
	    },	    
	 
	    addComment: function () {

	    	$(this.el).find('textarea').triggerHandler("submit.validation");
	    	for (var entry in $(this.el).find('textarea').jqBootstrapValidation("collectErrors")) {
	    		
	    		// don't proceed on validation error
	    		return false;
	    	}	    	
	    	
	    	var model = new CommentModel({taskId: this.task.attributes.id});
	    	
	    	model.set({
	            content: $('.content', $(this.el)).val()
	        });
	        
	    	$('.content', $(this.el)).val('');
	    	
        	var self = this;
            this.collection.create(model, {
            	success: function() {
            		self.trigger("commentCreated", model.id);
            	}
            });

	        return false;
	    }
	    
	});
	
	return CommentListView;
});