package com.leonti.slickpm.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.apache.commons.lang.builder.EqualsBuilder;

import com.leonti.slickpm.domain.dto.VcsDTO;

@Entity
public abstract class Vcs {

    @Id
    @GeneratedValue	
	private Integer id;
    
    @Column
    private String uri;
    
    @Column
    private String login;
    
    @Column
    private String password;


	public String getUri() {
		return uri;
	}


	public void setUri(String uri) {
		this.uri = uri;
	}


	public String getLogin() {
		return login;
	}


	public void setLogin(String login) {
		this.login = login;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public Integer getId() {
		return id;
	}
	
	public VcsDTO getDTO() {
		return new VcsDTO(this.id, this.uri);
	}
	
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (obj == this)
            return true;
        if (obj.getClass() != getClass())
            return false;

        Vcs vcs = (Vcs) obj;
        return new EqualsBuilder().
            append(this.uri, vcs.getUri()).
            isEquals();
    } 		
}
