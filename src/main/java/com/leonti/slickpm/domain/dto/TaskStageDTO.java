package com.leonti.slickpm.domain.dto;

public class TaskStageDTO {

	private Integer id;
	private String title;
	private String description;

	public TaskStageDTO() {
	}

	public TaskStageDTO(Integer id, String title, String description) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
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
}
