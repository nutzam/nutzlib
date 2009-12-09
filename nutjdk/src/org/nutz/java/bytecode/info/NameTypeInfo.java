package org.nutz.java.bytecode.info;

public class NameTypeInfo implements CPInfo {

	private ConstantPool pool;
	private int ni;
	private int di;

	NameTypeInfo(ConstantPool pool, int ni, int di) {
		this.pool = pool;
		this.ni = ni;
		this.di = di;
	}

	public String getText() {
		return String.format("%s-%s", pool.getInfo(ni).getText(), pool.getInfo(di).getText());
	}

}
