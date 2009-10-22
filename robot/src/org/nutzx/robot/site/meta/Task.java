package org.nutzx.robot.site.meta;

import java.util.HashMap;
import java.util.Map;

public class Task {

	public Task() {
		attrs = new HashMap<String, String>();
	}

	private Site site;

	private String type;

	private Map<String, String> attrs;

	public Site getSite() {
		return site;
	}

	public void setSite(Site site) {
		this.site = site;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setAttr(String name, String value) {
		attrs.put(name, value);
	}

	public String getAttr(String name) {
		return attrs.get(name);
	}

}
