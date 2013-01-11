package com.leonti.slickpm.service;

import java.util.List;
import java.util.Random;

import javax.annotation.Resource;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leonti.slickpm.domain.Comment;
import com.leonti.slickpm.domain.GitVcs;
import com.leonti.slickpm.domain.Iteration;
import com.leonti.slickpm.domain.Project;
import com.leonti.slickpm.domain.Task;
import com.leonti.slickpm.domain.TaskStage;
import com.leonti.slickpm.domain.TaskType;
import com.leonti.slickpm.domain.User;

@Service("DemoService")
@Transactional
public class DemoService {
	
	@Resource(name="TaskTypeService")
	TaskTypeService taskTypeService;	

	@Resource(name="UserService")
	UserService userService;	

	@Resource(name="ProjectService")
	ProjectService projectService;		

	@Resource(name="IterationService")
	IterationService iterationService;	

	@Resource(name="TaskStageService")
	TaskStageService taskStageService;		
	
	@Resource(name="TaskService")
	TaskService taskService;		

	@Resource(name="VcsService")
	VcsService vcsService;	
	
	@Autowired
	PasswordEncoder passwordEncoder;	
	
	public void populateDb() {
		
		if (taskTypeService.getList().size() == 0) {
			createTaskTypes();
			createTaskStages();
			createUsers();
			createProjects();
			createIterations();
			createTasks();			
		}
	}
	
	private void createTaskTypes() {
		String[] taskTypes = {"User story", "Bug", "Technical Task"};
		
		for (String type : taskTypes) {
			TaskType taskType = new TaskType();
			taskType.setTitle(type);
			
			taskTypeService.save(taskType);
		}		
	}
	
	private void createTaskStages() {
		String[] taskStages = {"To Do", "Doing", "Code Review", "Testing", "Done"};
		
		for (String stage : taskStages) {
			TaskStage taskStage = new TaskStage(stage, "");
			
			taskStageService.save(taskStage);
		}		
	}
	
	private void createUsers() {
		String[] users = {"demo", "demo1", "demo2"};
		String password = passwordEncoder.encodePassword("demo", null);
		
		for (String username : users) {
			User user = new User(username, username + "@example.com", password);
			userService.save(user);
		}
	}
	
	private void createProjects() {
		
		Project project = new Project("Demo project 1", "Demo project 1 description");
		
		GitVcs gitVcs = new GitVcs();
		gitVcs.setUri("https://leonti@bitbucket.org/leonti/test.git");
		vcsService.save(gitVcs);
		vcsService.checkAndCreateVcsCache(gitVcs);
		
		project.setVcs(gitVcs);		
		
		projectService.save(project);
		
		project = new Project("Demo project 2", "Demo project 2 description");
		
		project.setVcs(gitVcs);		
		projectService.save(project);		
	}
	
	private void createIterations() {
		
		List<Project> projects = projectService.getList();
		
		DateTime start = new DateTime();
		
		DateTime end = start.plusWeeks(2);
		
		Iteration iteration = new Iteration("First iteration", "Iteration description", projects.get(0));
		iteration.setStartDate(start.toDate());
		iteration.setEndDate(end.toDate());
		iterationService.save(iteration);
		
		iteration = new Iteration("Second iteration", "Iteration description", projects.get(0));
		start = start.plusWeeks(2);
		end = end.plusWeeks(2);		
		iteration.setStartDate(start.toDate());
		iteration.setEndDate(end.toDate());
		iterationService.save(iteration);

		iteration = new Iteration("Third iteration", "Iteration description", projects.get(0));
		start = start.plusWeeks(2);
		end = end.plusWeeks(2);
		iteration.setStartDate(start.toDate());
		iteration.setEndDate(end.toDate());
		iterationService.save(iteration);		
		
		iteration = new Iteration("Iteration 1", "Iteration description", projects.get(1));
		start = start.plusWeeks(2);
		end = end.plusWeeks(2);
		iteration.setStartDate(start.toDate());
		iteration.setEndDate(end.toDate());		
		iterationService.save(iteration);		
	}
	
	
	private void createTasks() {

		List<Project> projects = projectService.getList();
		List<TaskType> types = taskTypeService.getList();
		List<Iteration> iterations = iterationService.getList(projects.get(0).getId());
		iterations.addAll(iterationService.getList(projects.get(1).getId()));
		
		List<TaskStage> stages = taskStageService.getList();
		
		Double[] points = {0.5, 1d, 2d, 3d, 5d, 8d};
		
		Random random = new Random();
		
		for (int i = 0; i < 40; i++) {
			Task task = new Task(
					"Task " + random.nextInt(10), 
					"Task description " + random.nextInt(10), 
					types.get(random.nextInt(3)),
					projects.get(random.nextInt(2)));
			
			int iterationPosition = random.nextInt(6);
			if (iterationPosition < 3) {
				task.setIteration(iterations.get(iterationPosition));
			}
			
			task.setTaskStage(stages.get(0));
			
			task.setPoints(points[random.nextInt(5)]);
			
			if (random.nextInt(2) == 1) {
				task.setTaskStage(stages.get(stages.size() - 1));
			}
			
			taskService.save(task);	
			
			taskService.addComment(task, new Comment("Just random comment " + random.nextInt(100), task));
			taskService.addComment(task, new Comment("Another random comment " + random.nextInt(100), task));
		}		
	}
}
