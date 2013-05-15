package com.leonti.slickpm.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leonti.slickpm.domain.Comment;

@Service("CommentService")
@Transactional
public class CommentServiceImpl implements CommentService {

	@Autowired
	private SessionFactory sessionFactory;

	@Resource(name = "TaskService")
	TaskService taskService;

	@Override
	public Comment save(Comment comment) {
		sessionFactory.getCurrentSession().saveOrUpdate(comment);
		return comment;
	}

	@Override
	public void delete(Comment comment) {
		sessionFactory.getCurrentSession().delete(comment);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Comment> getList(Integer taskId) {

		return (ArrayList<Comment>) sessionFactory.getCurrentSession()
				.createQuery("FROM Comment WHERE task = ?")
				.setEntity(0, taskService.getById(taskId)).list();
	}

	@Override
	public Comment getById(Integer id) {

		return (Comment) sessionFactory.getCurrentSession()
				.createQuery("FROM Comment WHERE id = ?").setLong(0, id)
				.setMaxResults(1).uniqueResult();
	}
}
