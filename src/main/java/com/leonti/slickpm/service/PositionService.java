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
import com.leonti.slickpm.domain.Task;
import com.leonti.slickpm.domain.TaskStagePosition;

@Service("PositionService")
@Transactional
public class PositionService {

    @Autowired
    private SessionFactory sessionFactory;	
    
	public Position save(Position position) {
		sessionFactory.getCurrentSession().saveOrUpdate(position);
		return position;
	}	
    
    public void delete(Position position) {
    	sessionFactory.getCurrentSession().delete(position);
    }
    
    public List<BacklogPosition> getBacklogPositions(List<Task> tasks) {

    	List<BacklogPosition> positions = new ArrayList<BacklogPosition>();
    	for (Task task : tasks) {    		
    		positions.add(getOrCreateBacklogPosition(task));
    	}    	  	
    	Collections.sort(positions);
    	
    	return positions;
    }
    
    public BacklogPosition getOrCreateBacklogPosition(Task task) {
    	if (getBacklogPosition(task) == null) {
			BacklogPosition position = new BacklogPosition(task, getNextBacklogPosition());
			save(position);    		
    	}
    	
    	return getBacklogPosition(task);
    }
 
    private BacklogPosition getBacklogPosition(Task task) {
    	
    	return (BacklogPosition) sessionFactory.getCurrentSession()
    			.createQuery("FROM BacklogPosition WHERE task = ?")
    			.setEntity(0, task)
    			.setMaxResults(1)
    			.uniqueResult();    	
    }
    
    private Integer getNextBacklogPosition() {
    	
    	BacklogPosition position = (BacklogPosition) sessionFactory.getCurrentSession()
    			.createQuery("FROM BacklogPosition ORDER BY position DESC")
    			.setMaxResults(1)
    			.uniqueResult();
    	
    	if (position != null) {
    		return position.getPosition() + 1;
    	}
    		
    	return 0;	
    }       
    
    public List<IterationPosition> getIterationPositions(List<Task> tasks) {

    	List<IterationPosition> positions = new ArrayList<IterationPosition>();
    	for (Task task : tasks) {    		
    		positions.add(getOrCreateIterationPosition(task));
    	}    	  	
    	Collections.sort(positions);
    	
    	return positions;
    }

    public IterationPosition getOrCreateIterationPosition(Task task) {
    	if (getIterationPosition(task) == null) {
			IterationPosition position = new IterationPosition(task, getNextIterationPosition(task.getIteration()));
			save(position);    		
    	}
    	
    	return getIterationPosition(task);
    } 
    
    private IterationPosition getIterationPosition(Task task) {
    	
    	return (IterationPosition) sessionFactory.getCurrentSession()
    			.createQuery("FROM IterationPosition WHERE task = ?")
    			.setEntity(0, task)
    			.setMaxResults(1)
    			.uniqueResult();    	
    }     
    
    private Integer getNextIterationPosition(Iteration iteration) {
    	
    	IterationPosition position = (IterationPosition) sessionFactory.getCurrentSession()
    			.createQuery("FROM IterationPosition WHERE task.iteration = ? ORDER BY position DESC")
    			.setEntity(0, iteration)
    			.setMaxResults(1)
    			.uniqueResult();
    	
    	if (position != null) {
    		return position.getPosition() + 1;
    	}
    		
    	return 0;	
    }
    
    public List<TaskStagePosition> getTaskStagePositions(List<Task> tasks) {

    	List<TaskStagePosition> positions = new ArrayList<TaskStagePosition>();
    	for (Task task : tasks) {    		
    		positions.add(getOrCreateTaskStagePosition(task));
    	}    	  	
    	Collections.sort(positions);
    	
    	return positions;
    }
    
    public TaskStagePosition getOrCreateTaskStagePosition(Task task) {
    	if (getTaskStagePosition(task) == null) {
			TaskStagePosition position = new TaskStagePosition(task, getNextTaskStagePosition());
			save(position);    		
    	}
    	
    	return getTaskStagePosition(task);
    }
 
    private TaskStagePosition getTaskStagePosition(Task task) {
    	
    	return (TaskStagePosition) sessionFactory.getCurrentSession()
    			.createQuery("FROM TaskStagePosition WHERE task = ?")
    			.setEntity(0, task)
    			.setMaxResults(1)
    			.uniqueResult();    	
    }
    
    private Integer getNextTaskStagePosition() {
    	
    	TaskStagePosition position = (TaskStagePosition) sessionFactory.getCurrentSession()
    			.createQuery("FROM TaskStagePosition ORDER BY position DESC")
    			.setMaxResults(1)
    			.uniqueResult();
    	
    	if (position != null) {
    		return position.getPosition() + 1;
    	}
    		
    	return 0;	
    }       
    
}
