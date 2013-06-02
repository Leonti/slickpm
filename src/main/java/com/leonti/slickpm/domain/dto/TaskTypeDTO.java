package com.leonti.slickpm.domain.dto;

public class TaskTypeDTO {

	private Integer id;
	private String title;
	private String description;
	private String labelColor;

	public TaskTypeDTO() {
	}

	public TaskTypeDTO(Integer id, String title, String description, String labelColor) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.labelColor = labelColor;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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
}
