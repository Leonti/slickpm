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

import com.leonti.slickpm.domain.BacklogPosition;
import com.leonti.slickpm.domain.Iteration;
import com.leonti.slickpm.domain.IterationPosition;
import com.leonti.slickpm.domain.Project;
import com.leonti.slickpm.domain.Task;
import com.leonti.slickpm.form.ProjectForm;
import com.leonti.slickpm.service.IterationService;
import com.leonti.slickpm.service.PositionService;
import com.leonti.slickpm.service.ProjectService;
import com.leonti.slickpm.service.TaskService;
import com.leonti.slickpm.service.TaskStageService;
import com.leonti.slickpm.validator.ProjectFormValidator;

@Controller
@RequestMapping("/project")
public class ProjectController {

	@Resource(name="ProjectService")
	ProjectService projectService;

	@Resource(name="IterationService")
	IterationService iterationService;	
	
	@Resource(name="TaskService")
	TaskService taskService;	
	
	@Resource(name="PositionService")
	PositionService positionService;	

	@Resource(name="TaskStageService")
	TaskStageService taskStageService;		
	
	@Autowired
	ProjectFormValidator projectFormValidator;
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Model model) {
		model.addAttribute("list", projectService.getList());
		
		return "project/list";
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model) {
		
		model.addAttribute("projectForm", new ProjectForm());
		
		return "project/add";
	}	

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addPost(@ModelAttribute("projectForm") ProjectForm projectForm, 
    					Model model,
    					BindingResult result) {
    	   	
    	projectFormValidator.validate(projectForm, result); 
        if (result.hasErrors()) { 
        	return "project/add"; 
        } 
        
        projectService.save(projectForm.getProject());   	
		
    	return "redirect:list";
	}
    
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String edit(@RequestParam(value="id", required=true) Integer id,
    							Model model) {
    	
		model.addAttribute("projectForm", new ProjectForm(projectService.getById(id)));
		
		return "project/edit";
    }    
	
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String editPost(@RequestParam(value="id", required=true) Integer id,
    					@ModelAttribute("projectForm") ProjectForm projectForm, 
    					Model model,
    					BindingResult result) {
    	
    	projectFormValidator.validate(projectForm, result); 
        if (result.hasErrors()) { 
        	return "project/edit"; 
        } 

        Project project = projectService.getById(id);
        project.setTitle(projectForm.getTitle());
        project.setDescription(projectForm.getDescription());
        
        projectService.save(project);   	
		
    	return "redirect:list";
	}
    
    @RequestMapping(value = "/scrum", method = RequestMethod.GET)
    public String scrumView(@RequestParam(value="id", required=true) Integer id,
    							Model model) {
    	
    	List<BacklogPosition> positions = positionService.getBacklogPositions(taskService.getBacklogList(id));
    	
    	List<Task> sortedTasks = new ArrayList<Task>();    	
    	for (BacklogPosition position : positions) {
    		sortedTasks.add(position.getTask());
    	}
    	
    	Map<Iteration, List<Task>> sortedIterationTasks = new HashMap<Iteration, List<Task>>();
    	
    	List<Iteration> iterations = iterationService.getList(id);
    	
    	for (Iteration iteration : iterations) {
    		
    		List<IterationPosition> iterationPositions = positionService.getIterationPositions(iteration.getTasks());
    		
        	List<Task> sorted = new ArrayList<Task>();    	
        	for (IterationPosition position : iterationPositions) {
        		sorted.add(position.getTask());
        	}  

        	sortedIterationTasks.put(iteration, sorted);
    	}
    	
    	model.addAttribute("backlogList", sortedTasks);
    	model.addAttribute("iterationList", iterationService.getList(id));
    	model.addAttribute("sortedIterationTasks", sortedIterationTasks);
    	model.addAttribute("projectId", id);
    	
		return "project/scrum";
    }
    
    @RequestMapping(value = "/updateBacklog", method = RequestMethod.GET)
    public @ResponseBody Map<String, String> updateBacklog(
    		@RequestParam(value="idList", required=true) String idList,
    		@RequestParam(value="toBacklogId", required=false) Integer toBacklogId) {
    	
    	if (toBacklogId != null) {
        	Task task = taskService.getById(toBacklogId);
        	iterationService.removeTask(task.getIteration(), task);
    	}
    	
    	int positionCount = 0;
    	if (idList.length() > 0) {
	    	for (String id : idList.split(",")) {
	    		
	    		BacklogPosition position = positionService.getOrCreateBacklogPosition(taskService.getById(Integer.parseInt(id)));
	    		position.setPosition(positionCount);
	    		positionService.save(position);
	    		positionCount++;
	    	}
    	}
    	
    	Map<String, String> result = new HashMap<String, String>();
    	result.put("result", "OK");
    	
    	return result;
    }
    
    @RequestMapping(value = "/updateIteration", method = RequestMethod.GET)
    public @ResponseBody Map<String, String> updateIteration(
    		@RequestParam(value="iterationId", required=true) Integer iterationId,
    		@RequestParam(value="idList", required=true) String idList,
    		@RequestParam(value="newId", required=false) Integer newId) {

    	if (newId != null) {
	    	Iteration iteration = iterationService.getById(iterationId);
	    	Task task = taskService.getById(newId);
	
	    	if (taskStageService.getFirstStage() != null) {
	    		task.setTaskStage(taskStageService.getFirstStage());
	    		taskService.save(task);    		
	    	}
	    	 	
	    	iterationService.addTask(iteration, task);    		
    	}

    	int positionCount = 0;
    	if (idList.length() > 0) {
	    	for (String id : idList.split(",")) {
	    		
	    		IterationPosition position = positionService.getOrCreateIterationPosition(taskService.getById(Integer.parseInt(id)));
	    		position.setPosition(positionCount);
	    		positionService.save(position);
	    		positionCount++;
	    	} 
    	}
    	   	
    	Map<String, String> result = new HashMap<String, String>();
    	result.put("result", "OK");
    	
    	return result;
    }
	
}
