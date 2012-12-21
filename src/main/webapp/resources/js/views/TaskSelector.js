define([
	'jquery',
	'jqueryui/dialog',
	'underscore',
	'backbone',
	'text!templates/taskSelector.html'
], function( $, dialog, _, Backbone, taskSelectorTemplate ) {
	
	var TaskSelectorView = Backbone.View.extend({
		
		template: _.template(taskSelectorTemplate),		
		
		initialize : function(){
			_.bindAll(this,"render", "triggerSelection");
			this.collection.bind("reset", this.render, this);
		},
		
		render: function() {
			var dialogString = this.template({tasks: this.collection.toJSON()});
			$(dialogString).dialog({
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
			
			this.delegateEvents(this.events);
			return this;
		},
		
		triggerSelection: function(task) {
			this.trigger("taskSelected", task);
		}
		
	});
	
	return TaskSelectorView;
});