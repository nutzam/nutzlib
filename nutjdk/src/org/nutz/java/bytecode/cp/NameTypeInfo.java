package org.nutz.java.bytecode.cp;

public class NameTypeInfo extends AbstractCPInfo {

	private CP pool;
	private int ni;
	private int di;

	NameTypeInfo(CP pool, int ni, int di) {
		this.pool = pool;
		this.ni = ni;
		this.di = di;
	}

	public String getText() {
		String name = pool.getInfoText(ni);
		String descriptor = pool.getInfoText(di);
		if (null == name || null == descriptor)
			return null;
		return String.format("%s-%s", name, descriptor);
	}

}
