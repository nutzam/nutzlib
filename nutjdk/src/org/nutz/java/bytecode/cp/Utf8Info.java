package org.nutz.java.bytecode.cp;


public class Utf8Info implements CPInfo {

	private String str;

	Utf8Info(String str) {
		this.str = str;
	}

	public String getText() {
		return str;
	}

}
