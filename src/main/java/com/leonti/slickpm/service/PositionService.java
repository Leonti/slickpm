package com.leonti.slickpm.service;

import java.util.List;

import com.leonti.slickpm.domain.BacklogPosition;
import com.leonti.slickpm.domain.IterationPosition;
import com.leonti.slickpm.domain.Position;
import com.leonti.slickpm.domain.StagePosition;
import com.leonti.slickpm.domain.Task;
import com.leonti.slickpm.domain.TaskStage;
import com.leonti.slickpm.domain.TaskStagePosition;

public interface PositionService {

	Position save(Position position);

	void delete(Position position);

	void removeTaskPositions(Task task);

	void removeStagePositions(TaskStage taskStage);

	List<BacklogPosition> getBacklogPositions(List<Task> tasks);

	BacklogPosition getOrCreateBacklogPosition(Task task);

	List<IterationPosition> getIterationPositions(List<Task> tasks);

	IterationPosition getOrCreateIterationPosition(Task task);

	List<TaskStagePosition> getTaskStagePositions(List<Task> tasks);

	TaskStagePosition getOrCreateTaskStagePosition(Task task);

	List<StagePosition> getStagePositions(List<TaskStage> taskStages);

	StagePosition getOrCreateStagePosition(TaskStage taskStage);

}