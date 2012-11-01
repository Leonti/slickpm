package com.leonti.slickpm.form;

import com.leonti.slickpm.domain.Comment;
import com.leonti.slickpm.domain.Task;

public class CommentForm {

	private String content;
	
	public CommentForm() {}
	
	public CommentForm(Comment comment) {
		this.content = comment.getContent();
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Comment getComment(Task task) {
		return new Comment(content, task);
	}
}
