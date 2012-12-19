define([
	'jquery',
	'underscore',
	'backbone',
	'text!templates/commentListItem.html'
], function( $, _, Backbone, commentListItemTemplate ) {
	
	var CommentListItemView = Backbone.View.extend({
		 
	    tagName: "li",
	 
	    template: _.template(commentListItemTemplate),

	    initialize:function () {
	        this.model.bind("change", this.render, this);
	        this.model.bind("destroy", this.close, this);
	    },	    
	    
	    render: function (eventName) {
	    	
	        $(this.el).html(this.template(this.model.toJSON()));
	        return this;
	    },
	    
	    close:function () {
	        $(this.el).unbind();
	        $(this.el).remove();
	    }
	 
	});
	
	return CommentListItemView;
});