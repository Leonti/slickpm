package com.leonti.slickpm.service;

import java.util.List;

import com.leonti.slickpm.domain.User;

public interface UserService {

	User save(User user);

	void delete(User user);

	List<User> getList();

	User getById(Integer id);

	User getByEmail(String email);

	User getByForgotKey(String forgotKey);

	User getByConfirmationKey(String confirmationKey);

}