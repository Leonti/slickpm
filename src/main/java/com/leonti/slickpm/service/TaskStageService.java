package com.leonti.slickpm.service;

import java.util.List;

import com.leonti.slickpm.domain.Iteration;
import com.leonti.slickpm.domain.Task;
import com.leonti.slickpm.domain.TaskStage;

public interface TaskStageService {

	TaskStage save(TaskStage taskStage);

	void delete(TaskStage taskStage);

	List<TaskStage> getList();

	List<TaskStage> getDefaultList();

	TaskStage getById(Integer id);

	List<Task> getTasksForStage(Iteration iteration, TaskStage taskStage);

	TaskStage getFirstStage();

	TaskStage getLastStage();

}