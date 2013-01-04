package com.leonti.slickpm.domain.dto;

public class VcsDTO {

	private Integer id;
	private String uri;
	
	public VcsDTO() {}
		
	public VcsDTO(Integer id, String uri) {
		super();
		this.id = id;
		this.uri = uri;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
}
