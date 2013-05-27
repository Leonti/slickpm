package com.leonti.slickpm.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.leonti.slickpm.domain.StagePosition;
import com.leonti.slickpm.domain.TaskStage;
import com.leonti.slickpm.domain.dto.TaskStageDTO;
import com.leonti.slickpm.service.PositionService;
import com.leonti.slickpm.service.ProjectService;
import com.leonti.slickpm.service.TaskStageService;

@Controller
@Secured("ROLE_USER")
@RequestMapping("/taskstage")
public class TaskStageController {

	@Resource(name = "TaskStageService")
	TaskStageService taskStageService;

	@Resource(name = "ProjectService")
	ProjectService projectService;

	@Resource(name = "PositionService")
	PositionService positionService;

	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	@ResponseBody
	public TaskStageDTO RESTDetails(@PathVariable("id") Integer id) {

		return taskStageService.getById(id).getDTO();
	}

	@RequestMapping(value = "", method = RequestMethod.POST)
	@ResponseBody
	public TaskStageDTO RESTAdd(@RequestBody TaskStageDTO taskStageDTO) {

		TaskStage taskStage = new TaskStage();
		taskStage.setTitle(taskStageDTO.getTitle());
		taskStage.setDescription(taskStageDTO.getDescription());

		taskStageService.save(taskStage);

		return taskStage.getDTO();
	}

	@RequestMapping(value = "{id}", method = RequestMethod.PUT)
	@ResponseBody
	public TaskStageDTO RESTUpdate(@RequestBody TaskStageDTO taskStageDTO,
			@PathVariable("id") Integer id) {

		TaskStage taskStage = taskStageService.getById(id);
		taskStage.setTitle(taskStageDTO.getTitle());
		taskStage.setDescription(taskStageDTO.getDescription());
		taskStageService.save(taskStage);

		return taskStage.getDTO();
	}

	@RequestMapping(value = "{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public void RESTDelete(@PathVariable("id") Integer id) {

		TaskStage taskStage = taskStageService.getById(id);
		positionService.removeStagePositions(taskStage);
		taskStageService.delete(taskStage);
	}

	@RequestMapping(value = "", method = RequestMethod.GET)
	public @ResponseBody
	List<TaskStageDTO> RESTTaskStageList() {

		List<TaskStageDTO> taskStageDTOList = new ArrayList<TaskStageDTO>();

		List<StagePosition> positions = positionService
				.getStagePositions(taskStageService.getList());

		for (StagePosition stagePosition : positions) {
			taskStageDTOList.add(stagePosition.getTaskStage().getDTO());
		}

		return taskStageDTOList;
	}

	@RequestMapping(value = "/updateOrder", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, String> updateStages(
			@RequestParam(value = "idList", required = true) String idList) {

		if (idList.length() > 0) {

			int positionCount = 0;
			for (String id : idList.split(",")) {

				StagePosition position = positionService
						.getOrCreateStagePosition(taskStageService
								.getById(Integer.parseInt(id)));
				position.setPosition(positionCount);
				positionService.save(position);
				positionCount++;
			}
		}

		Map<String, String> result = new HashMap<String, String>();
		result.put("result", "OK");

		return result;
	}
}
