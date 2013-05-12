package com.leonti.slickpm.domain.dto;

public class ProjectDTO {

	private Integer id;
	private String title;
	private String description;
	private VcsDTO vcs;

	public ProjectDTO() {
	}

	public ProjectDTO(Integer id, String title, String description,
			VcsDTO vcsDTO) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.vcs = vcsDTO;
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

	public VcsDTO getVcs() {
		return vcs;
	}

	public void setVcs(VcsDTO vcs) {
		this.vcs = vcs;
	}
}
