define([
	'jquery',
	'underscore',
	'backbone',
	'models/comment',
	'views/commentlistitem',
	'text!templates/commentList.html'
], function( $, _, Backbone, CommentModel, CommentListItemView, commentListTemplate ) {
	
	var CommentListView = Backbone.View.extend({
		 
		template: _.template(commentListTemplate),
		className: 'list',
	 
	    initialize:function () {
	        this.model.bind("reset", this.render, this);
	        this.model.bind("add", function (comment) {
	            $(this.el).find('ul').append(new CommentListItemView({model: comment}).render().el);
	        }, this);
	    },
	 
	    render:function (eventName) {
	    	
	    	$(this.el).html(this.template());
	    	
	        _.each(this.model.models, function (comment) {
	        	
	        	var commentListItemView = new CommentListItemView({ model: comment });	        	
	            $(this.el).find('ul').append(commentListItemView.render().el);	            
	        }, this);
	        return this;
	    },
	    
	    events:{
	        "click .addComment": "addComment"
	    },	    
	 
	    addComment: function () {
	        
	    	var model = new CommentModel({taskId: 3});
	    	
	    	model.set({
	            content: $('.content', $(this.el)).val()
	        });
	        
	    	$('.content', $(this.el)).val('');
	    	
        	var self = this;
            this.model.create(model, {
            	success: function() {
            		self.trigger("commentCreated", model.id);
            	}
            });

	        return false;
	    }
	    
	});
	
	return CommentListView;
});