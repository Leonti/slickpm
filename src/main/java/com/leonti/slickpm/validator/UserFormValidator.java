package com.leonti.slickpm.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import com.leonti.slickpm.form.UserForm;

@Component
public class UserFormValidator {
	
	public boolean supports(Class<?> classToCheck) {
		return UserForm.class.equals(classToCheck);
	}

	public void validate(Object arg0, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "field.required");
	}
}
