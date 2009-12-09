package org.nutz.java.bytecode.info;

public class StringInfo implements CPInfo {

	private ConstantPool pool;
	private int index;

	StringInfo(ConstantPool pool, int index) {
		this.pool = pool;
		this.index = index;
	}

	public String getText() {
		return pool.getInfo(index).getText();
	}

}
