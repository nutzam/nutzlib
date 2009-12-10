package org.nutz.java.bytecode.cp;


public class Utf8Info extends AbstractCPInfo {

	private String str;

	Utf8Info(String str) {
		this.str = str;
	}

	public String getText() {
		return str;
	}

}
