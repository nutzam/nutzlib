package org.nutzx.robot.site.meta;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.nutz.lang.segment.Segment;

public class Site {

	public Site(File dir) {
		renders = new ArrayList<RenderInfo>();
		tasks = new ArrayList<Task>();
		attrs = new HashMap<String, Object>();
		this.dir = dir;
	}

	private String title;
	private Segment template;
	private String gasket;
	private List<RenderInfo> renders;
	private File dir;
	private List<Task> tasks;
	private Map<String, Object> attrs;

	@SuppressWarnings("unchecked")
	public <T> T getAttr(Class<T> type, String name) {
		return (T) attrs.get(name);
	}

	public void setAttr(String name, Object value) {
		attrs.put(name, value);
	}

	public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

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
