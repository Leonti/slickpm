package com.leonti.slickpm.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.leonti.slickpm.domain.TaskType;
import com.leonti.slickpm.domain.dto.TaskTypeDTO;
import com.leonti.slickpm.service.TaskTypeService;

@Controller
@Secured("ROLE_USER")
@RequestMapping("/taskType")
public class TaskTypeController {

	@Resource(name = "TaskTypeService")
	TaskTypeService taskTypeService;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public @ResponseBody
	List<TaskTypeDTO> RESTList(Model model) {

		List<TaskTypeDTO> taskTypeDTOList = new ArrayList<TaskTypeDTO>();

		for (TaskType taskType : taskTypeService.getList()) {
			taskTypeDTOList.add(taskType.getDTO());
		}

		return taskTypeDTOList;
	}
}
