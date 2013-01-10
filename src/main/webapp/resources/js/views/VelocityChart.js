define([
	'jquery',
	'underscore',
	'backbone',
	'jqplot',
	'text!templates/velocityChart.html'
], function( $, _, Backbone, jqPlot, velocityChartTemplate ) {
	
	var VelocityChartView = Backbone.View.extend({
	 
	    template: _.template(velocityChartTemplate),

	    initialize:function (options) {
	    	this.holder = options.element;
	    },	    
	    
	    render: function (eventName) {
	    	
	        $(this.el).html(this.template());
	        
	        this.holder.html(this.el);
	        
	        var self = this;
	        $.get('project/' + this.model.id + '/velocity', function(velocities) {
	        	
	        	var doneLine = [];
	        	var plannedLine = [];
	        	var days = 0;
	        	for (var i = 0; i < velocities.length; i++) {
	        		
	        		var velocity = velocities[i];
	        		days += velocity.days;
	        			        		
	        		doneLine.push([days, velocity.donePoints]);
	        		plannedLine.push([days, velocity.plannedPoints]);
	        			        		
	        		$(self.el).find('.velocityChart').jqplot([doneLine, plannedLine], {
	        			       legend: {
	        			    	   show: true,
	        			    	   labels: ['Actual velocity', 'Planned velocity'],
	        			       },
	        			axes: {
	        	            xaxis: {
	        	                showTicks : false,
	        	                tickOptions: {
	        	                	showGridline: false
	        	                }
	        	            },
	        	            yaxis: {
	        	                showTicks : false,
	        	                tickOptions: {
	        	                	showGridline: false
	        	                }	        	                
	        	            }	        			
	        			},
	        			
	        			grid: {
	        				drawGridLines: false,
	        				borderWidth: 1,
	        				shadow: false
	        			}
	        			       
	        		});
	        	}
	        	
	        }, 'json');
	        	        
	        return this;
	    },
	    
	    close:function () {
	        $(this.el).unbind();
	        $(this.el).remove();
	    }
	 
	});
	
	return VelocityChartView;
});