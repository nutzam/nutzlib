package org.nutzx.robot.site.impl;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.nutz.json.Json;
import org.nutz.lang.Files;
import org.nutz.lang.segment.CharSegment;
import org.nutz.lang.segment.Segment;
import org.nutzx.robot.site.PageRender;
import org.nutzx.robot.site.SiteRobot;
import org.nutzx.robot.site.meta.Page;
import org.nutzx.robot.site.meta.Site;

import static java.lang.String.*;

public class HDJYPageRender implements PageRender {

	private Site site;
	private String outputTo;

	private Segment segIndex;
	private Segment segDetail;
	private Map<String, String> map;

	private StringBuilder ulImgs;
	private StringBuilder ulIndexes;

	@SuppressWarnings("unchecked")
	public void render(Map<String, String> params, File dest, Page page) throws Exception {
		this.site = page.getSite();
		File base = page.getSource();
		this.outputTo = Files.getFile(dest, base.getName()).getAbsolutePath() + "/";

		String modes = params.get("modes");
		map = Json.fromJson(HashMap.class, modes);
		segIndex = new CharSegment(Files.read(Files.getFile(base, "index.html")));
		segDetail = new CharSegment(Files.read(Files.getFile(base, "temp.html")));

		File[] coms = Files.dirs(base);
		for (File dir : coms)
			renderCompany(dir);
	}

	private void renderCompany(File dir) throws IOException {
		String comName = dir.getName();
		ulImgs = new StringBuilder();
		ulIndexes = new StringBuilder();
		/*
		 * Check each product mode
		 */
		File[] modes = Files.dirs(dir);
		for (File f : modes)
			renderMode(comName, f);
		/*
		 * Write the index file for current product
		 */
		Segment comIndex = segIndex.born();
		comIndex.set("indexes", ulIndexes).set("products", ulImgs);
		comIndex.set("company", comName);
		File comFile = new File(outputTo, comName + "/index.html");

		Segment seg = site.getTemplate().born();
		seg.set("title", site.getTitle() + " : " + comName.toUpperCase());
		seg.set("base", "../../");
		seg.set(site.getGasket(), comIndex.toString());

		SiteRobot.buildLink(seg, site, SiteRobot.STYLESHEET, "../../", "css");
		SiteRobot.buildLink(seg, site, SiteRobot.SCRIPT, "../../", "js");

		Files.write(comFile, seg);
	}

	private static final String LI_MENU = "\t\t\t\t\t<li to='#p_%s'>%s\r\n";

	private static final String LI_IMG = "\t\t\t\t<li><img href='%s/%s.html' src='%s/%s_thumbnail.jpg'/>\r\n";

	private void renderMode(String comName, File dir) throws IOException {
		String modeName = dir.getName();
		// Eval mode text
		String modeText = map.get(modeName);
		if (null == modeText)
			modeText = modeName;

		ulIndexes.append(format(LI_MENU, modeName, modeText));
		ulImgs.append(format("\t\t\t<ul id=p_%s>\r\n", modeName));
		File[] products = Files.files(dir, "_thumbnail.jpg");
		for (File f : products) {
			String fn = f.getName();
			String proName = fn.substring(0, fn.lastIndexOf('_'));
			// Eval product text
			String proText = proName.toUpperCase();

			// Create product HTML file
			Segment seg = segDetail.born();
			seg.set("modeText", modeText);
			seg.set("mode", modeName).set("name", proName).set("text", proText);
			File html = new File(outputTo, comName + "/" + modeName + "/" + proName + ".html");
			Files.write(html, seg);

			// Copy thumbnail
			File newThumbnail = new File(outputTo, comName + "/" + modeName + "/" + f.getName());
			Files.copyFile(f, newThumbnail);

			// Copy images
			File img = new File(f.getParent() + "/" + proName + ".jpg");
			File newLarge = new File(outputTo, comName + "/" + modeName + "/" + proName + ".jpg");
			Files.copyFile(img, newLarge);

			// Add to UL-LI menu
			ulImgs.append(format(LI_IMG, modeName, proName, modeName, proName));
		}
		ulImgs.append("\t\t\t</ul>\r\n");
	}
}
