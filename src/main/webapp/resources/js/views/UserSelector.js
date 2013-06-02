define([
	'jquery',
	'jqueryui/dialog',
	'underscore',
	'backbone',
	'filter',
	'text!templates/userSelector.html'
], function( $, dialog, _, Backbone, filter, userSelectorTemplate ) {
	
	var UserSelectorView = Backbone.View.extend({
		
		template: _.template(userSelectorTemplate),		
		
		initialize : function(){
			_.bindAll(this,"render", "triggerSelection");
			this.collection.bind("reset", this.render, this);
		},
		
		render: function() {
			$('#userSelector').dialog("destroy").remove();
						
			var selectorDialog = $(this.template({users: this.collection.toJSON()}));			
			selectorDialog.attr('id', 'userSelector');
			selectorDialog.dialog({
				autoOpen: true,
				height: 460,
				width: 350,
				title: "Users",
				modal: true
			});
			
			this.el = $(".userSelector");
			
			var self = this;
			$('.userName', this.el).on('click', function() {
				self.el.dialog('close');
				self.triggerSelection(self.collection.get($(this).data('id')));
			});

			$('.listFilter .input', this.el).searchFilter({
				items: $('.item', this.el),
				matchItemClass: 'userName'
			});			
			
			this.delegateEvents(this.events);
			return this;
		},
		
		triggerSelection: function(task) {
			this.trigger("userSelected", task);
		}
		
	});
	
	return UserSelectorView;
});