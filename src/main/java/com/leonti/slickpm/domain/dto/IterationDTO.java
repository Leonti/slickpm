package com.leonti.slickpm.domain.dto;


public class IterationDTO {
	private Integer id;
    
    private String title;
    
    private String description;
    
    private Integer projectId;
   
    public IterationDTO() {}
    
	public IterationDTO(Integer id, String title, String description,
			Integer projectId) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.projectId = projectId;
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
}
