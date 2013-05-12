package com.leonti.slickpm.controller;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.leonti.slickpm.domain.AuthenticatedUser;

@Controller
public class GeneralController {

	@Secured("ROLE_USER")
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Model model) throws JsonGenerationException,
			JsonMappingException, IOException {

		AuthenticatedUser authUser = (AuthenticatedUser) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();

		ObjectMapper mapper = new ObjectMapper();
		model.addAttribute("user",
				mapper.writeValueAsString(authUser.getUser().getDTO()));

		return "main";
	}

	@Secured("ROLE_USER")
	@RequestMapping(value = "/admin", method = RequestMethod.GET)
	public String backbone(Model model) throws JsonGenerationException,
			JsonMappingException, IOException {

		AuthenticatedUser authUser = (AuthenticatedUser) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();

		ObjectMapper mapper = new ObjectMapper();
		model.addAttribute("user",
				mapper.writeValueAsString(authUser.getUser().getDTO()));

		return "admin";
	}
}
