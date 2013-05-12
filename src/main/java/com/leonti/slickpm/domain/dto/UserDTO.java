package com.leonti.slickpm.domain.dto;

public class UserDTO {

	private Integer id;
	private String name;
	private Integer avatarId;

	public UserDTO() {
	}

	public UserDTO(Integer id, String name, Integer avatarId) {
		super();
		this.id = id;
		this.name = name;
		this.avatarId = avatarId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAvatarId() {
		return avatarId;
	}

	public void setAvatarId(Integer avatarId) {
		this.avatarId = avatarId;
	}
}
