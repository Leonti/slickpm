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

import com.leonti.slickpm.domain.Project;
import com.leonti.slickpm.form.ProjectForm;
import com.leonti.slickpm.service.IterationService;
import com.leonti.slickpm.service.ProjectService;
import com.leonti.slickpm.service.TaskService;
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
    	
    	model.addAttribute("backlogList", taskService.getList(id));
    	model.addAttribute("iterationList", iterationService.getList(id));
    	model.addAttribute("projectId", id);
    	
		return "project/scrum";
    }   
	
}
