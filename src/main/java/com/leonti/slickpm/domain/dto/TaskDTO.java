package com.leonti.slickpm.domain.dto;


public class TaskDTO {
	
	private Integer id;
    private String title;
    private String description;
    private Double points;    
    private Integer taskTypeId;
    private Integer iterationId;
    private Integer taskStageId;
    private Integer projectId;
    private UserDTO user;
    
    public TaskDTO() {}

	public TaskDTO(Integer id, String title, String description, Double points,
			Integer taskTypeId, Integer iterationId, Integer taskStageId,
			Integer projectId, UserDTO userDTO) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.points = points;
		this.taskTypeId = taskTypeId;
		this.iterationId = iterationId;
		this.taskStageId = taskStageId;
		this.projectId = projectId;
		this.user = userDTO;
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

	public Double getPoints() {
		return points;
	}

	public void setPoints(Double points) {
		this.points = points;
	}

	public Integer getTaskTypeId() {
		return taskTypeId;
	}

	public void setTaskTypeId(Integer taskTypeId) {
		this.taskTypeId = taskTypeId;
	}

	public Integer getIterationId() {
		return iterationId;
	}

	public void setIterationId(Integer iterationId) {
		this.iterationId = iterationId;
	}

	public Integer getTaskStageId() {
		return taskStageId;
	}

	public void setTaskStageId(Integer taskStageId) {
		this.taskStageId = taskStageId;
	}

	public Integer getProjectId() {
		return projectId;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO userDTO) {
		this.user = userDTO;
	}
}
