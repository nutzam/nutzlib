package org.nutzx.robot.site.meta;

import java.util.Map;

import org.nutzx.robot.site.PageRender;

public class RenderInfo {

	public RenderInfo(PageRender render, Map<String, String> params, Page page) {
		this.render = render;
		this.page = page;
		this.params = params;
	}

	private PageRender render;
	private Map<String, String> params;
	private Page page;

	public PageRender getRender() {
		return render;
	}

	public void setRender(PageRender render) {
		this.render = render;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public Map<String, String> getParams() {
		return params;
	}

	public void setParams(Map<String, String> params) {
		this.params = params;
	}

}
