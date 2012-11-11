require(["jquery", "bootstrap", "jqueryui/sortable"], function($) {
    $(function() {
    	Taskboard.init();
    });
});

var Taskboard = {
		
	init: function() {	
		
		$('.taskList').sortable({
            connectWith: ".taskList",
            dropOnEmpty: true,
            
            update: function(event, ui) {            	
            	
            	var newId = ui.sender != null ? ui.item.attr('id').split('-')[1] : null;        	
            	Taskboard.updateTaskStage($(this).data('taskstageid'), Taskboard._parseIds($(this).sortable('toArray')), newId);            	
            },
            
            over: function(event, ui) {
            	$(this).addClass('hover');
            },
            
            out: function(event, ui) {
            	$(this).removeClass('hover');
            } 
            
        }).disableSelection();
		
		Taskboard.updateStats();
	},
	
	updateStats: function() {
		$.getJSON('/iteration/getStats', {id : $('#taskboard').data('iterationid')}, function(response) {
			$('.plannedPoints').text(response.plannedPoints);
			$('.donePoints').text(response.donePoints);
		});		
	},
	
	updateTaskStage: function(taskStageId, idList, newId) {
		
		var updateData = {
			taskStageId : taskStageId,
			idList : idList
		};
		
		if (newId != null)
			updateData.newId = newId;
		
		console.log(updateData);
		$.getJSON('/iteration/updateTaskStage', updateData, function(response) {
			console.log(response);
			Taskboard.updateStats();
		});
	},
	
	_parseIds: function(idArray) {
		var ids = [];
		for (var i = 0; i < idArray.length; i++) {
			ids.push(idArray[i].split('-')[1]);
		}
		
		return ids.join(',');
	}	
};