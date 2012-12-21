define([
	'jquery',
	'underscore',
	'backbone',
	'text!templates/dependencyListItem.html'
], function( $, _, Backbone, dependencyListItemTemplate ) {
	
	var DependencyListItemView = Backbone.View.extend({
		 
	    tagName: "li",
	 
	    template: _.template(dependencyListItemTemplate),

	    initialize:function () {
	    	this.model.bind("remove", this.close, this);
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
	    },
	    
	    events:{
	        "click .removeDependency": "removeDependency"
	    },		    
	 
	    removeDependency: function() {	    	
	    	this.trigger("removeDependency", this.model.id);
	    }
	});
	
	return DependencyListItemView;
});