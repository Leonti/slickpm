package com.leonti.slickpm.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class TaskStage {
	
    @Id
    @GeneratedValue	
	private Integer id;
    
    @Column
    private String title;
    
    @Column
    private String description;

    public TaskStage() {}
    
    public TaskStage(String title, String description) {
    	this.title = title;
    	this.description = description;
    }
    
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getId() {
		return id;
	}
}
