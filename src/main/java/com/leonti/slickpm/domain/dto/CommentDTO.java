package com.leonti.slickpm.domain.dto;

import java.util.Date;

public class CommentDTO {
	
	private Integer id;
	
	private Integer taskId;
	
    private String content;
    
    private Date date;   

    public CommentDTO() {}

	public CommentDTO(Integer id, Integer taskId, String content, Date date) {
		super();
		this.id= id;
		this.taskId = taskId;
		this.content = content;
		this.date = date;
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

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getTaskId() {
		return taskId;
	}

	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}	
}
