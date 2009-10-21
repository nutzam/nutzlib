package org.nutzx.robot.site.impl;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.nutz.lang.Files;
import org.nutz.lang.Lang;
import org.nutz.lang.Streams;
import org.nutz.lang.segment.Segment;
import org.nutzx.robot.site.PageRender;
import org.nutzx.robot.site.meta.Page;

public class DefaultPageRender implements PageRender {

	public void render(Map<String, String> params, File dest, Page page) throws IOException {
		File f = new File(dest.getAbsolutePath() + "/" + page.getPath());
		if (!f.exists())
			Files.createNewFile(f);

		String html = Lang.readAll(Streams.fileInr(page.getSource()));
		Segment seg = page.getSite().getTemplate().born();
		seg.set("title", page.getSite().getTitle());
		seg.set(page.getSite().getGasket(), html);

		html = seg.toString();
		Lang.writeAll(Streams.fileOutw(f), html);
	}

	public void init(Map<String, String> params) {}

}
