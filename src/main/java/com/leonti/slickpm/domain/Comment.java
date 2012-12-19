package com.leonti.slickpm.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.leonti.slickpm.domain.dto.CommentDTO;

@Entity
public class Comment {

    @Id
    @GeneratedValue	
	private Integer id;
    
    @Column
    private String content;
    
    @Column
    private Date date;
    
    @ManyToOne
    private Task task;

    public Comment() {}
        
	public Comment(String content, Task task) {
		this.content = content;
		this.task = task;
		this.date = new Date();
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Integer getId() {
		return id;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}
	
	public CommentDTO getDTO() {
		return new CommentDTO(this.id, this.task.getId(), this.content, this.date);
	}
}
