package com.leonti.slickpm.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.leonti.slickpm.domain.Iteration;
import com.leonti.slickpm.domain.IterationPosition;
import com.leonti.slickpm.domain.Task;
import com.leonti.slickpm.domain.TaskStage;
import com.leonti.slickpm.domain.TaskStagePosition;
import com.leonti.slickpm.domain.dto.IterationDTO;
import com.leonti.slickpm.domain.dto.TaskDTO;
import com.leonti.slickpm.hook.EmailNotificationHook;
import com.leonti.slickpm.service.IterationService;
import com.leonti.slickpm.service.PositionService;
import com.leonti.slickpm.service.ProjectService;
import com.leonti.slickpm.service.TaskService;
import com.leonti.slickpm.service.TaskStageService;
import com.leonti.slickpm.service.UploadedFileService;
import com.leonti.slickpm.validator.IterationFormValidator;

@Controller
@RequestMapping("/iteration")
public class IterationController {

	@Resource(name="IterationService")
	IterationService iterationService;
	
	@Resource(name="ProjectService")
	ProjectService projectService;	

	@Resource(name="TaskService")
	TaskService taskService;	

	@Resource(name="TaskStageService")
	TaskStageService taskStageService;	

	@Resource(name="PositionService")
	PositionService positionService;
	
	@Resource(name="UploadedFileService")
	UploadedFileService uploadedFileService;	
	
	@Autowired
	IterationFormValidator iterationFormValidator;		

	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	@ResponseBody
	public IterationDTO RESTDetails(@PathVariable("id") Integer id) {
		
		return iterationService.getById(id).getDTO();
	}	
	
	@RequestMapping(value = "", method = RequestMethod.POST)
	@ResponseBody
	public IterationDTO RESTAdd(@RequestBody IterationDTO iterationDTO) {
		
		Iteration iteration = new Iteration();
		iteration.setTitle(iterationDTO.getTitle());
		iteration.setDescription(iterationDTO.getDescription());
		iteration.setProject(projectService.getById(iterationDTO.getProjectId()));
		iteration.setStartDate(iterationDTO.getStartDate());
		iteration.setEndDate(iterationDTO.getEndDate());
		
		iterationService.save(iteration);
		
		return iteration.getDTO();
	}
	
	@RequestMapping(value = "{id}", method = RequestMethod.PUT)
	@ResponseBody
	public IterationDTO RESTUpdate(
			@RequestBody IterationDTO iterationDTO,
			@PathVariable("id") Integer id) {
		
		Iteration iteration = iterationService.getById(id);
		iteration.setTitle(iterationDTO.getTitle());
		iteration.setDescription(iterationDTO.getDescription());
		iteration.setStartDate(iterationDTO.getStartDate());
		iteration.setEndDate(iterationDTO.getEndDate());		
		
		iterationService.save(iteration);
		
		return iteration.getDTO();
	}	

	
	@RequestMapping(value = "{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public void RESTDelete(@PathVariable("id") Integer id) {
		
		iterationService.delete(iterationService.getById(id));
	}
	
	@RequestMapping(value = "{iterationId}/task", method = RequestMethod.GET)
	public @ResponseBody List<TaskDTO> RESTTaskList(
			@PathVariable("iterationId") Integer iterationId) {		
			
		List<IterationPosition> iterationPositions = 
				positionService.getIterationPositions(iterationService.getById(iterationId).getTasks());
		
		List<TaskDTO> taskDTOList = new ArrayList<TaskDTO>();    	
    	for (IterationPosition position : iterationPositions) {
    		Task task = position.getTask();
    		taskDTOList.add(task.getDTO());      		
    	}  		
		
		return taskDTOList;
	}
	
	@RequestMapping(value = "{iterationId}/stagetasks/{stageId}", method = RequestMethod.GET)
	public @ResponseBody List<TaskDTO> RESTStageTaskList(
			@PathVariable("iterationId") Integer iterationId,
			@PathVariable("stageId") Integer stageId) {
		
		List<TaskStagePosition> positions = positionService.getTaskStagePositions(taskStageService.getTasksForStage(
				iterationService.getById(iterationId), 
				taskStageService.getById(stageId)));
		
		List<TaskDTO> taskDTOList = new ArrayList<TaskDTO>();      	
    	for (TaskStagePosition position : positions) {
    		Task task = position.getTask();
    		taskDTOList.add(task.getDTO());
    	}      			
		
		return taskDTOList;
	}
	
	@RequestMapping(value = "{iterationId}/updateStage/{stageId}", method = RequestMethod.POST)
	public @ResponseBody Map<String, String> updateStage(
			@PathVariable("iterationId") Integer iterationId,
			@PathVariable("stageId") Integer stageId,
			@RequestParam(value="idList", required=true) String idList) {
    	
    	if (idList.length() > 0) {
    		
    		
    		TaskStage taskStage = taskStageService.getById(stageId);
    		List<Task> tasks = taskStageService.getTasksForStage(				
    				iterationService.getById(iterationId), 
    				taskStage);
    		   		
    		for (String id : idList.split(",")) {
    			Task task = taskService.getById(Integer.parseInt(id));
    			
    			// if task is not in this stage - change it's stage to current stage
    			if (!tasks.contains(task)) {

    	        	TaskStage previousStage = task.getTaskStage();
    	        	
    	        	task.setTaskStage(taskStage);
    	        	taskService.save(task);
    	        	
    	        	EmailNotificationHook emailNotificationHook = new EmailNotificationHook();
    	        	emailNotificationHook.execute(task, previousStage);   
    			}
    		}
    		
        	int positionCount = 0;
    	    	for (String id : idList.split(",")) {
    	    		
    	    		TaskStagePosition position = positionService.getOrCreateTaskStagePosition(taskService.getById(Integer.parseInt(id)));
    	    		position.setPosition(positionCount);
    	    		positionService.save(position);
    	    		positionCount++;
    	    	}      		   		   		
    	}  	
		
    	Map<String, String> result = new HashMap<String, String>();
    	result.put("result", "OK");
    	
    	return result;
	}	
    
    @RequestMapping(value = "/getStats", method = RequestMethod.GET)
    public @ResponseBody Map<String, String> getStats(
    		@RequestParam(value="id", required=true) Integer id) {    
    	
    	Iteration iteration = iterationService.getById(id);
    	
    	Map<String, String> result = new HashMap<String, String>();
 
    	result.put("plannedPoints", iterationService.getPlannedPoints(iteration).toString());
    	result.put("donePoints", iterationService.getDonePoints(iteration).toString());      	    	
    	result.put("result", "OK");
    	
    	return result;
    }
  
}
