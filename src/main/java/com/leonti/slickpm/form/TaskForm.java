package com.leonti.slickpm.form;

import com.leonti.slickpm.domain.Project;
import com.leonti.slickpm.domain.Task;
import com.leonti.slickpm.domain.TaskType;

public class TaskForm {

	private String title;
	private String description;
	
	private Integer taskTypeId;
	
	public TaskForm() {}
	
	public TaskForm(Task task) {
		this.title = task.getTitle();
		this.description = task.getDescription();
		this.taskTypeId = task.getTaskType().getId();
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}	
	public Integer getTaskTypeId() {
		return taskTypeId;
	}
	public void setTaskTypeId(Integer taskTypeId) {
		this.taskTypeId = taskTypeId;
	}

	public Task getTask(Project project, TaskType taskType) {
		return new Task(title, description, taskType, project);
	}
}
