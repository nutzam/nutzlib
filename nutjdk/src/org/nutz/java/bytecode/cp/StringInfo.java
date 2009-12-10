package org.nutz.java.bytecode.cp;

public class StringInfo extends AbstractCPInfo {

	private CP pool;
	private int index;

	StringInfo(CP pool, int index) {
		this.pool = pool;
		this.index = index;
	}

	public String getText() {
		return pool.getInfoText(index);
	}

}
