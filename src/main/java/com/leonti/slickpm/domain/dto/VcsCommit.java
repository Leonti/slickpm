package com.leonti.slickpm.domain.dto;

import java.util.ArrayList;
import java.util.List;

public class VcsCommit {

	private String id;
	private String message;

	List<VcsDiff> diffs;

	public VcsCommit() {
	}

	public VcsCommit(String id, String message) {
		super();
		this.id = id;
		this.message = message;
		this.diffs = new ArrayList<VcsDiff>();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<VcsDiff> getDiffs() {
		return diffs;
	}

	public void setDiffs(List<VcsDiff> diffs) {
		this.diffs = diffs;
	}

	public void addDiff(VcsDiff diff) {
		this.diffs.add(diff);
	}

}
