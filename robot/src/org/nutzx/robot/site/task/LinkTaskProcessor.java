package org.nutzx.robot.site.task;

import java.io.File;

import org.nutz.lang.Files;
import org.nutz.lang.Lang;
import org.nutzx.robot.site.TaskProcessor;
import org.nutzx.robot.site.meta.Site;
import org.nutzx.robot.site.meta.Task;

public class LinkTaskProcessor implements TaskProcessor {

	@Override
	public void process(Task task, File dest) throws Exception {
		Site site = task.getSite();
		String to = task.getAttr("to");
		String path = task.getAttr("path");
		String suffix = task.getAttr("suffix");

		File f = Files.getFile(site.getDir(), path);
		if (f.exists()) {
			String[] list = site.getAttr(String[].class, to);
			if (list == null)
				list = new String[0];
			
			if (f.isFile()) {
				list = Lang.merge(list, Lang.array(path));
			} else if (f.isDirectory()) {
				File[] fs = Files.files(f, suffix);
				String[] nms = new String[fs.length];
				for (int i = 0; i < fs.length; i++)
					nms[i] = path + "/" + fs[i].getName();
				list = Lang.merge(list, nms);
			}
			
			site.setAttr(to, list);
		}
	}

}
