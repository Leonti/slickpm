define([
	'jquery',
	'jqueryui/dialog',
	'underscore',
	'backbone',
	'filter',
	'text!templates/taskSelector.html'
], function( $, dialog, _, Backbone, filter, taskSelectorTemplate ) {
	
	var TaskSelectorView = Backbone.View.extend({
		
		template: _.template(taskSelectorTemplate),		
		
		initialize : function(){
			_.bindAll(this,"render", "triggerSelection");
			this.collection.bind("reset", this.render, this);
		},
		
		render: function() {
			$('#taskSelector').dialog("destroy").remove();
						
			var selectorDialog = $(this.template({tasks: this.collection.toJSON()}));			
			selectorDialog.attr('id', 'taskSelector');
			$('body').append(selectorDialog);
			
			selectorDialog.dialog({
				autoOpen: true,
				height: 460,
				width: 350,
				title: "Tasks",
				modal: true
			});
			
			this.el = $(".taskSelector");
			
			var self = this;
			$('.taskTitle', this.el).on('click', function() {
				self.el.dialog('close');
				self.triggerSelection(self.collection.get($(this).data('id')));
			});
			
			$('.listFilter .input', this.el).searchFilter({
				items: $('.item', this.el),
				matchItemClass: 'taskTitle'
			});
			
			this.delegateEvents(this.events);
			return this;
		},
		
		triggerSelection: function(task) {
			this.trigger("taskSelected", task);
		}
		
	});
	
	return TaskSelectorView;
});