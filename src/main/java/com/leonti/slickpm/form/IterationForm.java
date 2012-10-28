package com.leonti.slickpm.form;

import com.leonti.slickpm.domain.Iteration;
import com.leonti.slickpm.domain.Project;
import com.leonti.slickpm.domain.Task;

public class IterationForm {
	
	private String title;
	private String description;
	
	public IterationForm() {}
	
	public IterationForm(Iteration iteration) {
		this.title = iteration.getTitle();
		this.description = iteration.getDescription();
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

	public Iteration getIteration(Project project) {
		return new Iteration(title, description, project);
	}
}
