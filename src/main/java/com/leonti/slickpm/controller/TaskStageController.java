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

import com.leonti.slickpm.domain.TaskStage;
import com.leonti.slickpm.form.TaskStageForm;
import com.leonti.slickpm.service.TaskStageService;
import com.leonti.slickpm.validator.TaskStageFormValidator;

@Controller
@RequestMapping("/taskstage")
public class TaskStageController {
	
	@Resource(name="TaskStageService")
	TaskStageService taskStageService;		
	
	@Autowired
	TaskStageFormValidator taskStageFormValidator;
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Model model) {
		
		model.addAttribute("list", taskStageService.getList());
		
		return "taskstage/list";
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model) {
		
		model.addAttribute("taskStageForm", new TaskStageForm());
		
		return "taskstage/add";
	}	

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addPost(@ModelAttribute("taskStageForm") TaskStageForm taskStageForm, 
		Model model,
		BindingResult result) {
    	   	
    	taskStageFormValidator.validate(taskStageForm, result); 
        if (result.hasErrors()) { 
        	return "taskstage/add"; 
        } 
        
        taskStageService.save(taskStageForm.getTaskStage());   	
		
    	return "redirect:list";
	}
    
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String edit(@RequestParam(value="id", required=true) Integer id,
    							Model model) {
    	
    	TaskStage taskStage = taskStageService.getById(id);
		model.addAttribute("taskStageForm", new TaskStageForm(taskStage));
		
		return "taskstage/edit";
    }    
	
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String editPost(@RequestParam(value="id", required=true) Integer id,
    					@ModelAttribute("taskStageForm") TaskStageForm taskStageForm, 
    					Model model,
    					BindingResult result) {
    	
    	taskStageFormValidator.validate(taskStageForm, result); 
        if (result.hasErrors()) { 
        	return "taskstage/edit"; 
        } 

        TaskStage taskStage = taskStageService.getById(id);
        taskStage.setTitle(taskStageForm.getTitle());
        taskStage.setDescription(taskStageForm.getDescription());
        
        taskStageService.save(taskStage);   	
		
    	return "redirect:list";
	}

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String delete(@RequestParam(value="id", required=true) Integer id,
    							Model model) {
		
		model.addAttribute("messageCode", "taskStage.deleteConfirmation");
		model.addAttribute("backUrl", "/taskstage/list");
		
		return "confirmation";
    }    
	   
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String deletePost(@RequestParam(value="id", required=true) Integer id,
    					Model model) { 

        TaskStage taskStage = taskStageService.getById(id);        
        taskStageService.delete(taskStage);   	
		       
    	return "redirect:list";
	}	
}
