package com.leonti.slickpm.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.leonti.slickpm.form.PasswordForm;

public class PasswordFormValidator implements Validator {

	@Override
	public boolean supports(@SuppressWarnings("rawtypes") Class clazz) {
		return PasswordForm.class.equals(clazz);
	}

	@Override
	public void validate(Object obj, Errors errors) {

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password",
				"field.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "repeatPassword",
				"field.required");

		PasswordForm passwordForm = (PasswordForm) obj;

		if (!passwordForm.getPassword()
				.equals(passwordForm.getRepeatPassword())) {
			errors.rejectValue("repeatPassword", "passwordsnotequal");
		}
	}
}
