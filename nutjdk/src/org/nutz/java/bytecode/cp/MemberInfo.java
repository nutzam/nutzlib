package org.nutz.java.bytecode.cp;

public class MemberInfo extends AbstractCPInfo {

	private CP pool;
	private int ci;
	private int ni;

	MemberInfo(CP pool, int ci, int ni) {
		this.pool = pool;
		this.ci = ci;
		this.ni = ni;
	}

	public String getText() {
		String klass = pool.getInfoText(ci);
		String name = pool.getInfoText(ni);
		if (null == klass || null == name)
			return null;
		return String.format("%s.%s", klass, name);
	}

}
