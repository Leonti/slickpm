package com.leonti.slickpm.service;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leonti.slickpm.domain.TaskType;

@Service("TaskTypeService")
@Transactional
public class TaskTypeService {

    @Autowired
    private SessionFactory sessionFactory;	
    
	public TaskType save(TaskType taskType) {
		sessionFactory.getCurrentSession().saveOrUpdate(taskType);
		return taskType;
	}	
    
    public void delete(TaskType taskType) {
    	sessionFactory.getCurrentSession().delete(taskType);
    }
    
    @SuppressWarnings("unchecked")
	public List<TaskType> getList() {
    	
    	return (ArrayList<TaskType>) sessionFactory.getCurrentSession()
    			.createQuery("FROM TaskType").list();
	}
    
	public TaskType getById(Integer id) {

    	return (TaskType) sessionFactory.getCurrentSession()
    			.createQuery("FROM TaskType t WHERE t.id = ?")
    			.setLong(0, id)
    			.setMaxResults(1)
    			.uniqueResult();
	}   
}
