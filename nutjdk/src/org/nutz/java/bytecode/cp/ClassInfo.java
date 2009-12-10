package org.nutz.java.bytecode.cp;


public class ClassInfo implements CPInfo {

	private CP pool;
	private int index;

	ClassInfo(CP pool, int index) {
		this.pool = pool;
		this.index = index;
	}

	public String getText() {
		return pool.getInfo(index).getText();
	}

}
