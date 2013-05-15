package com.leonti.slickpm.service;

import java.util.List;

import com.leonti.slickpm.domain.Iteration;
import com.leonti.slickpm.domain.Task;

public interface IterationService {

	Iteration save(Iteration iteration);

	void delete(Iteration iteration);

	List<Iteration> getList(Integer projectId);

	Iteration getById(Integer id);

	void addTask(Iteration iteration, Task task);

	void removeTask(Iteration iteration, Task task);

	Double getPlannedPoints(Iteration iteration);

	Double getDonePoints(Iteration iteration);

}