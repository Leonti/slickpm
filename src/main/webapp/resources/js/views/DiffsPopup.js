define([
	'jquery',
	'jqueryui/dialog',
	'underscore',
	'backbone',
	'text!templates/diffsPopup.html',
	'text!templates/commit.html'
], function( $, dialog, _, Backbone, diffsPopupTemplate, commitTemplate) {
	
	var DiffsPopupView = Backbone.View.extend({
		
		template: _.template(diffsPopupTemplate),
		commitTemplate: _.template(commitTemplate),
		
		render: function() {
			$('#diffsPopup').dialog("destroy").remove();						
			var popupDialog = $(this.template());			
			popupDialog.attr('id', 'diffsPopup');
			$('body').append(popupDialog);
			
			popupDialog.dialog({
				autoOpen: true,
				height: 600,
				width: 1000,
				title: "Diffs for " + this.model.get('fullTitle'),
				modal: true
			});
			
			this.el = $(".diffsPopup");

			var self = this;
			$.get('/task/' + this.model.id + '/vcsDiff', function(commits) {

				if (commits.length == 0) {
					self.el.find('.message').text('No commits found for this task');
				} else {
					self.el.find('.message').remove();
				}
				
				for (var i = 0; i < commits.length; i++) {
					self.el.append(self.commitTemplate(commits[i]));
				}
						        
			}, 'json');				
		        
			return this;
		}
		
	});
	
	return DiffsPopupView;
});