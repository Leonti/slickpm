package com.leonti.slickpm.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class User {

    @Id
    @GeneratedValue	
	private Integer id;
    
    @Column
    private String name;
    
    @ManyToOne
    private UploadedFile avatar;       
    
    public User() {}
    
    public User(String name) {
    	this.name = name;
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public UploadedFile getAvatar() {
		return avatar;
	}

	public void setAvatar(UploadedFile avatar) {
		this.avatar = avatar;
	}

	public Integer getId() {
		return id;
	}
}
