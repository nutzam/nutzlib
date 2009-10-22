package org.nutzx.robot.site.impl;

import java.io.File;

import java.io.IOException;
import java.util.Map;

import org.nutz.lang.Files;
import org.nutz.lang.Lang;
import org.nutz.lang.Streams;
import org.nutz.lang.segment.Segment;
import org.nutzx.robot.site.PageRender;
import org.nutzx.robot.site.SiteRobot;
import org.nutzx.robot.site.meta.Page;
import org.nutzx.robot.site.meta.Site;

public class DefaultPageRender implements PageRender {

	public void render(Map<String, String> params, File dest, Page page) throws IOException {
		Site site = page.getSite();

		File f = new File(dest.getAbsolutePath() + "/" + page.getPath());
		if (!f.exists())
			Files.createNewFile(f);

		String html = Lang.readAll(Streams.fileInr(page.getSource()));

		Segment seg = site.getTemplate().born();
		seg.set("title", site.getTitle());
		seg.set(site.getGasket(), html);

		SiteRobot.buildLink(seg, site, SiteRobot.STYLESHEET, null, "css");
		SiteRobot.buildLink(seg, site, SiteRobot.SCRIPT, null, "js");

		html = seg.toString();
		Lang.writeAll(Streams.fileOutw(f), html);
	}

	public void init(Map<String, String> params) {}

}
