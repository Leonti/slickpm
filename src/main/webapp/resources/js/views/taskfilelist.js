define([
	'jquery',
	'underscore',
	'backbone',
	'models/file',
	'views/filelistitem',
	'text!templates/taskFileList.html',
	'fileupload/jquery.iframe-transport', 
	'fileupload/jquery.fileupload'
], function( $, _, Backbone, FileModel, FileListItemView, taskFileListTemplate ) {
	
	var TaskFileListView = Backbone.View.extend({
		 
		template: _.template(taskFileListTemplate),
		className: 'list',
	 
	    initialize: function(options) {
	        this.collection.bind("reset", this.render, this);
	        this.collection.bind("add", function (file) {
	            $(this.el).find('ul').append(new FileListItemView({model: file}).render().el);
	        }, this);
	        
	        this.task = options.task;
	    },
	 
	    render:function (eventName) {
	    	
	    	$(this.el).html(this.template());
	    	
	        _.each(this.collection.models, function (file) {
	        	
	        	var commentListItemView = new FileListItemView({ model: file });	        	
	            $(this.el).find('ul').append(commentListItemView.render().el);	            
	        }, this);
	        
	        var self = this;
	        $('#taskFileUpload', $(this.el)).fileupload()
			.bind('fileuploaddone', function (e, data) {    			
				console.log(data.result.id);
				
				var file = new FileModel(data.result);				
				self.collection.add(file);
				
				file.addToTask(self.task.attributes.id);
			});
	        
	        return this;
	    },
	    
	});
	
	return TaskFileListView;
});