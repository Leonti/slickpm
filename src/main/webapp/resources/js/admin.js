require.config({
	shim: {
		'underscore': {
			exports: '_'
		},
		'backbone': {
			deps: [
				'underscore',
				'jquery'
			],
			exports: 'Backbone'
		}
	},
	paths: {
		jquery: 'lib/jquery/jquery.min',
		jqueryui: 'lib/jqueryui',
		underscore: 'lib/backbone/underscore',
		backbone: 'lib/backbone/backbone',
		text: 'lib/require/text',
		fileupload: 'lib/fileupload',
		bootstrap: 'lib/bootstrap',
		editable: 'lib/editable',
		moment: 'lib/moment',
		jqplot: 'lib/jqplot/jquery.jqplot',
		jqBootstrapValidation: 'lib/jqBootstrapValidation'
	},
	
	urlArgs: "bust=" +  (new Date()).getTime()
});

require([
	'routers/adminRouter'
	], function(Workspace) {

	new Workspace();
	Backbone.history.start();
});
