package org.nutzx.robot.site.meta;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.nutz.lang.segment.Segment;

public class Site {

	public Site(File dir) {
		renders = new ArrayList<RenderInfo>();
		this.dir = dir;
	}

	private String title;
	private Segment template;
	private String gasket;
	private List<RenderInfo> renders;
	private File dir;

	public File getDir() {
		return dir;
	}

	public void setDir(File source) {
		this.dir = source;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Segment getTemplate() {
		return template;
	}

	public void setTemplate(Segment seg) {
		this.template = seg;
	}

	public String getGasket() {
		return gasket;
	}

	public void setGasket(String gasket) {
		this.gasket = gasket;
	}

	public List<RenderInfo> getRenders() {
		return renders;
	}

	public void setRenders(List<RenderInfo> renders) {
		this.renders = renders;
	}

}
