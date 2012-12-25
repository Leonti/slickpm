define([
	'jquery',
	'jqueryui/dialog',
	'underscore',
	'backbone',
	'views/IterationDetails',
	'text!templates/iterationPopup.html'
], function( $, dialog, _, Backbone, IterationDetailsView, iterationPopupTemplate ) {
	
	var IterationPopupView = Backbone.View.extend({
		
		template: _.template(iterationPopupTemplate),	
		
		initialize : function(){

			this.model.bind("reset", this.render, this);
		},
		
		render: function() {
			$('#iterationPopup').dialog("destroy").remove();						
			var popupDialog = $(this.template());			
			popupDialog.attr('id', 'iterationPopup');
			$('body').append(popupDialog);
			
			popupDialog.dialog({
				autoOpen: true,
				height: 400,
				width: 350,
				title: "Iteration Details",
				modal: true
			});
			
			this.el = $(".iterationPopup");
			
	        this.iterationDetailsView = new IterationDetailsView({ model: this.model});
	        this.iterationDetailsView.bind("cancel iterationSave iterationDelete", function() {
	        	popupDialog.dialog("close");
	        }, this);
        
	        this.el.html(this.iterationDetailsView.render().el);	
	        
			return this;
		}
		
	});
	
	return IterationPopupView;
});