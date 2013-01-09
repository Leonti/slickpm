package com.leonti.slickpm.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.leonti.slickpm.domain.UploadedFile;
import com.leonti.slickpm.domain.User;
import com.leonti.slickpm.domain.dto.UserDTO;
import com.leonti.slickpm.service.UploadedFileService;
import com.leonti.slickpm.service.UserService;

@Controller
@Secured("ROLE_USER")
@RequestMapping("/user")
public class UserController {

	@Resource(name="UserService")
	UserService userService;

	@Resource(name="UploadedFileService")
	UploadedFileService uploadedFileService;	

	@RequestMapping(value = "", method = RequestMethod.GET)
	public @ResponseBody List<UserDTO> RESTList(Model model) {
		
		List<UserDTO> users = new ArrayList<UserDTO> ();
		
		for (User user: userService.getList()) {
			users.add(user.getDTO());
		}
		
		return users;
	}	

	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	public @ResponseBody UserDTO RESTDetails(@PathVariable("id") Integer id) {
		
		return userService.getById(id).getDTO();
	}	

	
	@RequestMapping(value = "", method = RequestMethod.POST)
	@ResponseBody
	public UserDTO RESTAdd(@RequestBody UserDTO userDTO) {
		
		User user = new User();
		
		user.setName(userDTO.getName());		
		if (userDTO.getAvatarId() != null) {
			UploadedFile avatar = uploadedFileService.getById(userDTO.getAvatarId());
			user.setAvatar(avatar);
		}		
		userService.save(user);
		
		return user.getDTO();
	}	

	@RequestMapping(value = "{id}", method = RequestMethod.PUT)
	@ResponseBody
	public UserDTO RESTUpdate(
			@RequestBody UserDTO userDTO,
			@PathVariable("id") Integer id) {
		
		User user = userService.getById(id);
		user.setName(userDTO.getName());		
		if (userDTO.getAvatarId() != null) {
			UploadedFile avatar = uploadedFileService.getById(userDTO.getAvatarId());
			user.setAvatar(avatar);
		}		
		userService.save(user);		
		
		return user.getDTO();
	}	
	
	@RequestMapping(value = "{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public void RESTDelete(@PathVariable("id") Integer id) {
		
		userService.delete(userService.getById(id));
	}	
}
