package com.leonti.slickpm.domain.dto;

import java.util.Date;

public class UploadedFileDTO {

	private Integer id;
	private String filename;
	private String contentType;
	private Long size;
	private Date uploadDate;

	public UploadedFileDTO() {
	}

	public UploadedFileDTO(Integer id, String filename, String contentType,
			Long size, Date uploadDate) {
		super();
		this.id = id;
		this.filename = filename;
		this.contentType = contentType;
		this.size = size;
		this.uploadDate = uploadDate;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}

	public Date getUploadDate() {
		return uploadDate;
	}

	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}
}
