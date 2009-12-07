package org.nutz.java.src;

import java.util.List;

/**
 * 枚举
 * 
 * @author zozoh(zozohtnt@gmail.com)
 */
public class JavaEnum extends JavaType {

	private List<String> items;

	public JavaEnum() {
		super();
		items = Srcs.list(String.class);
	}

	/**
	 * @return 枚举值列表
	 */
	public List<String> items() {
		return items;
	}

}
