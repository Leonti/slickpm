define([
	'jquery',
	'underscore',
	'backbone',
	'models/Vcs',
	'views/VelocityChart',	
	'text!templates/projectHeader.html'
], function( $, _, Backbone, VcsModel, VelocityChartView, projectHeaderTemplate ) {
	
	var ProjectDetailsView = Backbone.View.extend({
		 
	    template:_.template(projectHeaderTemplate),
	    
	    initialize: function (options) {
	    	
	        this.model.bind("change", this.render, this);	        
	    },
	 
	    render:function (eventName) {
	    	
	        $(this.el).html(this.template(this.model.toJSON()));

	        var velocityChartView = new VelocityChartView({model: this.model, element: $(this.el).find('.projectVelocity')});
	        velocityChartView.render();
	        
	        return this;
	    },	 
	    close:function () {
	        $(this.el).unbind();
	        $(this.el).empty();
	    }
	 
	});
	
	return ProjectDetailsView;
});