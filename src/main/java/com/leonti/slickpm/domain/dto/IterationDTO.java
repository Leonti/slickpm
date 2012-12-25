package com.leonti.slickpm.domain.dto;

import java.util.Date;


public class IterationDTO {
	private Integer id;
    
    private String title;
    
    private String description;
    
    private Integer projectId;
    
    private Date startDate;
    
    private Date endDate;
   
    public IterationDTO() {}
    
	public IterationDTO(Integer id, String title, String description,
			Integer projectId, Date startDate, Date endDate) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.projectId = projectId;
		this.startDate= startDate;
		this.endDate = endDate;
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

	public Integer getProjectId() {
		return projectId;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
}
