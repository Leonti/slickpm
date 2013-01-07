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
	        $.get('project/2/velocity', function(velocities) {
	        	
	        	var doneLine = [];
	        	var plannedLine = [];
	        	var days = 0;
	        	for (var i = 0; i < velocities.length; i++) {
	        		
	        		var velocity = velocities[i];
	        		days += velocity.days;
	        		
	        		
	        		
	        		doneLine.push([days, velocity.donePoints]);
	        		plannedLine.push([days, velocity.plannedPoints]);
	        		
	        		
	        		$(self.el).find('.velocityChart').jqplot([doneLine, plannedLine], {
	        			/*
	        			   series:[
	        			           {label:'good line'},
	        			           {label:'bad line'}
	        			       ],
	        			       */
	        			       legend: {
	        			    	   show: true,
	        			    	//   location: 'se',
	        			    	//   showSwatch: true,
	        			    	//   textColor: 'red',
	        			    	//   placement: 'outsideGrid',
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
	        
	     //   var line1 = [[1,1], [1.5,2], [3,3]]; 
	        
	        
	        
	        return this;
	    },
	    
	    close:function () {
	        $(this.el).unbind();
	        $(this.el).remove();
	    }
	 
	});
	
	return VelocityChartView;
});