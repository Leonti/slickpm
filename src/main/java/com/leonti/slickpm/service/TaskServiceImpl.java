package com.leonti.slickpm.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leonti.slickpm.domain.Comment;
import com.leonti.slickpm.domain.Task;
import com.leonti.slickpm.domain.UploadedFile;
import com.leonti.slickpm.domain.User;

@Service("TaskService")
@Transactional
public class TaskServiceImpl implements TaskService {

	@Autowired
	private SessionFactory sessionFactory;

	@Resource(name = "ProjectService")
	ProjectServiceImpl projectService;

	@Resource(name = "PositionService")
	PositionService positionService;

	@Override
	public Task save(Task task) {
		sessionFactory.getCurrentSession().saveOrUpdate(task);
		return task;
	}

	@Override
	public void delete(Task task) {

		positionService.removeTaskPositions(task);
		sessionFactory.getCurrentSession().delete(task);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Task> getList(Integer projectId) {

		return (ArrayList<Task>) sessionFactory.getCurrentSession()
				.createQuery("FROM Task WHERE project = ?")
				.setEntity(0, projectService.getById(projectId)).list();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Task> getBacklogList(Integer projectId) {

		return (ArrayList<Task>) sessionFactory
				.getCurrentSession()
				.createQuery(
						"FROM Task WHERE project = ? AND iteration IS NULL")
				.setEntity(0, projectService.getById(projectId)).list();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Task> getListForUser(User user) {

		return (ArrayList<Task>) sessionFactory.getCurrentSession()
				.createQuery("FROM Task WHERE user = ?").setEntity(0, user)
				.list();
	}

	@Override
	public List<Task> getDependencyCandidates(Task task) {

		List<Task> projectTasks = getList(task.getProject().getId());
		projectTasks.remove(task);
		projectTasks.removeAll(task.getDependsOn());

		return projectTasks;
	}

	@Override
	public Task getById(Integer id) {

		return (Task) sessionFactory.getCurrentSession()
				.createQuery("FROM Task t WHERE t.id = ?").setLong(0, id)
				.setMaxResults(1).uniqueResult();
	}

	@Override
	public void addComment(Task task, Comment comment) {

		comment.setTask(task);
		sessionFactory.getCurrentSession().saveOrUpdate(comment);
	}

	@Override
	public void addDependency(Task task, Task dependency) {
		task.getDependsOn().add(dependency);
		save(task);
	}

	@Override
	public void addUploadedFile(Task task, UploadedFile uploadedFile) {
		task.getFiles().add(uploadedFile);
		save(task);
	}

	@Override
	public void removeDependency(Task task, Task dependency) {
		task.getDependsOn().remove(dependency);
		save(task);
	}
}
