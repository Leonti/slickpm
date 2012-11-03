package com.leonti.slickpm.hook;

import com.leonti.slickpm.domain.Task;
import com.leonti.slickpm.domain.TaskStage;

public interface StageChangeHook {

	void execute(Task task, TaskStage previousStage);
}
