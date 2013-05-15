package com.leonti.slickpm.service;

import java.util.List;

import com.leonti.slickpm.domain.Comment;
import com.leonti.slickpm.domain.Task;
import com.leonti.slickpm.domain.UploadedFile;
import com.leonti.slickpm.domain.User;

public interface TaskService {

	Task save(Task task);

	void delete(Task task);

	List<Task> getList(Integer projectId);

	List<Task> getBacklogList(Integer projectId);

	List<Task> getListForUser(User user);

	List<Task> getDependencyCandidates(Task task);

	Task getById(Integer id);

	void addComment(Task task, Comment comment);

	void addDependency(Task task, Task dependency);

	void addUploadedFile(Task task, UploadedFile uploadedFile);

	void removeDependency(Task task, Task dependency);

}