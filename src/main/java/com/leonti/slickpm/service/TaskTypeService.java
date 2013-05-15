package com.leonti.slickpm.service;

import java.util.List;

import com.leonti.slickpm.domain.TaskType;

public interface TaskTypeService {

	TaskType save(TaskType taskType);

	void delete(TaskType taskType);

	List<TaskType> getList();

	TaskType getById(Integer id);

}