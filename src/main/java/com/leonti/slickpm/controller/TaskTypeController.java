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

import com.leonti.slickpm.domain.TaskType;
import com.leonti.slickpm.form.TaskTypeForm;
import com.leonti.slickpm.service.TaskTypeService;
import com.leonti.slickpm.validator.TaskTypeFormValidator;

@Controller
@RequestMapping("/taskType")
public class TaskTypeController {

	@Resource(name="TaskTypeService")
	TaskTypeService taskTypeService;
	
	@Autowired
	TaskTypeFormValidator taskTypeFormValidator;
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Model model) {
		model.addAttribute("list", taskTypeService.getList());
		
		return "taskType/list";
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model) {
		
		model.addAttribute("taskTypeForm", new TaskTypeForm());
		
		return "taskType/add";
	}	

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addPost(@ModelAttribute("taskTypeForm") TaskTypeForm taskTypeForm, 
    					Model model,
    					BindingResult result) {
    	   	
    	taskTypeFormValidator.validate(taskTypeForm, result); 
        if (result.hasErrors()) { 
        	return "taskType/add"; 
        } 
        
        taskTypeService.save(taskTypeForm.getTaskType());   	
		
    	return "redirect:list";
	}
    
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String edit(@RequestParam(value="id", required=true) Integer id,
    							Model model) {
    	
		model.addAttribute("taskTypeForm", new TaskTypeForm(taskTypeService.getById(id)));
		
		return "taskType/edit";
    }    
	
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String editPost(@RequestParam(value="id", required=true) Integer id,
    					@ModelAttribute("taskTypeForm") TaskTypeForm taskTypeForm, 
    					Model model,
    					BindingResult result) {
    	
    	taskTypeFormValidator.validate(taskTypeForm, result); 
        if (result.hasErrors()) { 
        	return "taskType/edit"; 
        } 

        TaskType taskType = taskTypeService.getById(id);
        taskType.setTitle(taskTypeForm.getTitle());
        taskType.setDescription(taskTypeForm.getDescription());
        
        taskTypeService.save(taskType);   	
		
    	return "redirect:list";
	} 	
    
}
