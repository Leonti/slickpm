package com.leonti.slickpm.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leonti.slickpm.domain.BacklogPosition;
import com.leonti.slickpm.domain.Iteration;
import com.leonti.slickpm.domain.IterationPosition;
import com.leonti.slickpm.domain.Position;
import com.leonti.slickpm.domain.StagePosition;
import com.leonti.slickpm.domain.Task;
import com.leonti.slickpm.domain.TaskStage;
import com.leonti.slickpm.domain.TaskStagePosition;

@Service("PositionService")
@Transactional
public class PositionServiceImpl implements PositionService {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public Position save(Position position) {
		sessionFactory.getCurrentSession().saveOrUpdate(position);
		return position;
	}

	@Override
	public void delete(Position position) {
		sessionFactory.getCurrentSession().delete(position);
	}

	@Override
	public void removeTaskPositions(Task task) {
		sessionFactory.getCurrentSession()
				.createQuery("DELETE FROM BacklogPosition WHERE task = ?")
				.setEntity(0, task).executeUpdate();

		sessionFactory.getCurrentSession()
				.createQuery("DELETE FROM IterationPosition WHERE task = ?")
				.setEntity(0, task).executeUpdate();

		sessionFactory.getCurrentSession()
				.createQuery("DELETE FROM TaskStagePosition WHERE task = ?")
				.setEntity(0, task).executeUpdate();
	}

	@Override
	public void removeStagePositions(TaskStage taskStage) {
		sessionFactory.getCurrentSession()
				.createQuery("DELETE FROM StagePosition WHERE taskStage = ?")
				.setEntity(0, taskStage).executeUpdate();
	}

	@Override
	public List<BacklogPosition> getBacklogPositions(List<Task> tasks) {

		List<BacklogPosition> positions = new ArrayList<BacklogPosition>();
		for (Task task : tasks) {
			positions.add(getOrCreateBacklogPosition(task));
		}
		Collections.sort(positions);

		return positions;
	}

	@Override
	public BacklogPosition getOrCreateBacklogPosition(Task task) {
		if (getBacklogPosition(task) == null) {
			BacklogPosition position = new BacklogPosition(task,
					getNextBacklogPosition());
			save(position);
		}

		return getBacklogPosition(task);
	}

	private BacklogPosition getBacklogPosition(Task task) {

		return (BacklogPosition) sessionFactory.getCurrentSession()
				.createQuery("FROM BacklogPosition WHERE task = ?")
				.setEntity(0, task).setMaxResults(1).uniqueResult();
	}

	private Integer getNextBacklogPosition() {

		BacklogPosition position = (BacklogPosition) sessionFactory
				.getCurrentSession()
				.createQuery("FROM BacklogPosition ORDER BY position DESC")
				.setMaxResults(1).uniqueResult();

		if (position != null) {
			return position.getPosition() + 1;
		}

		return 0;
	}

	@Override
	public List<IterationPosition> getIterationPositions(List<Task> tasks) {

		List<IterationPosition> positions = new ArrayList<IterationPosition>();
		for (Task task : tasks) {
			positions.add(getOrCreateIterationPosition(task));
		}
		Collections.sort(positions);

		return positions;
	}

	@Override
	public IterationPosition getOrCreateIterationPosition(Task task) {
		if (getIterationPosition(task) == null) {
			IterationPosition position = new IterationPosition(task,
					getNextIterationPosition(task.getIteration()));
			save(position);
		}

		return getIterationPosition(task);
	}

	private IterationPosition getIterationPosition(Task task) {

		return (IterationPosition) sessionFactory.getCurrentSession()
				.createQuery("FROM IterationPosition WHERE task = ?")
				.setEntity(0, task).setMaxResults(1).uniqueResult();
	}

	private Integer getNextIterationPosition(Iteration iteration) {

		IterationPosition position = (IterationPosition) sessionFactory
				.getCurrentSession()
				.createQuery(
						"FROM IterationPosition WHERE task.iteration = ? ORDER BY position DESC")
				.setEntity(0, iteration).setMaxResults(1).uniqueResult();

		if (position != null) {
			return position.getPosition() + 1;
		}

		return 0;
	}

	@Override
	public List<TaskStagePosition> getTaskStagePositions(List<Task> tasks) {

		List<TaskStagePosition> positions = new ArrayList<TaskStagePosition>();
		for (Task task : tasks) {
			positions.add(getOrCreateTaskStagePosition(task));
		}
		Collections.sort(positions);

		return positions;
	}

	@Override
	public TaskStagePosition getOrCreateTaskStagePosition(Task task) {
		if (getTaskStagePosition(task) == null) {
			TaskStagePosition position = new TaskStagePosition(task,
					getNextTaskStagePosition());
			save(position);
		}

		return getTaskStagePosition(task);
	}

	private TaskStagePosition getTaskStagePosition(Task task) {

		return (TaskStagePosition) sessionFactory.getCurrentSession()
				.createQuery("FROM TaskStagePosition WHERE task = ?")
				.setEntity(0, task).setMaxResults(1).uniqueResult();
	}

	private Integer getNextTaskStagePosition() {

		TaskStagePosition position = (TaskStagePosition) sessionFactory
				.getCurrentSession()
				.createQuery("FROM TaskStagePosition ORDER BY position DESC")
				.setMaxResults(1).uniqueResult();

		if (position != null) {
			return position.getPosition() + 1;
		}

		return 0;
	}

	/* Order of stages on iteration */

	@Override
	public List<StagePosition> getStagePositions(List<TaskStage> taskStages) {

		List<StagePosition> positions = new ArrayList<StagePosition>();
		for (TaskStage taskStage : taskStages) {
			positions.add(getOrCreateStagePosition(taskStage));
		}
		Collections.sort(positions);

		return positions;
	}

	@Override
	public StagePosition getOrCreateStagePosition(TaskStage taskStage) {
		if (getStagePosition(taskStage) == null) {
			StagePosition position = new StagePosition(taskStage,
					getNextStagePosition());
			save(position);
		}

		return getStagePosition(taskStage);
	}

	private StagePosition getStagePosition(TaskStage taskStage) {

		return (StagePosition) sessionFactory.getCurrentSession()
				.createQuery("FROM StagePosition WHERE taskStage = ?")
				.setEntity(0, taskStage).setMaxResults(1).uniqueResult();
	}

	private Integer getNextStagePosition() {

		StagePosition position = (StagePosition) sessionFactory
				.getCurrentSession()
				.createQuery("FROM StagePosition ORDER BY position DESC")
				.setMaxResults(1).uniqueResult();

		if (position != null) {
			return position.getPosition() + 1;
		}

		return 0;
	}
}
