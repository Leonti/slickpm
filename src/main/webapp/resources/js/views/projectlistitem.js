define([
	'jquery',
	'underscore',
	'backbone',
	'text!templates/projectListItem.html'
], function( $, _, Backbone, projectListItemTemplate ) {
	
	var ProjectListItemView = Backbone.View.extend({
		 
	    tagName: "li",
	 
	    template:_.template(projectListItemTemplate),

	    initialize:function () {
	        this.model.bind("change", this.render, this);
	        this.model.bind("destroy", this.close, this);
	    },	    
	    
	    render:function (eventName) {
	        $(this.el).html(this.template(this.model.toJSON()));
	        return this;
	    },
	    
	    close:function () {
	        $(this.el).unbind();
	        $(this.el).remove();
	    }
	 
	});
	
	return ProjectListItemView;
});