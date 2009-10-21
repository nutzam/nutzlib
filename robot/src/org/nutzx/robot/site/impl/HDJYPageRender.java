package org.nutzx.robot.site.impl;

import java.io.File;
import java.util.Map;

import org.nutz.lang.Files;
import org.nutz.lang.Lang;
import org.nutz.lang.Streams;
import org.nutz.lang.segment.CharSegment;
import org.nutz.lang.segment.Segment;
import org.nutzx.robot.site.PageRender;
import org.nutzx.robot.site.meta.Page;

import static java.lang.String.*;

public class HDJYPageRender implements PageRender {

	private static final String LI = "\t\t\t<li><img href=\"%s\" src=\"%s\"/></li>\n";

	public void render(Map<String, String> params, File dest, Page page) throws Exception {
		File base = page.getSource();
		String dftDetail = file2html(Files.getFile(base, params.get("default-text")));
		String imgSuffix = params.get("img-suffix");
		// parse index.html as template

		Segment tpIndex = loadTemplate(base, params, "index-template");
		Segment tpDetail = loadTemplate(base, params, "detail-template");

		// lets loop each folder
		for (File dir : Files.dirs(base)) {
			String name = dir.getName().toLowerCase();
			String target = targetPath(dest, base, name);

			// generate index html
			/*
			 * Find all images
			 */
			File[] imgs = Files.files(dir, imgSuffix);
			StringBuilder uls = new StringBuilder();
			// each products
			for (File img : imgs) {
				String pName = Files.getMajorName(img);
				// for indexes
				uls.append(format(LI, HREF(img), img.getName()));
				// load detail
				File detail = Files.renameSuffix(img, ".html");
				String s;
				if (detail.exists())
					s = file2html(detail);
				else
					s = dftDetail;
				// render detail and write new file
				Segment seg = tpDetail.born();
				seg.set("name", pName).set("detail", s);
				File newDetail = new File(target + HREF(img));
				Lang.writeAll(Streams.fileOutw(newDetail), seg.toString());

				// copy image
				File newImg = new File(target + img.getName());
				Files.copyFile(img, newImg);
			}
			// create the index
			File indexFile = new File(target + "index.html");
			if (!indexFile.exists())
				Files.createNewFile(indexFile);
			Segment seg = tpIndex.born();
			seg.set("name", name).set("list", uls.toString());
			Segment html = page.getSite().getTemplate().clone();
			html.set(page.getSite().getGasket(), seg.toString());
			Lang.writeAll(Streams.fileOutw(indexFile), html.toString());
		}
	}

	private String targetPath(File dest, File base, String name) {
		return dest.getAbsolutePath() + "/" + base.getName() + "/" + name + "/";
	}

	private Segment loadTemplate(File base, Map<String, String> params, String name) {
		File f1 = Files.getFile(base, params.get(name));
		return new CharSegment(Lang.readAll(Streams.fileInr(f1)));
	}

	private static String HREF(File img) {
		return Files.getMajorName(img).toLowerCase() + ".html";
	}

	private String file2html(File f) {
		// ZDocParser parser = new ZDocParser();
		// ZDoc doc = parser.parse(Lang.readAll(Streams.fileInr(f)));
		// HtmlDocRender render = new HtmlDocRender();
		// return render.render(doc).toString();
		return Lang.readAll(Streams.fileInr(f));
	}

}
