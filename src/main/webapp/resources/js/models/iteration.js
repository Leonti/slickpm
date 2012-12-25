define([
	'underscore',
	'backbone',
	'moment'
], function( _, Backbone, moment ) {

	var IterationModel = Backbone.Model.extend({
	    urlRoot:"iteration",
	    defaults:{
	        id: null,
	        title: "",
	        description: "",
	        startDate: moment().toDate(),
	        endDate: moment().add('days', 14).toDate()	
	    },
	    
	    initialize: function(options) {
	    	this.defaults.projectId = options.projectId;
	    }
	});
	
	return IterationModel;
});