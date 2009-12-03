package org.nutz.jdk.bytes.attr;

public class LocalVarAttr extends Attr {

	private int length;
	private LocalVarEntry[] lvEntrys;

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public LocalVarEntry[] getLvEntrys() {
		return lvEntrys;
	}

	public void setLvEntrys(LocalVarEntry[] lvEntrys) {
		this.lvEntrys = lvEntrys;
	}

}
