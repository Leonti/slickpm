define([
	'jquery',
	'jqueryui/dialog',
	'underscore',
	'backbone',
	'views/TaskDetails',
	'text!templates/taskPopup.html'
], function( $, dialog, _, Backbone, TaskDetailsView, taskPopupTemplate ) {
	
	var TaskPopupView = Backbone.View.extend({
		
		template: _.template(taskPopupTemplate),	
		
		initialize : function(){

			this.model.bind("reset", this.render, this);
		},
		
		render: function() {
			$('#taskPopup').dialog("destroy").remove();						
			var popupDialog = $(this.template());			
			popupDialog.attr('id', 'taskPopup');
			$('body').append(popupDialog);
			
			popupDialog.dialog({
				autoOpen: true,
				height: 460,
				width: 600,
				title: "Task Details",
				modal: true
			});
			
			this.el = $(".taskPopup");
			
	        this.taskDetailsView = new TaskDetailsView({ model: this.model});
	        this.el.html(this.taskDetailsView.render().el);	
	        
			return this;
		}
		
	});

	return TaskPopupView;
});