package com.leonti.slickpm.service;

import java.util.List;

import com.leonti.slickpm.domain.Comment;

public interface CommentService {

	Comment save(Comment comment);

	void delete(Comment comment);

	List<Comment> getList(Integer taskId);

	Comment getById(Integer id);

}