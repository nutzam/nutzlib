package org.nutzx.robot.site;

import static java.lang.String.format;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import org.nutz.lang.Files;
import org.nutz.lang.Strings;
import org.nutz.lang.segment.Segment;
import org.nutzx.robot.site.impl.DefaultSiteLoader;
import org.nutzx.robot.site.meta.RenderInfo;
import org.nutzx.robot.site.meta.Site;
import org.nutzx.robot.site.meta.Task;
import org.nutzx.robot.site.task.CopyTaskProcessor;
import org.nutzx.robot.site.task.LinkTaskProcessor;

public class SiteRobot {

	public static final String STYLESHEET = "<link rel=\"stylesheet\" type=\"text/css\" href=\"%s\"/>\r\n";
	public static final String SCRIPT = "<script language=\"Javascript\" src=\"%s\"></script>\r\n";

	public SiteRobot() {
		tps = new HashMap<String, TaskProcessor>();
		tps.put("copy", new CopyTaskProcessor());
		tps.put("link", new LinkTaskProcessor());
	}

	private Map<String, TaskProcessor> tps;

	public void transform(File src, File dest) throws Exception {
		// Load site
		Site site = new DefaultSiteLoader().load(src);

		// run tasks
		for (Task t : site.getTasks()) {
			TaskProcessor tp = tps.get(t.getType());
			if (null != tp)
				tp.process(t, dest);
		}

		// Rend page
		for (RenderInfo info : site.getRenders())
			info.getRender().render(info.getParams(), dest, info.getPage());
	}

	public static void buildLink(Segment seg, Site site, String ptn, String prefix, String to) {
		StringBuilder sb = new StringBuilder();
		String[] paths = site.getAttr(String[].class, to);
		if (null != paths) {
			/*
			 * Ordering the paths
			 */
			if (to.equalsIgnoreCase("js")) {
				ArrayList<String> after = new ArrayList<String>();
				ArrayList<String> jqs = new ArrayList<String>();
				ArrayList<String> nutzs = new ArrayList<String>();
				ArrayList<String> jqnutzs = new ArrayList<String>();
				ArrayList<String> mine = new ArrayList<String>();
				for (String path : paths) {
					if (isLike(path, "jquery.nutz")) {
						jqnutzs.add(path);
					} else if (isLike(path, "jquery")) {
						jqs.add(path);
					} else if (isLike(path, "nutz")) {
						nutzs.add(path);
					} else {
						mine.add(path);
					}
				}
				sort(jqs);
				sort(nutzs);
				sort(jqnutzs);
				sort(mine);

				after.addAll(jqs);
				after.addAll(nutzs);
				after.addAll(jqnutzs);
				after.addAll(mine);

				paths = after.toArray(new String[after.size()]);
			}
			/*
			 * Rendering...
			 */
			for (String path : paths)
				if (Strings.isBlank(prefix))
					sb.append(format(ptn, path));
				else
					sb.append(format(ptn, prefix + path));
			if (sb.length() > 0)
				seg.set(to, sb);
		}
	}

	private static void sort(ArrayList<String> jqs) {
		Collections.sort(jqs, new Comparator<String>() {
			public int compare(String s1, String s2) {
				if (s1.length() == s2.length())
					return 0;
				if (s1.length() > s2.length())
					return 1;
				return -1;
			}
		});
	}

	private static boolean isLike(String path, String prefix) {
		String name = Files.getMajorName(path);
		return name.toLowerCase().startsWith(prefix);
	}
}
