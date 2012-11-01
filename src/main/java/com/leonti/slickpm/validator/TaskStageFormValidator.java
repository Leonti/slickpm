package com.leonti.slickpm.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import com.leonti.slickpm.form.TaskStageForm;

@Component
public class TaskStageFormValidator {
	
	public boolean supports(Class<?> classToCheck) {
		return TaskStageForm.class.equals(classToCheck);
	}

	public void validate(Object arg0, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", "field.required");
	}
}
