package com.leonti.slickpm.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.leonti.slickpm.domain.dto.ProjectDTO;

@Entity
public class Project {

    @Id
    @GeneratedValue	
	private Integer id;
    
    @Column
    private String title;
    
    @Column
    private String description;
    
    @ManyToOne
    private Vcs vcs;

    public Project() {}
    
    public Project(String title, String description) {
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

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}
	
	public Vcs getVcs() {
		return vcs;
	}

	public void setVcs(Vcs vcs) {
		this.vcs = vcs;
	}

	public ProjectDTO getDTO() {
		return new ProjectDTO(id, title, description, vcs != null ? vcs.getDTO() : null );
	}
}
