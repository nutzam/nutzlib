package org.nutzx.robot.site.task;

import java.io.File;
import java.io.IOException;

import org.nutz.lang.Files;
import org.nutzx.robot.site.TaskProcessor;
import org.nutzx.robot.site.meta.Task;

public class CopyTaskProcessor implements TaskProcessor {

	public void process(Task task,File dest) throws IOException {
		File src = task.getSite().getDir();
		if (task.getType().equalsIgnoreCase("copy")) {
			String path = task.getAttr("path");
			File f = Files.getFile(src, path);
			if (f.exists()) {
				File nf = Files.getFile(dest, path);
				if (f.isFile()) {
					Files.copyFile(f, nf);
				} else if (f.isDirectory()) {
					Files.copyDir(f, nf);
				}
			}
		}
	}

}
