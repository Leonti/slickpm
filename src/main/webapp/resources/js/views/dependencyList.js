define([
	'jquery',
	'underscore',
	'backbone',
	'collections/dependencyCandidates',
	'views/dependencyListItem',
	'views/TaskSelector',
	'text!templates/dependencyList.html'
], function( $, _, Backbone, 
		DependencyCandidatesCollection,
		DependencytListItemView,
		TaskSelectorView,
		dependencyListTemplate ) {
	
	var DependencyListView = Backbone.View.extend({
		 
		template: _.template(dependencyListTemplate),
		className: 'list',
	 
	    initialize: function(options) {
	        this.collection.bind("reset", this.render, this);
	        this.collection.bind("add", function (dependency) {
	            $(this.el).find('ul').append(new DependencytListItemView({model: dependency}).render().el);
	        }, this);
	        
	        this.task = options.task;
	    },
	 
	    render:function (eventName) {
	    	
	    	$(this.el).html(this.template());
	    	
	        _.each(this.collection.models, function (dependency) {
	        	
	        	var dependencyListItemView = new DependencytListItemView({ model: dependency });	        	
	            $(this.el).find('ul').append(dependencyListItemView.render().el);
	            
	            dependencyListItemView.bind("removeDependency", function(id) {
	            	this.task.removeDependency(id);	            	
	            	this.collection.remove(id);            	
	            }, this);
	            
	        }, this);
	        return this;
	    },
	    
	    events:{
	        "click .addDependency": "addDependency"
	    },	    
	 
	    addDependency: function() {
	    	
	    	var dependencyCandidates = new DependencyCandidatesCollection(null, {taskId: this.task.id});	    	
	    	var taskSelectorView = new TaskSelectorView({collection: dependencyCandidates});
	    	
	    	var self = this;
	    	taskSelectorView.bind("taskSelected", function(task) {
	    		self.task.addDependency(task.id);	    		
	    		self.task.bind("dependencyAdded", function() {
	    			self.collection.add(task);
	    		});
	    	});
	    	
	    	return false;
	    }	    
	});
	
	return DependencyListView;
});