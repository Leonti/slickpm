require(["jquery", "bootstrap", "fileupload/jquery.iframe-transport", "fileupload/jquery.fileupload"], function($) {
    $(function() {
    	$('#avatarupload').fileupload()
    		.bind('fileuploaddone', function (e, data) {    			
    			$('.avatar .background').prop("src", '/file/download/' + data.result.id + '/' + data.result.filename);
    			
    			$.post('/user/avatar', {id: $('#avatarupload').data('userid'), fileId: data.result.id});
    		});   	   	
    });
});