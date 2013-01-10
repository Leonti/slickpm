package com.leonti.slickpm.controller;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.leonti.slickpm.domain.AuthenticatedUser;
import com.leonti.slickpm.domain.User;
import com.leonti.slickpm.form.ForgotForm;
import com.leonti.slickpm.form.PasswordForm;
import com.leonti.slickpm.form.UserForm;
import com.leonti.slickpm.service.ConfigurationService;
import com.leonti.slickpm.service.EmailService;
import com.leonti.slickpm.service.UserService;
import com.leonti.slickpm.validator.ForgotFormValidator;
import com.leonti.slickpm.validator.PasswordFormValidator;
import com.leonti.slickpm.validator.UserFormValidator;

@Controller
@RequestMapping("/account")
public class AccountController {

	@Resource(name="UserService")
	UserService userService;

	@Resource(name="emailService")
	EmailService emailService;
	
	@Resource(name="configService")
	ConfigurationService configService;
	
	@Autowired
	UserFormValidator userFormValidator;	
	
	@Autowired
	ForgotFormValidator forgotFormValidator;
	
	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	private ApplicationContext context; 
	
	@Autowired
	private HttpServletRequest request;	
	
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String register(Model model) {
				
		model.addAttribute("userForm", new UserForm());						
		return "account/register";
	}
	
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String registerPost(@ModelAttribute("userForm") UserForm userForm, 
    					Model model,
    					BindingResult result) {
    	
    	
    	userFormValidator.validate(userForm, result); 
        if (result.hasErrors()) { 
        	return "account/register"; 
        } 
        
        User user = userForm.getUser();
        
        SecureRandom random = new SecureRandom();
        String confirmationKey = new BigInteger(130, random).toString(32);
        user.setConfirmationKey(confirmationKey);       

        user.setPassword(passwordEncoder.encodePassword(user.getPassword(), null));
        
        userService.save(user);   	
    	
		try {
			
	        Map<String, Object> replacements = new HashMap<String, Object>();
			replacements.put("confirmationkey", confirmationKey);
			
			emailService.sendSimpleMail(userForm.getEmail(), "confirmation", replacements);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    	return "redirect:registered";
	}
    
    @RequestMapping(value = "/registered", method = RequestMethod.GET)
    public String confirmationsent() {
    	return "account/confirmationsent";
    }	

    @RequestMapping(value = "/confirm", method = RequestMethod.GET)
    public String confirmation(@RequestParam(value="key", required=true) String confirmationKey,
    							Model model) {
    	
    	User user = userService.getByConfirmationKey(confirmationKey);

    	if (user == null) {    		
    		model.addAttribute("error", context.getMessage("confirmation.invalidKey", null, Locale.getDefault()));
    		return "errormessage";
    	}
    	
		user.setConfirmationKey(null);
		userService.save(user);
    	return "redirect:confirmed";
    }
    
    @RequestMapping(value = "/confirmed", method = RequestMethod.GET)
    public String confirmed() {
    	return "account/confirmed";
    }    
  
    
    @Secured("ROLE_USER")
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String getEdit(Model model) {
    	
    	User user = ((AuthenticatedUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();

		model.addAttribute("changePasswordForm", new PasswordForm());
		return "account/edit";
    } 
    
    @Secured("ROLE_USER")
    @RequestMapping(value = "/savepassword", method = RequestMethod.POST)
    public String savePassword( @ModelAttribute("changePasswordForm") PasswordForm changePasswordForm,
									Model model,
									BindingResult result) {
    	   	
    	User user = ((AuthenticatedUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
    	
    	PasswordFormValidator validator = new PasswordFormValidator();
    	validator.validate(changePasswordForm, result); 
    	   	
    	if (result.hasErrors()) { 		
    		
    		return "account/edit";
    	}

    	user.setPassword(passwordEncoder.encodePassword(changePasswordForm.getPassword(), null));
    	userService.save(user);
    	
    	return "redirect:..";    	
    }
    
    @RequestMapping(value = "/forgot", method = RequestMethod.GET)
    public String getForgot(Model model) {
    	
    	model.addAttribute("forgotForm", new ForgotForm());
    	return "account/forgot";
    }
    
    @RequestMapping(value = "/forgot", method = RequestMethod.POST)
    public String forgotPost(@ModelAttribute("forgotForm") ForgotForm forgotForm,
    							Model model,
    							BindingResult result) {

    	forgotFormValidator.validate(forgotForm, result);
    	if (result.hasErrors()) {
    		return "account/forgot";
    	}
    	
    	User user = userService.getByEmail(forgotForm.getEmail());
        SecureRandom random = new SecureRandom();
        String forgotKey = new BigInteger(130, random).toString(32);
    	
        user.setForgotKey(forgotKey);
        
        userService.save(user);
    	
		try {
			
	        Map<String, Object> replacements = new HashMap<String, Object>();
			replacements.put("forgotkey", forgotKey);
			
			emailService.sendSimpleMail(forgotForm.getEmail(), "forgot", replacements);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "redirect:resetsent";
    	
    }
    
    @RequestMapping(value = "/resetsent", method = RequestMethod.GET)
    public String resetSent() {
    	return "account/forgotsent";
    }
    
    @RequestMapping(value = "/reset", method = RequestMethod.GET)
    public String getReset(@RequestParam(value="key", required=true) String forgotKey,  
									Model model) {
    	    	
    	PasswordForm passwordForm = new PasswordForm();
    	
    	model.addAttribute("resetForm", passwordForm);
    	
    	return "account/reset";
    }
    
    @RequestMapping(value = "/reset", method = RequestMethod.POST)
    public String resetPost(@RequestParam(value="key", required=true) String forgotKey,
    						@ModelAttribute("resetForm") PasswordForm resetForm,
							Model model,
							BindingResult result) {
    	
    	PasswordFormValidator validator = new PasswordFormValidator();
    	validator.validate(resetForm, result);
    	if (result.hasErrors()) {
    		return "account/reset";
    	}
   	
    	User user = userService.getByForgotKey(forgotKey);
    	if (user == null) {
    		model.addAttribute("error", context.getMessage("reset.invalidKey", null, Locale.getDefault()));
    		return "errormessage";
    	}    	

    	user.setPassword(passwordEncoder.encodePassword(resetForm.getPassword(), null));
    	user.setForgotKey(null);
    	
    	userService.save(user);
    	
    	return "redirect:reseted";
    }
    
    @RequestMapping(value = "/reseted", method = RequestMethod.GET)
    public String reseted() {
    	return "account/reseted";
    }  
}
