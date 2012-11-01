package com.leonti.slickpm.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Points {

    @Id
    @GeneratedValue	
	private Integer id;
    
	@Column
	private String title;
	
	@Column
	private Double value;

	public Points() {}
	
	public Points(String title, Double value) {
		this.title = title;
		this.value = value;
	}
	
	public Integer getId() {
		return id;
	}	
	public String getTitle() {
		return title;
	}
	public Double getValue() {
		return value;
	}
}
