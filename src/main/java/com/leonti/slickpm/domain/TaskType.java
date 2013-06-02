package com.leonti.slickpm.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.leonti.slickpm.domain.dto.TaskTypeDTO;

@Entity
public class TaskType {

	@Id
	@GeneratedValue
	private Integer id;

	@Column
	private String title;

	@Column
	private String description;
	
	@Column
	private String labelColor;	

	public TaskType() {
	}

	public TaskType(String title, String description, String labelColor) {
		this.title = title;
		this.description = description;
		this.labelColor = labelColor;
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
	
	public String getLabelColor() {
		return labelColor;
	}

	public void setLabelColor(String labelColor) {
		this.labelColor = labelColor;
	}

	public Integer getId() {
		return id;
	}

	public TaskTypeDTO getDTO() {
		return new TaskTypeDTO(id, title, description, labelColor);
	}
}
