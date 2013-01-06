package com.leonti.slickpm.domain.dto;


public class TaskDTO {
	
	private Integer id;
    private String title;

	private String description;
    private Double points;    
    private TaskTypeDTO taskType;
    private Integer iterationId;
    private Integer taskStageId;
    private ProjectDTO project;
    private UserDTO user;
    
    public TaskDTO() {}

	public TaskDTO(Integer id, String title, String description, Double points,
			TaskTypeDTO taskType, Integer iterationId, Integer taskStageId,
			ProjectDTO project, UserDTO userDTO) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.points = points;
		this.taskType = taskType;
		this.iterationId = iterationId;
		this.taskStageId = taskStageId;
		this.project = project;
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

	public TaskTypeDTO getTaskType() {
		return taskType;
	}

	public void setTaskType(TaskTypeDTO taskType) {
		this.taskType = taskType;
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

	public ProjectDTO getProject() {
		return project;
	}

	public void setProject(ProjectDTO project) {
		this.project = project;
	}

	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO userDTO) {
		this.user = userDTO;
	}
	
	// Dummy setter for automatic JSON object creation
	public void setFullTitle(String fullTitle) {}
	
	public String getFullTitle() {
		return "#" + this.id + " - " + this.title;
	}
}
