package com.leonti.slickpm.domain.dto;

public class VcsDiff {

	private String file;
	private String content;

	public VcsDiff() {
	}

	public VcsDiff(String file, String content) {
		super();
		this.file = file;
		this.content = content;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
