package com.leonti.slickpm.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.leonti.slickpm.domain.BacklogPosition;
import com.leonti.slickpm.domain.GitVcs;
import com.leonti.slickpm.domain.Iteration;
import com.leonti.slickpm.domain.IterationPosition;
import com.leonti.slickpm.domain.Project;
import com.leonti.slickpm.domain.Task;
import com.leonti.slickpm.domain.dto.IterationDTO;
import com.leonti.slickpm.domain.dto.ProjectDTO;
import com.leonti.slickpm.domain.dto.TaskDTO;
import com.leonti.slickpm.service.IterationService;
import com.leonti.slickpm.service.PositionService;
import com.leonti.slickpm.service.ProjectService;
import com.leonti.slickpm.service.TaskService;
import com.leonti.slickpm.service.TaskStageService;
import com.leonti.slickpm.service.UploadedFileService;
import com.leonti.slickpm.service.VcsService;
import com.leonti.slickpm.validator.ProjectFormValidator;

@Controller
@RequestMapping("/project")
public class ProjectController {

	@Resource(name="ProjectService")
	ProjectService projectService;

	@Resource(name="IterationService")
	IterationService iterationService;	
	
	@Resource(name="TaskService")
	TaskService taskService;	
	
	@Resource(name="PositionService")
	PositionService positionService;	

	@Resource(name="TaskStageService")
	TaskStageService taskStageService;		

	@Resource(name="UploadedFileService")
	UploadedFileService uploadedFileService;	

	@Resource(name="VcsService")
	VcsService vcsService;	
	
	@Autowired
	ProjectFormValidator projectFormValidator;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public @ResponseBody List<ProjectDTO> RESTList(Model model) {
		
		List<ProjectDTO> projectDTOList = new ArrayList<ProjectDTO>();
		
		for (Project project : projectService.getList()) {
			projectDTOList.add(project.getDTO());
		}
		
		return projectDTOList;
	}	

	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	public @ResponseBody ProjectDTO RESTDetails(@PathVariable("id") Integer id) {
		
		return projectService.getById(id).getDTO();
	}	

	
	@RequestMapping(value = "", method = RequestMethod.POST)
	@ResponseBody
	public ProjectDTO RESTAdd(@RequestBody ProjectDTO projectDTO) {
		
		Project project = new Project();
		project.setTitle(projectDTO.getTitle());
		project.setDescription(projectDTO.getDescription());
		if (projectDTO.getVcs() != null) {
			GitVcs gitVcs = new GitVcs();
			gitVcs.setUri(projectDTO.getVcs().getUri());
			vcsService.save(gitVcs);
			
			vcsService.checkAndCreateVcsCache(gitVcs);
			
			project.setVcs(gitVcs);
		}
		
		projectService.save(project);
		
		return project.getDTO();
	}	

	@RequestMapping(value = "{id}", method = RequestMethod.PUT)
	@ResponseBody
	public ProjectDTO RESTUpdate(
			@RequestBody ProjectDTO projectDTO,
			@PathVariable("id") Integer id) {
		
		Project project = projectService.getById(projectDTO.getId());
		project.setTitle(projectDTO.getTitle());
		project.setDescription(projectDTO.getDescription());
		if (projectDTO.getVcs() != null) {
			GitVcs gitVcs = new GitVcs();
			gitVcs.setUri(projectDTO.getVcs().getUri());
			vcsService.save(gitVcs);
			
			// we are updating git repo - create dir for it
			if (!gitVcs.equals(project.getVcs())) {
				vcsService.checkAndCreateVcsCache(gitVcs);
			}
			
			project.setVcs(gitVcs);
		} else {
			if (project.getVcs() != null) {
				
				project.setVcs(null);
			}
		}		
		
		projectService.save(project);
		
		return projectDTO;
	}	
	
	@RequestMapping(value = "{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public void RESTDelete(@PathVariable("id") Integer id) {
		
		projectService.delete(projectService.getById(id));
	}	
	
	@RequestMapping(value = "{projectId}/iteration", method = RequestMethod.GET)
	public @ResponseBody List<IterationDTO> RESTIterationList(
			@PathVariable("projectId") Integer projectId) {
		
		List<IterationDTO> iterationDTOList = new ArrayList<IterationDTO>();
		
		for(Iteration iteration : iterationService.getList(projectId)) {
			iterationDTOList.add(iteration.getDTO());
		}
		
		return iterationDTOList;
	}
	
	@RequestMapping(value = "{projectId}/backlog", method = RequestMethod.GET)
	public @ResponseBody List<TaskDTO> RESTBacklog(
			@PathVariable("projectId") Integer projectId) {

    	List<BacklogPosition> positions = positionService.getBacklogPositions(taskService.getBacklogList(projectId));
    	List<TaskDTO> backlogTasks = new ArrayList<TaskDTO>();    	
    	
    	for (BacklogPosition position : positions) {    		
    		TaskDTO taskDTO = position.getTask().getDTO();
    		backlogTasks.add(taskDTO);    		
    	}		
		
		return backlogTasks;
	}	

	@RequestMapping(value = "user/{userId}", method = RequestMethod.GET)
	public @ResponseBody List<Project> RESTUserProjectList(
			@PathVariable("userId") Integer userId) {
		
		// TODO: implement logic to return only projects for given user
		return projectService.getList();
	}	
	
    @RequestMapping(value = "{projectId}/updateBacklog", method = RequestMethod.POST)
    public @ResponseBody Map<String, String> updateBacklog(
    		@PathVariable("projectId") Integer projectId,
    		@RequestParam(value="idList", required=true) String idList) {
    	  	
    	if (idList.length() > 0) {
    		
    		List<Task> backlogTasks = taskService.getBacklogList(projectId);  	
    		for (String id : idList.split(",")) {
    			Task task = taskService.getById(Integer.parseInt(id));
    			
    			// if task is not in backlog - remove it from iteration - it will add it to backlog
    			if (!backlogTasks.contains(task)) {
    				iterationService.removeTask(task.getIteration(), task);
    			}
    		}
    		
    		int positionCount = 0;
	    	for (String id : idList.split(",")) {
	    		
	    		BacklogPosition position = positionService.getOrCreateBacklogPosition(taskService.getById(Integer.parseInt(id)));
	    		position.setPosition(positionCount);
	    		positionService.save(position);
	    		positionCount++;
	    	}
    	}
    	
    	Map<String, String> result = new HashMap<String, String>();
    	result.put("result", "OK");
    	
    	return result;
    }
    
    @RequestMapping(value = "updateIteration/{iterationId}", method = RequestMethod.POST)
    public @ResponseBody Map<String, String> updateIteration(
    		@PathVariable("iterationId") Integer iterationId,
    		@RequestParam(value="idList", required=true) String idList) {
    	
    	if (idList.length() > 0) {

    		List<Task> iterationTasks = iterationService.getById(iterationId).getTasks();
    		for (String id : idList.split(",")) {
    			Task task = taskService.getById(Integer.parseInt(id));
    			
    			// if task is not in iteration - create new task with first stage
    			if (!iterationTasks.contains(task)) {
    		
    		    	if (taskStageService.getFirstStage() != null) {
    		    		task.setTaskStage(taskStageService.getFirstStage());
    		    		taskService.save(task);    		
    		    	}
    		    	 	
    		    	iterationService.addTask(iterationService.getById(iterationId), task); 
    			}
    		}    		
    		
    		int positionCount = 0;
	    	for (String id : idList.split(",")) {
	    		
	    		IterationPosition position = positionService.getOrCreateIterationPosition(taskService.getById(Integer.parseInt(id)));
	    		position.setPosition(positionCount);
	    		positionService.save(position);
	    		positionCount++;
	    	} 
    	}
    	   	
    	Map<String, String> result = new HashMap<String, String>();
    	result.put("result", "OK");
    	
    	return result;
    }	
  
	@RequestMapping(value = "checkVcs", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, String> checkVcs(
			@RequestParam(value="uri", required=true) String uri) {
		
		GitVcs gitVcs = new GitVcs();
		gitVcs.setUri(uri);
		
		Map<String, String> result = new HashMap<String, String>();
			
		if (vcsService.checkAndCreateVcsCache(gitVcs).equals(VcsService.VcsStatus.INVALID_URI)) {
			result.put("result", "ERROR");
			result.put("message", "Invalid uri for git repo");
			return result;
		}
		  	
    	result.put("result", "OK");
    	
    	return result;
	}    
    
}
