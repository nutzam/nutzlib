package org.nutz.java.bytecode.cp;

public class MemberInfo implements CPInfo {

	private CP pool;
	private int ci;
	private int ni;

	MemberInfo(CP pool, int ci, int ni) {
		this.pool = pool;
		this.ci = ci;
		this.ni = ni;
	}

	public String getText() {
		return String.format("%s.%s", pool.getInfo(ci).getText(), pool.getInfo(ni).getText());
	}

}
