package com.leonti.slickpm.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.leonti.slickpm.domain.dto.UserDTO;

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

        User user = (User) obj;
        return new EqualsBuilder().
            append(this.id, user.getId()).
            isEquals();
    }
    
    public UserDTO getDTO() {
    	return new UserDTO(id, name, avatar == null ? null : avatar.getId());
    }
}
