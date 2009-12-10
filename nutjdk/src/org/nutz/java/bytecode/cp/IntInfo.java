package org.nutz.java.bytecode.cp;

public class IntInfo implements CPInfo {

	private int num;

	public IntInfo(int num) {
		this.num = num;
	}

	public String getText() {
		return "" + num;
	}

}
