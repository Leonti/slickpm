package com.leonti.slickpm.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.leonti.slickpm.domain.dto.TaskDTO;

@Entity
public class Task {

    @Id
    @GeneratedValue	
	private Integer id;
    
    @Column
    private String title;
    
    @Column
    private String description;
    
    @Column
    private Double points;    
    
    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany
    private List<Task> dependsOn;
    
    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany
    private List<UploadedFile> files;
    
    @ManyToOne
    private TaskType taskType;
    
    @ManyToOne
    private Iteration iteration;
    
    @ManyToOne
    private TaskStage taskStage;
    
    @ManyToOne
    private Project project;
    
    @ManyToOne
    private User user;

    @LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy = "task", cascade = CascadeType.ALL)
	private List<Comment> comments;    
    
    public Task() {}
    
    public Task(String title, String description, TaskType taskType, Project project) {
    	this.title = title;
    	this.description = description;
    	this.project = project;
    	this.taskType = taskType;
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

	public Double getPoints() {
		return points;
	}

	public void setPoints(Double points) {
		this.points = points;
	}

	public TaskType getTaskType() {
		return taskType;
	}

	public void setTaskType(TaskType taskType) {
		this.taskType = taskType;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public Iteration getIteration() {
		return iteration;
	}

	public void setIteration(Iteration iteration) {
		this.iteration = iteration;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public TaskStage getTaskStage() {
		return taskStage;
	}

	public void setTaskStage(TaskStage taskStage) {
		this.taskStage = taskStage;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Task> getDependsOn() {
		return dependsOn;
	}

	public void setDependsOn(List<Task> dependsOn) {
		this.dependsOn = dependsOn;
	}
	
	public List<UploadedFile> getFiles() {
		return files;
	}

	public void setFiles(List<UploadedFile> files) {
		this.files = files;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public TaskDTO getDTO() {
		
		return new TaskDTO(id, title, description, 
				points,
				taskType == null ? null : taskType.getDTO(),
				iteration == null ? null : iteration.getId(), 
				taskStage == null ? null : taskStage.getId(), 
				project == null ? null : project.getDTO(), 
				user == null ? null : user.getDTO());
	}
	
    public int hashCode() {
        return new HashCodeBuilder(17, 31).
            append(this.id).
            toHashCode();
    }
    
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (obj == this)
            return true;
        if (obj.getClass() != getClass())
            return false;

        Task task = (Task) obj;
        return new EqualsBuilder().
            append(this.id, task.getId()).
            isEquals();
    }   
}
