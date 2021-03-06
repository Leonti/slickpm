package com.leonti.slickpm.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leonti.slickpm.domain.Iteration;
import com.leonti.slickpm.domain.Task;
import com.leonti.slickpm.domain.TaskStage;

@Service("TaskStageService")
@Transactional
public class TaskStageServiceImpl implements TaskStageService {

	@Autowired
	private SessionFactory sessionFactory;

	@Resource(name = "ProjectService")
	ProjectService projectService;

	@Override
	public TaskStage save(TaskStage taskStage) {
		sessionFactory.getCurrentSession().saveOrUpdate(taskStage);
		return taskStage;
	}

	@Override
	public void delete(TaskStage taskStage) {
		sessionFactory.getCurrentSession().delete(taskStage);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<TaskStage> getList() {

		return (ArrayList<TaskStage>) sessionFactory.getCurrentSession()
				.createQuery("FROM TaskStage").list();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<TaskStage> getDefaultList() {

		return (ArrayList<TaskStage>) sessionFactory.getCurrentSession()
				.createQuery("FROM TaskStage").list();
	}

	@Override
	public TaskStage getById(Integer id) {

		return (TaskStage) sessionFactory.getCurrentSession()
				.createQuery("FROM TaskStage WHERE id = ?").setLong(0, id)
				.setMaxResults(1).uniqueResult();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Task> getTasksForStage(Iteration iteration, TaskStage taskStage) {

		return (ArrayList<Task>) sessionFactory.getCurrentSession()
				.createQuery("FROM Task WHERE iteration = ? AND taskStage = ?")
				.setEntity(0, iteration).setEntity(1, taskStage).list();
	}

	@Override
	public TaskStage getFirstStage() {

		List<TaskStage> stages = getList();
		if (stages.size() > 0) {
			return stages.get(0);
		}

		return null;
	}

	@Override
	public TaskStage getLastStage() {

		List<TaskStage> stages = getList();
		if (stages.size() > 0) {
			return stages.get(stages.size() - 1);
		}

		return null;
	}
}
