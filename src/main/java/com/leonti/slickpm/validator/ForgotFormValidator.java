package com.leonti.slickpm.validator;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.leonti.slickpm.form.ForgotForm;
import com.leonti.slickpm.service.UserService;

@Component
public class ForgotFormValidator implements Validator {

	@Resource(name="UserService")
	UserService userService;	
	
	@Override
	public boolean supports(@SuppressWarnings("rawtypes") Class clazz) {
		return ForgotForm.class.equals(clazz);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "field.required");
		
		ForgotForm forgotForm = (ForgotForm) obj;
		
		if (userService.getByEmail(forgotForm.getEmail()) == null) {
			errors.rejectValue("email", "emailnotfound");
		}		
	}

}
