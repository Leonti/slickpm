package com.leonti.slickpm.service;

import java.util.List;

import com.leonti.slickpm.domain.GitVcs;
import com.leonti.slickpm.domain.Task;
import com.leonti.slickpm.domain.Vcs;
import com.leonti.slickpm.domain.dto.VcsCommit;

public interface VcsService {

	public static enum VcsStatus {
		OK, INVALID_URI
	}

	List<VcsCommit> getDiffForTask(GitVcs gitVcs, Task task);

	VcsStatus checkAndCreateVcsCache(GitVcs gitVcs);

	Vcs save(GitVcs vcs);

	void delete(GitVcs vcs);

	GitVcs getById(Integer id);

}