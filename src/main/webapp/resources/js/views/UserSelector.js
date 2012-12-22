define([
	'jquery',
	'jqueryui/dialog',
	'underscore',
	'backbone',
	'text!templates/userSelector.html'
], function( $, dialog, _, Backbone, userSelectorTemplate ) {
	
	var UserSelectorView = Backbone.View.extend({
		
		template: _.template(userSelectorTemplate),		
		
		initialize : function(){
			_.bindAll(this,"render", "triggerSelection");
			this.collection.bind("reset", this.render, this);
		},
		
		render: function() {
			var dialogString = this.template({users: this.collection.toJSON()});
			$(dialogString).dialog({
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
			
			this.delegateEvents(this.events);
			return this;
		},
		
		triggerSelection: function(task) {
			this.trigger("userSelected", task);
		}
		
	});
	
	return UserSelectorView;
});