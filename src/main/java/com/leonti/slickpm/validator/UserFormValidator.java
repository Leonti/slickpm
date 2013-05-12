package com.leonti.slickpm.validator;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.leonti.slickpm.form.UserForm;
import com.leonti.slickpm.service.UserService;

@Component
public class UserFormValidator implements Validator {

	@Resource(name = "UserService")
	UserService userService;

	@Override
	public boolean supports(@SuppressWarnings("rawtypes") Class clazz) {
		return UserForm.class.equals(clazz);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name",
				"field.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email",
				"field.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password",
				"field.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "repeatPassword",
				"field.required");

		UserForm userForm = (UserForm) obj;

		if (userService.getByEmail(userForm.getEmail()) != null) {
			errors.rejectValue("email", "userregistered");
		}

		if (!userForm.getPassword().equals(userForm.getRepeatPassword())) {
			errors.rejectValue("repeatPassword", "passwordsnotequal");
		}
	}

}
