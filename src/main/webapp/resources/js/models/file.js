define([
	'underscore',
	'backbone'
], function( _, Backbone ) {

	var FileModel = Backbone.Model.extend({
	    defaults:{
	        "id": null,
	        "filename": "",
	        "contentType": "",
	        "size": "",
	        "uploadDate": null
	    },
	    
	    addToTask: function(taskId) {
			$.post('/task/' + taskId + '/addFile', 
					{
						id: this.id,
					}, function(response) {
				console.log(response);
			}, 'json');
	    }	    
	});
	
	return FileModel;
});