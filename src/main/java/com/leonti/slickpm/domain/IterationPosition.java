package com.leonti.slickpm.domain;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
public class IterationPosition extends Position {
	
	@OneToOne
	private Task task;
	
	public IterationPosition() {}
	
	public IterationPosition(Task task, Integer position) {
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
