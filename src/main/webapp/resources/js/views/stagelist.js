define([
	'jquery',
	'underscore',
	'backbone',
	'views/stagelistitem',
	'text!templates/stageList.html',
	'jqueryui/sortable'
], function( $, _, Backbone, StageListItemView, stageListTemplate, sortable) {
	
	var StageListView = Backbone.View.extend({
		 
	    template: _.template(stageListTemplate),
	    className: 'list',
	 
	    initialize:function () {
	        this.model.bind("reset", this.render, this);
	        this.model.bind("add", function (stage) {
	        	$(this.el).find('ul').append(new StageListItemView({model: stage}).render().el);
	        }, this);
	    },
	    	 
	    render:function (eventName) {
	    	
	    	$(this.el).html(this.template());
	    	
	        _.each(this.model.models, function (stage) {	        	
	        	$(this.el).find('ul').append(new StageListItemView({ model: stage }).render().el);
	        }, this);
	        
	        var self = this;
	        $(this.el).find('ul').sortable({
	            
	        	update: function(event, ui) {
	        		var newOrder = [];
	        		$('.stage', this).each(function(i, el) {
	        			newOrder.push($(el).data('id'));
	        		});
        			self.model.updateOrder(newOrder);
	        	}
	        });	        
	        
	        return this;
	    }
	 
	});
	
	return StageListView;
});