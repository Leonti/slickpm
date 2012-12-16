package com.leonti.slickpm.service;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leonti.slickpm.domain.Task;
import com.leonti.slickpm.domain.User;

@Service("UserService")
@Transactional
public class UserService {

    @Autowired
    private SessionFactory sessionFactory;	

	public User save(User user) {
		sessionFactory.getCurrentSession().saveOrUpdate(user);
		return user;
	}	
    
    public void delete(User user) {
    	sessionFactory.getCurrentSession().delete(user);
    }    
    
    @SuppressWarnings("unchecked")
	public List<User> getList() {
    	
    	return (ArrayList<User>) sessionFactory.getCurrentSession()
    			.createQuery("FROM User").list();
	}
    
	public User getById(Integer id) {

    	return (User) sessionFactory.getCurrentSession()
    			.createQuery("FROM User WHERE id = ?")
    			.setLong(0, id)
    			.setMaxResults(1)
    			.uniqueResult();
	}	
}