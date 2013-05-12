package com.leonti.slickpm.hook;

import com.leonti.slickpm.domain.Task;
import com.leonti.slickpm.domain.TaskStage;

public class EmailNotificationHook implements StageChangeHook {

	public void execute(Task task, TaskStage previousStage) {
		System.out.println("Email notification hook executed: "
				+ task.getTitle() + ": stage " + previousStage.getTitle()
				+ " changed to " + task.getTaskStage().getTitle());
	}
}
