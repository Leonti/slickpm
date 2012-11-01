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

@Service("CommentService")
@Transactional
public class CommentService {

    @Autowired
    private SessionFactory sessionFactory;	

	@Resource(name="TaskService")
	TaskService taskService;    
    
	public Comment save(Comment comment) {
		sessionFactory.getCurrentSession().saveOrUpdate(comment);
		return comment;
	}	
    
    public void delete(Comment comment) {
    	sessionFactory.getCurrentSession().delete(comment);
    }
    
    @SuppressWarnings("unchecked")
	public List<Task> getList(Integer taskId) {
    	
    	return (ArrayList<Task>) sessionFactory.getCurrentSession()
    			.createQuery("FROM Comment WHERE task = ?")
    			.setEntity(0, taskService.getById(taskId)).list();
	}     
    
	public Task getById(Integer id) {

    	return (Task) sessionFactory.getCurrentSession()
    			.createQuery("FROM Comment WHERE id = ?")
    			.setLong(0, id)
    			.setMaxResults(1)
    			.uniqueResult();
	}     
}