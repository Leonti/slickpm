package com.leonti.slickpm.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.leonti.slickpm.domain.dto.IterationDTO;

@Entity
public class Iteration implements Comparable<Iteration> {
	@Id
	@GeneratedValue
	private Integer id;

	@Column
	private String title;

	@Column
	private String description;

	@Column
	private Date startDate;

	@Column
	private Date endDate;

	@OneToMany(mappedBy = "iteration", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<Task> tasks;

	@ManyToOne
	private Project project;

	public Iteration() {
	}

	public Iteration(String title, String description, Project project) {
		this.title = title;
		this.description = description;
		this.project = project;
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

	public Integer getId() {
		return id;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public IterationDTO getDTO() {
		return new IterationDTO(this.id, this.title, this.description,
				this.project.getId(), this.startDate, this.endDate);
	}

	public int hashCode() {
		return new HashCodeBuilder(17, 31).append(this.id).toHashCode();
	}

	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (obj == this)
			return true;
		if (obj.getClass() != getClass())
			return false;

		Iteration iteration = (Iteration) obj;
		return new EqualsBuilder().append(this.id, iteration.getId())
				.isEquals();
	}

	public int compareTo(Iteration other) {
		final int BEFORE = -1;
		final int EQUAL = 0;
		final int AFTER = 1;

		if (this.getId() < other.getId())
			return BEFORE;

		if (this.getId().equals(other.getId()))
			return EQUAL;

		if (this.getId() > other.getId())
			return AFTER;

		return EQUAL;
	}
}
