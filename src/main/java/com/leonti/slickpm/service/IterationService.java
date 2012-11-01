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

@Service("IterationService")
@Transactional
public class IterationService {

    @Autowired
    private SessionFactory sessionFactory;	

	@Resource(name="ProjectService")
	ProjectService projectService;    
    
	public Iteration save(Iteration iteration) {
		sessionFactory.getCurrentSession().saveOrUpdate(iteration);
		return iteration;
	}	
    
    public void delete(Iteration iteration) {
    	sessionFactory.getCurrentSession().delete(iteration);
    }
    
    @SuppressWarnings("unchecked")
	public List<Iteration> getList(Integer projectId) {
    	
    	return (ArrayList<Iteration>) sessionFactory.getCurrentSession()
    			.createQuery("FROM Iteration WHERE project = ?")
    			.setEntity(0, projectService.getById(projectId)).list();
	}   
    
	public Iteration getById(Integer id) {

    	return (Iteration) sessionFactory.getCurrentSession()
    			.createQuery("FROM Iteration i WHERE i.id = ?")
    			.setLong(0, id)
    			.setMaxResults(1)
    			.uniqueResult();
	}
	
	public void addTask(Iteration iteration, Task task) {

		task.setIteration(iteration);
		sessionFactory.getCurrentSession().saveOrUpdate(task);	
	}
	
	public void removeTask(Iteration iteration, Task task) {
//		iteration.getTasks().remove(task);
//		save(iteration);
		
		task.setIteration(null);
		sessionFactory.getCurrentSession().saveOrUpdate(task);			
	}
}
