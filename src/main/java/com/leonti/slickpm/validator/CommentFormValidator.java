package com.leonti.slickpm.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import com.leonti.slickpm.form.CommentForm;

@Component
public class CommentFormValidator {

	public boolean supports(Class<?> classToCheck) {
		return CommentForm.class.equals(classToCheck);
	}

	public void validate(Object arg0, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "content", "field.required");
	}	
}
