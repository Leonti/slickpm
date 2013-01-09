package com.leonti.slickpm.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.leonti.slickpm.form.EditUserForm;

@Component
public class EditUserFormValidator implements Validator {	
	
	@Override
	public boolean supports(@SuppressWarnings("rawtypes") Class clazz) {
		return EditUserForm.class.equals(clazz);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "field.required");
	}
}
