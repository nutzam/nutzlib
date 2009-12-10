package org.nutz.java.bytecode.cp;

public class StringInfo implements CPInfo {

	private CP pool;
	private int index;

	StringInfo(CP pool, int index) {
		this.pool = pool;
		this.index = index;
	}

	public String getText() {
		return pool.getInfo(index).getText();
	}

}
