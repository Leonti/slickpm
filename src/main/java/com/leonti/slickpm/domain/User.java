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

	@Column(name="email")
	private String email;
	
	private String password;

	@Column(name="forgotKey")
	private String forgotKey = null;
	
	@Column(name="confirmationKey")
	private String confirmationKey = null;

	private Boolean deleted = false;
	
	private Integer role = 2;    
    
    public User() {}
    
    public User(String name, String email, String password) {
    	this.name = name;
    	this.password = password;
    	this.email = email;
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
		
    public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getForgotKey() {
		return forgotKey;
	}

	public void setForgotKey(String forgotKey) {
		this.forgotKey = forgotKey;
	}

	public String getConfirmationKey() {
		return confirmationKey;
	}

	public void setConfirmationKey(String confirmationKey) {
		this.confirmationKey = confirmationKey;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	public Integer getRole() {
		return role;
	}

	public void setRole(Integer role) {
		this.role = role;
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
