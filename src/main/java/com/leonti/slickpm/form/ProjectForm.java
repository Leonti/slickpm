package com.leonti.slickpm.form;

import com.leonti.slickpm.domain.Project;

public class ProjectForm {

	private String title;
	private String description;
	
	public ProjectForm() {}
	
	public ProjectForm(Project project) {
		this.title = project.getTitle();
		this.description = project.getDescription(); 
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
	
	public Project getProject() {
		return new Project(title, description);
	}
}
