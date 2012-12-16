define(["jquery"], function(jQuery) {

	(function($){		
		
		$.fn.editable = function(options) {
		
			var opts = $.extend( {}, $.fn.editable.options, options );
			
			this.each(function() {
				var el = $(this);
				
				el.on('click', function() {
					

					var editor = $('<input type="text">');
					if (opts.type == 'textarea') {
						editor = $('<textarea></textarea>');
					}					

					editor.val(el.text());
					editor.on('blur', function() {
						$(this).hide()
						.prev().text($(this).val()).show();
						
						if (opts.onSave) {
							opts.onSave.call(this, {
								value: $(this).val()
							});
						}
					});
					
					el.hide().after(editor);
					editor.focus();
				});
				
				return this;
		    });
		};		
				
		$.fn.editable.options = {
			type: 'input'
		};

	})(jQuery);
});