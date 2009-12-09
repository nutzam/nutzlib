package org.nutz.java.bytecode.info;


public class ClassInfo implements CPInfo {

	private ConstantPool pool;
	private int index;

	ClassInfo(ConstantPool pool, int index) {
		this.pool = pool;
		this.index = index;
	}

	public String getText() {
		return pool.getInfo(index).getText();
	}

}
