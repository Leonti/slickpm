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

import com.leonti.slickpm.domain.Iteration;
import com.leonti.slickpm.form.IterationForm;
import com.leonti.slickpm.service.IterationService;
import com.leonti.slickpm.service.ProjectService;
import com.leonti.slickpm.validator.IterationFormValidator;

@Controller
@RequestMapping("/iteration")
public class IterationController {

	@Resource(name="IterationService")
	IterationService iterationService;
	
	@Resource(name="ProjectService")
	ProjectService projectService;	
	
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
}
