define([
	'jquery',
	'underscore',
	'backbone',
	'moment',
	'text!templates/fileListItem.html'
], function( $, _, Backbone, moment, fileListItemTemplate ) {
	
	var FileListItemView = Backbone.View.extend({
		 
	    tagName: "li",
	 
	    template: _.template(fileListItemTemplate),

	    initialize:function () {
	        this.model.bind("change", this.render, this);
	        this.model.bind("destroy", this.close, this);
	    },	    
	    
	    render: function (eventName) {
	    	
	        $(this.el).html(this.template(_.extend(this.model.toJSON(), {moment: moment})));
	        return this;
	    },
	    
	    close:function () {
	        $(this.el).unbind();
	        $(this.el).remove();
	    }
	 
	});
	
	return FileListItemView;
});