package com.leonti.slickpm.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public abstract class Position implements Comparable<Position> {

	@Id
	@GeneratedValue
	private Integer id;

	@Column
	private Integer position;

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

	public Integer getId() {
		return id;
	}

	public int compareTo(Position other) {
		final int BEFORE = -1;
		final int EQUAL = 0;
		final int AFTER = 1;

		if (this.getPosition() < other.getPosition())
			return BEFORE;

		if (this.getPosition().equals(other.getPosition()))
			return EQUAL;

		if (this.getPosition() > other.getPosition())
			return AFTER;

		return EQUAL;
	}
}
