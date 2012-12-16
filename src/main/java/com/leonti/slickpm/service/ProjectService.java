package com.leonti.slickpm.service;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leonti.slickpm.domain.Project;

@Service("ProjectService")
@Transactional
public class ProjectService {

    @Autowired
    private SessionFactory sessionFactory;	
    
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
    
	public Project getById(Integer id) {

    	return (Project) sessionFactory.getCurrentSession()
    			.createQuery("FROM Project p WHERE p.id = ?")
    			.setLong(0, id)
    			.setMaxResults(1)
    			.uniqueResult();
	}     
}
