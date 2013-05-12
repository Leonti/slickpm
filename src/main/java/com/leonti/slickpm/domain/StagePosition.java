package com.leonti.slickpm.domain;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
public class StagePosition extends Position {

	@OneToOne
	private TaskStage taskStage;

	public StagePosition() {
	}

	public StagePosition(TaskStage taskStage, Integer position) {
		this.taskStage = taskStage;
		setPosition(position);
	}

	public TaskStage getTaskStage() {
		return taskStage;
	}

	public void setTaskStage(TaskStage taskStage) {
		this.taskStage = taskStage;
	}
}
