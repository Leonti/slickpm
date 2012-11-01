package com.leonti.slickpm.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
public class Task {

    @Id
    @GeneratedValue	
	private Integer id;
    
    @Column
    private String title;
    
    @Column
    private String description;
    
    @ManyToOne
    private Task parent;
    
    @ManyToOne
    private TaskType taskType;
    
    @ManyToOne
    private Iteration iteration;
    
    @ManyToOne
    private Project project;

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

	public Task getParent() {
		return parent;
	}

	public TaskType getTaskType() {
		return taskType;
	}

	public void setTaskType(TaskType taskType) {
		this.taskType = taskType;
	}

	public void setParent(Task parent) {
		this.parent = parent;
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
}