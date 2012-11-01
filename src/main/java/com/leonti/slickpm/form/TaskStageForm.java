package com.leonti.slickpm.form;

import com.leonti.slickpm.domain.TaskStage;

public class TaskStageForm {

	private String title;
	private String description;
	
	public TaskStageForm() {}
	
	public TaskStageForm(TaskStage taskStage) {
		this.title = taskStage.getTitle();
		this.description = taskStage.getDescription();
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
	
	public TaskStage getTaskStage() {
		return new TaskStage(this.title, this.description);
	}
}
