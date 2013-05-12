package com.leonti.slickpm.service;

import java.io.IOException;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;

@Service("emailService")
public class EmailService {

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private VelocityEngine velocityEngine;

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private ApplicationContext context;

	public void sendSimpleMail(String recipientEmail, String template,
			Map<String, Object> replacements) throws MessagingException {

		Properties props;
		try {
			props = PropertiesLoaderUtils
					.loadAllProperties("configuration.properties");

			// Prepare message using a Spring helper
			MimeMessage mimeMessage = this.mailSender.createMimeMessage();
			MimeMessageHelper message = new MimeMessageHelper(mimeMessage,
					"UTF-8");

			message.setSubject(context.getMessage("mail." + template
					+ "Subject", null, Locale.getDefault()));

			message.setFrom(props.getProperty("mail.from"));
			message.setTo(recipientEmail);

			replacements.put("baseUrl", getbaseUrl());

			String emailContent = VelocityEngineUtils.mergeTemplateIntoString(
					velocityEngine, template + ".vm", replacements);

			System.out.println(emailContent);
			message.setText(emailContent, true);

			// Send email
			this.mailSender.send(mimeMessage);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private String getbaseUrl() {
		return request.getRequestURL().toString()
				.replace(request.getServletPath(), "");
	}
}
