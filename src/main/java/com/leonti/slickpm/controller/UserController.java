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
import com.leonti.slickpm.domain.User;
import com.leonti.slickpm.form.UserForm;
import com.leonti.slickpm.service.UserService;
import com.leonti.slickpm.validator.UserFormValidator;

@Controller
@RequestMapping("/user")
public class UserController {

	@Resource(name="UserService")
	UserService userService;
	
	@Autowired
	UserFormValidator userFormValidator;
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Model model) {
		model.addAttribute("list", userService.getList());
		
		return "user/list";
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model) {
		
		model.addAttribute("userForm", new UserForm());
		
		return "user/add";
	}	

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addPost(@ModelAttribute("userForm") UserForm userForm, 
    					Model model,
    					BindingResult result) {
    	   	
    	userFormValidator.validate(userForm, result); 
        if (result.hasErrors()) { 
        	return "user/add"; 
        } 
        
        userService.save(userForm.getUser());   	
		
    	return "redirect:list";
	}
    
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String edit(@RequestParam(value="id", required=true) Integer id,
    							Model model) {
    	
		model.addAttribute("userForm", new UserForm(userService.getById(id)));
		
		return "user/edit";
    }    
	
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String editPost(@RequestParam(value="id", required=true) Integer id,
    					@ModelAttribute("userForm") UserForm userForm, 
    					Model model,
    					BindingResult result) {
    	
    	userFormValidator.validate(userForm, result); 
        if (result.hasErrors()) { 
        	return "user/edit"; 
        } 

        User user = userService.getById(id);
        user.setName(userForm.getName());      
        userService.save(user);   	
		
    	return "redirect:list";
	} 
    
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String delete(@RequestParam(value="id", required=true) Integer id,
    							Model model) {
		
		model.addAttribute("messageCode", "user.deleteConfirmation");
		model.addAttribute("backUrl", "/user/list");
		
		return "confirmation";
    }    
	
    
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String deletePost(@RequestParam(value="id", required=true) Integer id,
    					Model model) { 

    	User user = userService.getById(id);        
        userService.delete(user);   	
		       
    	return "redirect:list";
	}    
}
