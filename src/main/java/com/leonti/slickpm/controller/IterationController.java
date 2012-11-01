package com.leonti.slickpm.controller;

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

import com.leonti.slickpm.domain.Iteration;
import com.leonti.slickpm.domain.Task;
import com.leonti.slickpm.domain.TaskStage;
import com.leonti.slickpm.form.IterationForm;
import com.leonti.slickpm.form.IterationTaskForm;
import com.leonti.slickpm.form.TaskTaskStageForm;
import com.leonti.slickpm.service.IterationService;
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
    
    @RequestMapping(value = "/addTask", method = RequestMethod.POST)
    public String addTask(@RequestParam(value="taskId", required=true) Integer taskId,
			@ModelAttribute("iterationTaskForm") IterationTaskForm iterationTaskForm, 
			Model model) {
    	
    	Iteration iteration = iterationService.getById(iterationTaskForm.getIterationId());
    	Task task = taskService.getById(taskId);
    	
    	List<TaskStage> taskStages = taskStageService.getList();
    	if (taskStages.size() > 0) {
    		task.setTaskStage(taskStages.get(0));
    		taskService.save(task);
    	}
    	 	
    	iterationService.addTask(iteration, task);
    	
    	return "redirect:/project/scrum?id=" + iteration.getProject().getId();
    }
    
    @RequestMapping(value = "/removeTask", method = RequestMethod.POST)
    public String removeTask(@RequestParam(value="taskId", required=true) Integer taskId,
			Model model) {
    	
    	Task task = taskService.getById(taskId);
    	Integer projectId = task.getProject().getId();
    	iterationService.removeTask(task.getIteration(), task);
    	
    	return "redirect:/project/scrum?id=" + projectId;
    }
    
    @RequestMapping(value = "/taskboard", method = RequestMethod.GET)
    public String taskboard(@RequestParam(value="id", required=true) Integer id,
    							Model model) {
    	
    	Iteration iteration = iterationService.getById(id);
    	List<TaskStage> taskStages = taskStageService.getList();
    	  	
    	Map<TaskStage, List<Task>> tasks = new HashMap<TaskStage, List<Task>>();
    	
    	for (TaskStage taskStage : taskStages) {    		
    		tasks.put(taskStage, taskStageService.getTasksForStage(iteration, taskStage));
    	}
    	
    	model.addAttribute("iteration", iteration);
		model.addAttribute("taskStageList", taskStages);
		model.addAttribute("tasks", tasks);
		model.addAttribute("taskTaskStageForm", new TaskTaskStageForm());
		
		return "iteration/taskboard";
    }

    @RequestMapping(value = "/changeTaskStage", method = RequestMethod.POST)
    public String changeTaskStage(@RequestParam(value="taskId", required=true) Integer taskId,
			@ModelAttribute("taskTaskStageForm") TaskTaskStageForm taskTaskStageForm, 
			Model model) {
    	
    	Task task = taskService.getById(taskId);
    	task.setTaskStage(taskStageService.getById(taskTaskStageForm.getTaskStageId()));
    	
    	taskService.save(task);
    	
    	return "redirect:taskboard?id=" + task.getIteration().getId();
    }    
    
}
