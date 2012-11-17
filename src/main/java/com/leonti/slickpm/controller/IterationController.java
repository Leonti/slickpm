package com.leonti.slickpm.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.leonti.slickpm.domain.Iteration;
import com.leonti.slickpm.domain.Task;
import com.leonti.slickpm.domain.TaskStage;
import com.leonti.slickpm.domain.TaskStagePosition;
import com.leonti.slickpm.domain.UploadedFile;
import com.leonti.slickpm.form.IterationForm;
import com.leonti.slickpm.hook.EmailNotificationHook;
import com.leonti.slickpm.service.IterationService;
import com.leonti.slickpm.service.PositionService;
import com.leonti.slickpm.service.ProjectService;
import com.leonti.slickpm.service.TaskService;
import com.leonti.slickpm.service.TaskStageService;
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
	
	@Autowired
	IterationFormValidator iterationFormValidator;	
	
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(@RequestParam(value="projectId", required=true) Integer projectId,
			Model model) {
		
		model.addAttribute("projectId", projectId);
		model.addAttribute("iterationForm", new IterationForm());
		
		return "iteration/add";
	}	

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addPost(@RequestParam(value="projectId", required=true) Integer projectId,
		@ModelAttribute("projectForm") IterationForm iterationForm, 
		Model model,
		BindingResult result) {
    	   	
    	iterationFormValidator.validate(iterationForm, result); 
        if (result.hasErrors()) { 
        	return "iteration/add"; 
        } 
        
        iterationService.save(iterationForm.getIteration(projectService.getById(projectId)));   	
		
    	return "redirect:/project/scrum?id=" + projectId;
	}
    
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String edit(@RequestParam(value="id", required=true) Integer id,
    							Model model) {
    	
    	Iteration iteration = iterationService.getById(id);
		model.addAttribute("iterationForm", new IterationForm(iteration));
		
		return "iteration/edit";
    }    
	
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String editPost(@RequestParam(value="id", required=true) Integer id,
    					@ModelAttribute("iterationForm") IterationForm iterationForm, 
    					Model model,
    					BindingResult result) {
    	
    	iterationFormValidator.validate(iterationForm, result); 
        if (result.hasErrors()) { 
        	return "iteration/edit"; 
        } 

        Iteration iteration = iterationService.getById(id);
        iteration.setTitle(iterationForm.getTitle());
        iteration.setDescription(iterationForm.getDescription());
        
        iterationService.save(iteration);   	
		
    	return "redirect:/project/scrum?id=" + iteration.getProject().getId();
	}

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String delete(@RequestParam(value="id", required=true) Integer id,
    							Model model) {
		
		model.addAttribute("messageCode", "iteration.deleteConfirmation");
		
		Iteration iteration = iterationService.getById(id);
		model.addAttribute("backUrl", "/iteration/list?projectId=" + iteration.getProject().getId());
		
		return "confirmation";
    }    
	
    
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String deletePost(@RequestParam(value="id", required=true) Integer id,
    					Model model) { 

        Iteration iteration = iterationService.getById(id);
        Integer projectId = iteration.getProject().getId();
        
        iterationService.delete(iteration);   	
		       
    	return "redirect:list?projectId=" + projectId;
	} 
    
    @RequestMapping(value = "/taskboard", method = RequestMethod.GET)
    public String taskboard(@RequestParam(value="id", required=true) Integer id,
    							Model model) {
    	
    	Iteration iteration = iterationService.getById(id);
    	List<TaskStage> taskStages = taskStageService.getList();
    	  	
    	Map<TaskStage, List<Task>> tasks = new HashMap<TaskStage, List<Task>>();
    	Map<Task, String> avatars = new HashMap<Task, String>();
    	
    	for (TaskStage taskStage : taskStages) {    		
 
    		List<TaskStagePosition> positions = positionService.getTaskStagePositions(taskStageService.getTasksForStage(iteration, taskStage));
    		
        	List<Task> sorted = new ArrayList<Task>();    	
        	for (TaskStagePosition position : positions) {
        		Task task = position.getTask();
        		sorted.add(task);
        		avatars.put(task, getTaskAvatar(task));
        	}      		
    		
    		tasks.put(taskStage, sorted);
    	}
    	
    	model.addAttribute("iteration", iteration);  	
		model.addAttribute("taskStageList", taskStages);
		model.addAttribute("tasks", tasks);
		model.addAttribute("avatars", avatars);
		
		return "iteration/taskboard";
    }
    
    private String getTaskAvatar(Task task) {
    	if (task.getUser() == null)
    		return "no_user";
    	
    	UploadedFile avatar = task.getUser().getAvatar();
    	if (avatar == null)
    		return "/resources/images/avatar_placeholder.png";
    	  	
    	return "/file/download/" + avatar.getId() + "/" + avatar.getFilename();
    }

    @RequestMapping(value = "/updateTaskStage", method = RequestMethod.GET)
    public @ResponseBody Map<String, String> updateTaskStage(
    		@RequestParam(value="taskStageId", required=true) Integer taskStageId,
    		@RequestParam(value="idList", required=true) String idList,
    		@RequestParam(value="newId", required=false) Integer newId) {
    	
    	if (newId != null) {
        	Task task = taskService.getById(newId);
        	TaskStage previousStage = task.getTaskStage();
        	
        	task.setTaskStage(taskStageService.getById(taskStageId));
        	taskService.save(task);
        	
        	EmailNotificationHook emailNotificationHook = new EmailNotificationHook();
        	emailNotificationHook.execute(task, previousStage);        	
    	}
   
    	int positionCount = 0;
    	if (idList.length() > 0) {
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
