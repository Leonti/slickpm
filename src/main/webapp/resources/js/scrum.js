require(["jquery", "bootstrap", "jqueryui/sortable"], function($) {
    $(function() {
    	Scrum.init();
    });
});

var Scrum = {
	init: function() {
		
		$('.taskList').css('background', 'yellow').css('min-height', '200px');
		
		$('#backlog.taskList').sortable({
            connectWith: ".taskList",
            dropOnEmpty: true,
            
            update: function(event, ui) {            	
            	
            	var toBacklogId = ui.sender != null ? ui.item.attr('id').split('-')[1] : null;
            	Scrum.updateBacklog(Scrum._parseIds($(this).sortable('toArray')), toBacklogId);            	
            }
        }).disableSelection();	
		
		$('#iterations .taskList').sortable({
            connectWith: ".taskList",
            dropOnEmpty: true,
            
            update: function(event, ui) {
            	
            	var newId = ui.sender != null ? ui.item.attr('id').split('-')[1] : null;            	
            	Scrum.updateIteration($(this).data('iterationid'), Scrum._parseIds($(this).sortable('toArray')), newId);
            }
        }).disableSelection();
	},
	
	updateBacklog: function(idList, toBacklogId) {
		
		var updateData = {
				idList : idList
			};
		
		if (toBacklogId != null)
			updateData.toBacklogId = toBacklogId;
		
		$.getJSON('/project/updateBacklog', updateData, function(response) {
				console.log(response);
			});
	},
	
	updateIteration: function(iterationId, idList, newId) {
		
		var updateData = {
			iterationId : iterationId,
			idList : idList
		};
		
		if (newId != null)
			updateData.newId = newId;
		
		$.getJSON('/project/updateIteration', updateData, function(response) {
			console.log(response);
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