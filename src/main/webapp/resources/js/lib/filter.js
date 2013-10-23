define(["jquery"], function(jQuery) {

	(function($){		
		
		$.fn.searchFilter = function(options) {
		
			var opts = $.extend( {}, $.fn.filter.options, options );			
			
			this.each(function() {
				var input = $(this);
				
				input.on('keyup', function() {

					opts.items.each(function(i, item) {
												
						if (hasMatches(opts.matchItemClass, $(item), input.val())) {
							$(item).show();
						} else {
							$(item).hide();
						}
					});
				});
				
				return this;
		    });				

		};		
		
		function hasMatches(textClass, el, filterText) {
			
			var textElement = $('.' + textClass, el);
			if (textElement.length == 0 && el.hasClass(textClass)) {
				textElement = el;
			} 
			
			if (textElement.text().indexOf(filterText) !== -1) {
				return true;
			}
			return false;
		}
				
		$.fn.filter.options = {};

	})(jQuery);
});