package com.leonti.slickpm.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leonti.slickpm.domain.Project;
import com.leonti.slickpm.domain.Task;
import com.leonti.slickpm.domain.User;

@Service("ProjectService")
@Transactional
public class ProjectService {

	@Autowired
	private SessionFactory sessionFactory;

	@Resource(name = "TaskService")
	TaskService taskService;

	public Project save(Project project) {
		sessionFactory.getCurrentSession().saveOrUpdate(project);
		return project;
	}

	public void delete(Project project) {
		sessionFactory.getCurrentSession().delete(project);
	}

	@SuppressWarnings("unchecked")
	public List<Project> getList() {

		return (ArrayList<Project>) sessionFactory.getCurrentSession()
				.createQuery("FROM Project").list();
	}

	public List<Project> getListForUser(User user) {

		Set<Project> userProjects = new HashSet<Project>();

		for (Task task : taskService.getListForUser(user)) {
			userProjects.add(task.getProject());
		}

		return new ArrayList<Project>(userProjects);
	}

	public Project getById(Integer id) {

		return (Project) sessionFactory.getCurrentSession()
				.createQuery("FROM Project p WHERE p.id = ?").setLong(0, id)
				.setMaxResults(1).uniqueResult();
	}
}
