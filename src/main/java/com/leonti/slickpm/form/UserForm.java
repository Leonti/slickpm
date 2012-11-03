package com.leonti.slickpm.form;

import com.leonti.slickpm.domain.User;

public class UserForm {

	private String name;

	public UserForm() {}
	
	public UserForm(User user) {
		this.name = user.getName();
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public User getUser() {
		return new User(this.name);
	}
}
