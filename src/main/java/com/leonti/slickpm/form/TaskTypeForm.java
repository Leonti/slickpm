package com.leonti.slickpm.form;

import com.leonti.slickpm.domain.TaskType;

public class TaskTypeForm {

	private String title;
	private String description;
	
	public TaskTypeForm() {}
	
	public TaskTypeForm(TaskType taskType) {
		this.title = taskType.getTitle();
		this.description = taskType.getDescription();
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
	
	public TaskType getTaskType() {
		return new TaskType(title, description);
	}
}
