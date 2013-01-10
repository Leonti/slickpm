define([
	'jquery',
	'underscore',
	'backbone',
	'text!templates/userDetails.html',
	'fileupload/jquery.iframe-transport', 
	'fileupload/jquery.fileupload'
], function( $, _, Backbone, userDetailsTemplate ) {
	
	var UserDetailsView = Backbone.View.extend({
		 
	    template:_.template(userDetailsTemplate),
	    
	    initialize: function (options) {
	    	this.userList = options.userList;
	    	
	        this.model.bind("change", this.render, this);	        
	    },
	 
	    render:function (eventName) {
	    	
	        $(this.el).html(this.template(this.model.toJSON()));
	    	
	        var self = this;
	        $('#avatarupload', $(this.el)).fileupload()
			.bind('fileuploaddone', function (e, data) {    			
				$('.avatar .background').prop("src", '/file/download/' + data.result.id + '/avatar');
				$('.avatarId').val(data.result.id);
				self.saveUser();
			});
	        
	        
	        $(this.el).find('.name').editable({
	        	onSave: function() { self.saveUser(); }
	        });
	        
	        return this;
	    },
	 
	    events:{
	        "click .save": "saveUser",
	        "click .delete": "deleteUser"
	    },
	 
	    saveUser:function () {
	        this.model.set({
	            name: $('.name', $(this.el)).text(),
	            avatarId: $('.avatarId', $(this.el)).val()
	        });
	        if (this.model.isNew()) {
	        	
	        	var self = this;
	            this.userList.create(this.model, {
	            	success: function() {
	            		self.trigger("userCreated", self.model.id);
	            	}
	            });
	        } else {
	            this.model.save();
	        }
	        return false;
	    },
	 
	    deleteUser:function () {
	    	var self = this;
	        this.model.destroy({
	            success:function () {
	                window.history.back();
	                self.close();
	            }
	        });
	        return false;
	    },
	 
	    close:function () {
	        $(this.el).unbind();
	        $(this.el).empty();
	    }
	 
	});
	
	return UserDetailsView;
});