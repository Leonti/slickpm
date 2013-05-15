package com.leonti.slickpm.service;

import java.util.Map;

import javax.mail.MessagingException;

public interface EmailService {

	void sendSimpleMail(String recipientEmail, String template,
			Map<String, Object> replacements) throws MessagingException;

}