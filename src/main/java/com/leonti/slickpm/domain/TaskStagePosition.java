package com.leonti.slickpm.domain;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
public class TaskStagePosition extends Position {

	@OneToOne
	private Task task;
	
	public TaskStagePosition() {}
	
	public TaskStagePosition(Task task, Integer position) {
		this.task = task;
		setPosition(position);
	}
	
	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}
}
