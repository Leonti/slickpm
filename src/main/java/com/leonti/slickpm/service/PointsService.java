package com.leonti.slickpm.service;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leonti.slickpm.domain.Points;

@Service("PointsService")
@Transactional
public class PointsService {

    @Autowired
    private SessionFactory sessionFactory;	
	
    @SuppressWarnings("unchecked")
	public List<Points> getList() {
    	
    	return (ArrayList<Points>) sessionFactory.getCurrentSession()
    			.createQuery("FROM Points").list();
	}
    
	public Points getById(Integer id) {

    	return (Points) sessionFactory.getCurrentSession()
    			.createQuery("FROM Points WHERE id = ?")
    			.setLong(0, id)
    			.setMaxResults(1)
    			.uniqueResult();
	}	
}
