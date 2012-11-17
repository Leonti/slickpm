package com.leonti.slickpm.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class UploadedFile {

    @Id
    @GeneratedValue	
	private Integer id;
    
    @Column
    private String filename;
    
    @Column
    private String contentType;
    
    @Column
    private Long size;
    
    @Column
    private Date uploadDate;

    public UploadedFile() {}
    
	public UploadedFile(String filename, String contentType, Long size) {
		this.filename = filename;
		this.contentType = contentType;
		this.size = size;
		this.uploadDate = new Date();
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public Date getUploadDate() {
		return uploadDate;
	}

	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
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

	public Integer getId() {
		return id;
	}
}
