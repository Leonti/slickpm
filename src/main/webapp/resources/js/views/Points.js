define([
	'jquery',
	'underscore',
	'backbone',
	'text!templates/points.html'
], function( $, _, Backbone, pointsTemplate ) {
	
	var PointsView = Backbone.View.extend({
	 
	    template:_.template(pointsTemplate),

	    initialize:function () {
	        this.model.bind("destroy", this.close, this);
	        
	        var self = this;
	        $('html').on('click', function() {
	        	self.hideSelect();
	        });
	    },	    
	    
	    render:function (eventName) {
	        $(this.el).html(this.template(this.model.toJSON()));
	        return this;
	    },
	    
	    events:{
	        "click .staticPoints": "showSelect",
	        "change .pointsSelect": "savePoints",
	        "click ": "viewClick" 
	    },
	    
	    showSelect: function () {
	    	$(this.el).find('.staticPoints').hide();
	    	
	    	var pointsSelect = $(this.el).find('.pointsSelect');
	    	pointsSelect.show();
	    	pointsSelect.val(this.model.get('points'));
	    },
	    
	    savePoints: function() {
	    	this.hideSelect();
	    	
	    	var points = $(this.el).find('.pointsSelect').val();
	    	if (points == "null")
	    		points = null;
	    	
	    	this.model.set({points: points});
	    	this.model.save();
	    },
	    
	    hideSelect: function() {
	    	$(this.el).find('.staticPoints').show();
	    	$(this.el).find('.pointsSelect').hide();	    	
	    },
	    
	    viewClick: function(event) {
	    	event.stopPropagation();
	    },
	    
	    close: function () {
	        $(this.el).unbind();
	        $(this.el).remove();
	    }
	 
	});
	
	return PointsView;
});