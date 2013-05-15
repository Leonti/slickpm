package com.leonti.slickpm.service;

import java.util.List;

import com.leonti.slickpm.domain.Project;
import com.leonti.slickpm.domain.User;

public interface ProjectService {
	
	Project save(Project project);

	public void delete(Project project);

	public List<Project> getList();

	public List<Project> getListForUser(User user);

	public Project getById(Integer id);
}
