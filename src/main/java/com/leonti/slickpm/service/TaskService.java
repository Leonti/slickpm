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

@Service("TaskService")
@Transactional
public class TaskService {

    @Autowired
    private SessionFactory sessionFactory;	

	@Resource(name="ProjectService")
	ProjectService projectService;    
    
	public Task save(Task task) {
		sessionFactory.getCurrentSession().saveOrUpdate(task);
		return task;
	}	
    
    public void delete(Task task) {
    	sessionFactory.getCurrentSession().delete(task);
    }
    
    @SuppressWarnings("unchecked")
	public List<Task> getList(Integer projectId) {
    	
    	return (ArrayList<Task>) sessionFactory.getCurrentSession()
    			.createQuery("FROM Task WHERE project = ?")
    			.setEntity(0, projectService.getById(projectId)).list();
	}   

    @SuppressWarnings("unchecked")
	public List<Task> getBacklogList(Integer projectId) {
    	
    	return (ArrayList<Task>) sessionFactory.getCurrentSession()
    			.createQuery("FROM Task WHERE project = ? AND iteration IS NULL")
    			.setEntity(0, projectService.getById(projectId)).list();
	}     
    
	public Task getById(Integer id) {

    	return (Task) sessionFactory.getCurrentSession()
    			.createQuery("FROM Task t WHERE t.id = ?")
    			.setLong(0, id)
    			.setMaxResults(1)
    			.uniqueResult();
	}
	
	public void addComment(Task task, Comment comment) {

		comment.setTask(task);
		sessionFactory.getCurrentSession().saveOrUpdate(comment);	
	}	
}
