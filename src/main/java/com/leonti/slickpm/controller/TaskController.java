package com.leonti.slickpm.controller;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.leonti.slickpm.domain.Task;
import com.leonti.slickpm.form.CommentForm;
import com.leonti.slickpm.form.TaskForm;
import com.leonti.slickpm.service.CommentService;
import com.leonti.slickpm.service.ProjectService;
import com.leonti.slickpm.service.TaskService;
import com.leonti.slickpm.service.TaskTypeService;
import com.leonti.slickpm.validator.TaskFormValidator;

@Controller
@RequestMapping("/task")
public class TaskController {

	@Resource(name="TaskService")
	TaskService taskService;

	@Resource(name="TaskTypeService")
	TaskTypeService taskTypeService;	
	
	@Resource(name="ProjectService")
	ProjectService projectService;	

	@Resource(name="CommentService")
	CommentService commentService;		
	
	@Autowired
	TaskFormValidator taskFormValidator;
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(@RequestParam(value="projectId", required=true) Integer projectId,
			Model model) {
		
		model.addAttribute("projectId", projectId);
		model.addAttribute("list", taskService.getList(projectId));
		
		return "task/list";
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(@RequestParam(value="projectId", required=true) Integer projectId,
			Model model) {
		
		model.addAttribute("projectId", projectId);
		model.addAttribute("taskTypeList", taskTypeService.getList());
		model.addAttribute("taskForm", new TaskForm());
		
		return "task/add";
	}	

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addPost(@RequestParam(value="projectId", required=true) Integer projectId,
		@ModelAttribute("projectForm") TaskForm taskForm, 
		Model model,
		BindingResult result) {
    	   	
    	taskFormValidator.validate(taskForm, result); 
        if (result.hasErrors()) { 
        	return "task/add"; 
        } 
        
        taskService.save(taskForm.getTask(
        		projectService.getById(projectId), 
        		taskTypeService.getById(taskForm.getTaskTypeId())));   	
		
    	return "redirect:list?projectId=" + projectId;
	}
    
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String edit(@RequestParam(value="id", required=true) Integer id,
    							Model model) {
    	
    	Task task = taskService.getById(id);
    	model.addAttribute("taskTypeList", taskTypeService.getList());
		model.addAttribute("taskForm", new TaskForm(task));
		
		return "task/edit";
    }    
	
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String editPost(@RequestParam(value="id", required=true) Integer id,
    					@ModelAttribute("taskForm") TaskForm taskForm, 
    					Model model,
    					BindingResult result) {
    	
    	taskFormValidator.validate(taskForm, result); 
        if (result.hasErrors()) { 
        	return "task/edit"; 
        } 

        Task task = taskService.getById(id);
        task.setTitle(taskForm.getTitle());
        task.setDescription(taskForm.getDescription());
        
        taskService.save(task);   	
		
    	return "redirect:list?projectId=" + task.getProject().getId();
	}

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String delete(@RequestParam(value="id", required=true) Integer id,
    							Model model) {
		
		model.addAttribute("messageCode", "task.deleteConfirmation");
		
		Task task = taskService.getById(id);
		model.addAttribute("backUrl", "/task/list?projectId=" + task.getProject().getId());
		
		return "confirmation";
    }    
	
    
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String deletePost(@RequestParam(value="id", required=true) Integer id,
    					Model model) { 

        Task task = taskService.getById(id);
        Integer projectId = task.getProject().getId();
        
        taskService.delete(task);   	
		       
    	return "redirect:list?projectId=" + projectId;
	}
 
    @RequestMapping(value = "/details", method = RequestMethod.GET)
    public String details(@RequestParam(value="id", required=true) Integer id,
    							Model model) {
    	
    	Task task = taskService.getById(id);
    	model.addAttribute("task", task);
    	model.addAttribute("commentForm", new CommentForm()); 
    	
		return "task/details";
    }      
    
    @RequestMapping(value = "/addComment", method = RequestMethod.POST)
    public String addComment(@RequestParam(value="id", required=true) Integer id,
    		@ModelAttribute("commentForm") CommentForm commentForm,
			Model model) {

    	Task task = taskService.getById(id);
		taskService.addComment(task, commentService.save(commentForm.getComment(task)));  	
    	
    	return "redirect:details?id=" + id;
    }

}
