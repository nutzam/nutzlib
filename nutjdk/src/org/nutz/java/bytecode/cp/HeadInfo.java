package org.nutz.java.bytecode.cp;

/**
 * 常量池的占位元素
 * 
 * @author zozoh(zozohtnt@gmail.com)
 */
public class HeadInfo extends AbstractCPInfo {

	public String getText() {
		return "<head>";
	}

	@Override
	public boolean equals(Object obj) {
		return false;
	}

}
