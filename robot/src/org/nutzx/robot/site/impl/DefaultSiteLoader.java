package org.nutzx.robot.site.impl;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.nutz.lang.Files;
import org.nutz.lang.Lang;
import org.nutz.lang.Streams;
import org.nutz.lang.Strings;
import org.nutz.lang.segment.CharSegment;
import org.nutzx.robot.site.PageRender;
import org.nutzx.robot.site.SiteLoader;
import org.nutzx.robot.site.meta.Page;
import org.nutzx.robot.site.meta.RenderInfo;
import org.nutzx.robot.site.meta.Site;
import org.nutzx.robot.site.meta.Task;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DefaultSiteLoader implements SiteLoader {

	public Site load(File dir) throws Exception {
		Site site = new Site(dir);
		// find site.xml
		File xml = new File(dir.getAbsolutePath() + "/site.xml");
		Element root = Lang.xmls().parse(xml).getDocumentElement();

		// read site attributes
		site.setTitle(root.getAttribute("title"));
		site.setGasket(root.getAttribute("gasket"));
		File tempFile = Files.getFile(dir, root.getAttribute("pattern"));
		site.setTemplate(new CharSegment(Lang.readAll(Streams.fileInr(tempFile))));

		// load tasks
		NodeList list = root.getElementsByTagName("task");
		for (int i = 0; i < list.getLength(); i++) {
			Element ele = (Element) list.item(i);
			Task t = new Task();
			t.setType(ele.getAttribute("type"));
			NamedNodeMap nnm = ele.getAttributes();
			for (int j = 0; j < nnm.getLength(); j++) {
				Node attr = nnm.item(j);
				if (attr.getNodeName().equalsIgnoreCase("type"))
					continue;
				t.setAttr(Strings.trim(attr.getNodeName().toLowerCase()), attr.getNodeValue());
				t.setSite(site);
			}
			site.getTasks().add(t);
		}

		// load renders
		list = root.getElementsByTagName("render");
		for (int i = 0; i < list.getLength(); i++) {
			Element ele = (Element) list.item(i);
			PageRender render = createRender(ele);
			Map<String, String> params = loadParams(ele);
			Page[] pages = createPageArray(ele);
			for (Page p : pages) {
				p.setSite(site);
				p.setSource(Files.getFile(dir, p.getPath()));
				site.getRenders().add(new RenderInfo(render, params, p));
			}
		}
		return site;
	}

	private Page[] createPageArray(Element ele) {
		NodeList list = ele.getElementsByTagName("page");
		Page[] pages = new Page[list.getLength()];
		for (int i = 0; i < list.getLength(); i++) {
			Element page = (Element) list.item(i);
			pages[i] = new Page();
			pages[i].setPath(page.getAttribute("path"));
			pages[i].setTitle(page.getAttribute("title"));
		}
		return pages;
	}

	private PageRender createRender(Element ele) throws Exception {
		String typeName = ele.getAttribute("type");
		PageRender render;
		if (Strings.isBlank(typeName))
			render = new DefaultPageRender();
		else
			render = (PageRender) Class.forName(typeName).newInstance();
		return render;
	}

	private Map<String, String> loadParams(Element ele) {
		Map<String, String> params = new HashMap<String, String>();
		NodeList list = ele.getElementsByTagName("param");
		for (int i = 0; i < list.getLength(); i++) {
			Element param = (Element) list.item(i);
			String key = Strings.trim(param.getAttribute("name")).toLowerCase();
			params.put(key, Strings.trim(param.getTextContent()));
		}
		return params;
	}

}
