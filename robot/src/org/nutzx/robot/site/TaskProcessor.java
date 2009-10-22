package org.nutzx.robot.site;

import java.io.File;

import org.nutzx.robot.site.meta.Task;

public interface TaskProcessor {

	void process(Task task, File dest) throws Exception;

}
