package org.nutz.mock.web;

import javax.servlet.FilterConfig;

/**
 * Ä£ÄâÒ»¸öFilterConfig
 * @author wendal(wendal1985@gmail.com)
 *
 */
public class MockFilterConfig extends MockWebObject implements FilterConfig {
	
	private String filterName;

	public String getFilterName() {
		return filterName;
	}

	public void setFilterName(String filterName) {
		this.filterName = filterName;
	}
}
