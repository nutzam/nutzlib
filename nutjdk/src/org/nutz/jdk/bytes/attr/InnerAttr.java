package org.nutz.jdk.bytes.attr;

public class InnerAttr extends Attr {

	private int clsCount;
	private ClassEntry[] clsEntry;

	public int getClsCount() {
		return clsCount;
	}

	public void setClsCount(int clsCount) {
		this.clsCount = clsCount;
	}

	public ClassEntry[] getClsEntry() {
		return clsEntry;
	}

	public void setClsEntry(ClassEntry[] clsEntry) {
		this.clsEntry = clsEntry;
	}

}
