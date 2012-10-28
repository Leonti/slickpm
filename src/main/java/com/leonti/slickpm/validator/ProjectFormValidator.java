package com.leonti.slickpm.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.leonti.slickpm.form.ProjectForm;

@Component
public class ProjectFormValidator implements Validator {

	public boolean supports(Class<?> classToCheck) {
		return ProjectForm.class.equals(classToCheck);
	}

	public void validate(Object arg0, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", "field.required");
	}

}
