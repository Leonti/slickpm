define([
	'jquery',
	'underscore',
	'backbone',
	'text!templates/stageListItem.html'
], function( $, _, Backbone, stageListItemTemplate ) {
	
	var StageListItemView = Backbone.View.extend({
		 
	    tagName:"li",
	 
	    template:_.template(stageListItemTemplate),

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
	
	return StageListItemView;
});