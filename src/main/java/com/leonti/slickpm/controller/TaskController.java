package com.leonti.slickpm.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.leonti.slickpm.domain.Comment;
import com.leonti.slickpm.domain.Task;
import com.leonti.slickpm.domain.UploadedFile;
import com.leonti.slickpm.domain.User;
import com.leonti.slickpm.domain.dto.CommentDTO;
import com.leonti.slickpm.domain.dto.TaskDTO;
import com.leonti.slickpm.domain.dto.UploadedFileDTO;
import com.leonti.slickpm.form.TaskForm;
import com.leonti.slickpm.service.CommentService;
import com.leonti.slickpm.service.PointsService;
import com.leonti.slickpm.service.ProjectService;
import com.leonti.slickpm.service.TaskService;
import com.leonti.slickpm.service.TaskTypeService;
import com.leonti.slickpm.service.UploadedFileService;
import com.leonti.slickpm.service.UserService;
import com.leonti.slickpm.validator.TaskFormValidator;

@Controller
@RequestMapping("/task")
public class TaskController {

	@Resource(name="TaskService")
	TaskService taskService;

	@Resource(name="TaskTypeService")
	TaskTypeService taskTypeService;	

	@Resource(name="PointsService")
	PointsService pointsService;	
	
	@Resource(name="ProjectService")
	ProjectService projectService;	

	@Resource(name="UserService")
	UserService userService;		
	
	@Resource(name="CommentService")
	CommentService commentService;		

	@Resource(name="UploadedFileService")
	UploadedFileService uploadedFileService;	
	
	@Autowired
	TaskFormValidator taskFormValidator;

	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	public @ResponseBody TaskDTO RESTDetails(@PathVariable("id") Integer id) {
		
		return taskService.getById(id).getDTO();
	}	
	
	@RequestMapping(value = "", method = RequestMethod.POST)
	@ResponseBody
	public TaskDTO RESTAdd(@RequestBody TaskDTO taskDTO) {
	
		Task task = new Task();
		
		task.setTitle(taskDTO.getTitle());
		task.setDescription(taskDTO.getDescription());
		task.setProject(projectService.getById(taskDTO.getProjectId()));
		taskService.save(task);
		
		return task.getDTO();
	}	

	@RequestMapping(value = "{id}", method = RequestMethod.PUT)
	@ResponseBody
	public TaskDTO RESTUpdate(
			@RequestBody TaskDTO taskDTO,
			@PathVariable("id") Integer id) {
		
		Task task = taskService.getById(taskDTO.getId());
		task.setTitle(taskDTO.getTitle());
		task.setDescription(taskDTO.getDescription());
		taskService.save(task);
		
		return taskDTO;
	}	
	
	@RequestMapping(value = "{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public void RESTDelete(@PathVariable("id") Integer id) {
		
		taskService.delete(taskService.getById(id));
	}
	
	@RequestMapping(value = "{taskId}/comment", method = RequestMethod.GET)
	public @ResponseBody List<CommentDTO> RESTCommentList(
			@PathVariable("taskId") Integer taskId) {
		
		List<CommentDTO> comments = new ArrayList<CommentDTO>();
		for (Comment comment : commentService.getList(taskId)) {
			comments.add(comment.getDTO());
		}
		
		return comments;
	}
	
	@RequestMapping(value = "{taskId}/comment/{id}", method = RequestMethod.GET)
	public @ResponseBody CommentDTO RESTGetComment(
			@PathVariable("id") Integer id) {
		return commentService.getById(id).getDTO();
	}	
	
	@RequestMapping(value = "{taskId}/comment", method = RequestMethod.POST)
	@ResponseBody
	public CommentDTO RESTAddComment(
			@PathVariable("taskId") Integer taskId,
			@RequestBody CommentDTO commentDTO) {
	
		Comment comment = new Comment();
		
		comment.setContent(commentDTO.getContent());
		comment.setDate(new Date());
		comment.setTask(taskService.getById(taskId));
		commentService.save(comment);
		
		return comment.getDTO();
	}	

	@RequestMapping(value = "{taskId}/comment/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public CommentDTO RESTUpdateComment(
			@PathVariable("taskId") Integer taskId,
			@RequestBody CommentDTO commentDTO,
			@PathVariable("id") Integer id) {

		Comment comment = commentService.getById(id);
		
		comment.setContent(commentDTO.getContent());
		commentService.save(comment);
		
		return comment.getDTO();		
	}	
	
	@RequestMapping(value = "{taskId}/comment/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public void RESTDeleteComment(@PathVariable("id") Integer id) {
		
		commentService.delete(commentService.getById(id));
	}
	
	@RequestMapping(value = "{taskId}/file", method = RequestMethod.GET)
	public @ResponseBody List<UploadedFileDTO> RESTFiletList(
			@PathVariable("taskId") Integer taskId) {
		
		Task task = taskService.getById(taskId);
		List<UploadedFileDTO> files = new ArrayList<UploadedFileDTO>();
		for (UploadedFile uploadedFile : task.getFiles()) {
			files.add(uploadedFile.getDTO());
		}
		
		return files;
	}
	
	@RequestMapping(value = "{taskId}/addFile", method = RequestMethod.POST)
	public @ResponseBody Map<String, String> addFile(
			@PathVariable("taskId") Integer taskId,
			@RequestParam(value="id", required=true) Integer id) {
		
		taskService.addUploadedFile(taskService.getById(taskId), uploadedFileService.getById(id));
		
    	Map<String, String> result = new HashMap<String, String>();
    	result.put("result", "OK");
    	
    	return result;		
	}	
		
	@RequestMapping(value = "{taskId}/dependencyCandidates", method = RequestMethod.GET)
	public @ResponseBody List<TaskDTO> dependencyCandidates(
			@PathVariable("taskId") Integer taskId) {
		
		List<TaskDTO> tasks = new ArrayList<TaskDTO>();      	
		
		for (Task task : taskService.getDependencyCandidates(taskService.getById(taskId))) {
			tasks.add(task.getDTO());
		}
		
		return tasks;
	}	

	@RequestMapping(value = "{taskId}/dependency", method = RequestMethod.GET)
	public @ResponseBody List<TaskDTO> dependencyList(
			@PathVariable("taskId") Integer taskId) {
		
		List<TaskDTO> tasks = new ArrayList<TaskDTO>();      	
		
		for (Task task : taskService.getById(taskId).getDependsOn()) {
			tasks.add(task.getDTO());
		}
		
		return tasks;
	}		

	@RequestMapping(value = "{taskId}/addDependency", method = RequestMethod.POST)
	public @ResponseBody Map<String, String> addDependency(
			@PathVariable("taskId") Integer taskId,
			@RequestParam(value="dependencyId", required=true) Integer dependencyId) {
		
		taskService.addDependency(taskService.getById(taskId), taskService.getById(dependencyId));
		
    	Map<String, String> result = new HashMap<String, String>();
    	result.put("result", "OK");
    	
    	return result;		
	}		

	@RequestMapping(value = "{taskId}/removeDependency", method = RequestMethod.POST)
	public @ResponseBody Map<String, String> removeDependency(
			@PathVariable("taskId") Integer taskId,
			@RequestParam(value="dependencyId", required=true) Integer dependencyId) {
		
		taskService.removeDependency(taskService.getById(taskId), taskService.getById(dependencyId));
		
    	Map<String, String> result = new HashMap<String, String>();
    	result.put("result", "OK");
    	
    	return result;		
	}		
	
	/* pre backbone code */
		

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addPost(@RequestParam(value="projectId", required=true) Integer projectId,
		@ModelAttribute("taskForm") TaskForm taskForm, 
		Model model,
		BindingResult result) {
    	   	
    	taskFormValidator.validate(taskForm, result); 
        if (result.hasErrors()) { 
        	return "task/add"; 
        } 
        
        taskService.save(taskForm.getTask(
        		projectService.getById(projectId), 
        		taskTypeService.getById(taskForm.getTaskTypeId())));   	
		
    	return "redirect:list?projectId=" + projectId;
	}
    
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String edit(@RequestParam(value="id", required=true) Integer id,
    							Model model) {
    	
    	Task task = taskService.getById(id);
    	model.addAttribute("taskTypeList", taskTypeService.getList());
    	model.addAttribute("pointList", pointsService.getList());
    	model.addAttribute("userList", userService.getList());
    	model.addAttribute("task", task);
		model.addAttribute("taskForm", new TaskForm(task));
		model.addAttribute("taskList", taskService.getDependencyCandidates(task));
		
		return "task/edit";
    }    
	
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String editPost(@RequestParam(value="id", required=true) Integer id,
    					@ModelAttribute("taskForm") TaskForm taskForm, 
    					Model model,
    					BindingResult result) {
    	
    	taskFormValidator.validate(taskForm, result); 
        if (result.hasErrors()) { 
        	return "task/edit"; 
        } 

        Task task = taskService.getById(id);
        task.setTitle(taskForm.getTitle());
        task.setDescription(taskForm.getDescription());
        task.setTaskType(taskTypeService.getById(taskForm.getTaskTypeId()));
        
        if (taskForm.getPointsId() > 1) {
        	task.setPoints(pointsService.getById(taskForm.getPointsId()));
        } else {
        	task.setPoints(null);
        }
        
        taskService.save(task);   	
		
    	return "redirect:list?projectId=" + task.getProject().getId();
	}
    
	@RequestMapping(value="/updateField", method=RequestMethod.POST)
	public @ResponseBody Map<String, String> updateField(
			@RequestParam(value="id", required=true) Integer id,
			@RequestParam(value="field", required=true) String field,
			@RequestParam(value="value", required=true) String value) {
		
		Task task = taskService.getById(id);
		
		if (field.equals("title")) {
			task.setTitle(value);
		} else if (field.equals("description")) {
			task.setDescription(value);
		}
		
		taskService.save(task); 
		
		Map<String, String> result = new HashMap<String, String>();  	
    	result.put("result", "OK");   	
    	return result;		
	}    

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String delete(@RequestParam(value="id", required=true) Integer id,
    							Model model) {
		
		model.addAttribute("messageCode", "task.deleteConfirmation");
		
		Task task = taskService.getById(id);
		model.addAttribute("backUrl", "/task/list?projectId=" + task.getProject().getId());
		
		return "confirmation";
    }    
	
    
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String deletePost(@RequestParam(value="id", required=true) Integer id,
    					Model model) { 

        Task task = taskService.getById(id);
        Integer projectId = task.getProject().getId();
        
        taskService.delete(task);   	
		       
    	return "redirect:list?projectId=" + projectId;
	}
 
    @RequestMapping(value = "/details", method = RequestMethod.GET)
    public String details(@RequestParam(value="id", required=true) Integer id,
    							Model model) {
    	
    	Task task = taskService.getById(id);
    	
    	List<User> userList = userService.getList();   	    	
		Map<User, String> avatars = new HashMap<User, String>();	
		
		for (User user : userList) {
			avatars.put(user, uploadedFileService.getUserAvatar(user));
		}
		
		userList.remove(task.getUser());
	
		List<Task> dependencyCandidates = taskService.getDependencyCandidates(task);
		Map<Task, String> dependencyAvatars = new HashMap<Task, String>();	
		for (Task dependencyTask : dependencyCandidates) {
			dependencyAvatars.put(dependencyTask, uploadedFileService.getTaskAvatar(dependencyTask));
		}		
		
		for (Task dependencyTask : task.getDependsOn()) {
			dependencyAvatars.put(dependencyTask, uploadedFileService.getTaskAvatar(dependencyTask));
		}
		
    	model.addAttribute("task", task);
    	model.addAttribute("userList", userList);
		model.addAttribute("avatars", avatars);
		model.addAttribute("dependencyCandidates", dependencyCandidates);
		model.addAttribute("dependencyAvatars", dependencyAvatars); 
    	
		return "task/details";
    }      
    
    @RequestMapping(value = "/addComment", method = RequestMethod.POST)
    public @ResponseBody Map<String, String> addComment(
    		@RequestParam(value="id", required=true) Integer id,
    		@RequestParam(value="comment", required=true) String comment,
			Model model) {

    	Task task = taskService.getById(id);
		taskService.addComment(task, commentService.save(new Comment(comment, task)));  	
    	
		Map<String, String> result = new HashMap<String, String>();  	
    	result.put("result", "OK");   	
    	return result;
    }

    @RequestMapping(value = "/assignToUser", method = RequestMethod.POST)
    public @ResponseBody Map<String, String>  assignToUser(
    		@RequestParam(value="id", required=true) Integer id,
    		@RequestParam(value="userId", required=true) Integer userId,
			Model model) {

    	Task task = taskService.getById(id); 	
    	task.setUser(userService.getById(userId));
		taskService.save(task);  	
    	
		Map<String, String> result = new HashMap<String, String>();  	
    	result.put("result", "OK");   	
    	return result;
    } 
    
    @RequestMapping(value = "/addDependency", method = RequestMethod.POST)
    public @ResponseBody Map<String, String>  addDependency(
    		@RequestParam(value="id", required=true) Integer id,
    		@RequestParam(value="taskId", required=true) Integer taskId,
			Model model) {
    	
		taskService.addDependency(taskService.getById(id), taskService.getById(taskId));
    	
		Map<String, String> result = new HashMap<String, String>();  	
    	result.put("result", "OK");   	
    	return result;
    }    

    @RequestMapping(value = "/removeDependency", method = RequestMethod.POST)
    public @ResponseBody Map<String, String>  removeDependency(
    		@RequestParam(value="id", required=true) Integer id,
    		@RequestParam(value="taskId", required=true) Integer taskId,
			Model model) {
    	
		taskService.removeDependency(taskService.getById(id), taskService.getById(taskId));
    	
		Map<String, String> result = new HashMap<String, String>();  	
    	result.put("result", "OK");   	
    	return result;
    }     
}
