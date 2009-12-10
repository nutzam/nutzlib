package org.nutz.java.bytecode.cp;

public class IntInfo extends AbstractCPInfo {

	private int num;

	public IntInfo(int num) {
		this.num = num;
	}

	public String getText() {
		return "int:" + num;
	}

}
