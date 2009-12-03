package org.nutz.jdk.bytes.attr;

public class ClassEntry {
	private int inIndex;
	private int outIndex;
	private int inNameIndex;
	private int inAccessFlag;

	public int getInIndex() {
		return inIndex;
	}

	public void setInIndex(int inIndex) {
		this.inIndex = inIndex;
	}

	public int getOutIndex() {
		return outIndex;
	}

	public void setOutIndex(int outIndex) {
		this.outIndex = outIndex;
	}

	public int getInNameIndex() {
		return inNameIndex;
	}

	public void setInNameIndex(int inNameIndex) {
		this.inNameIndex = inNameIndex;
	}

	public int getInAccessFlag() {
		return inAccessFlag;
	}

	public void setInAccessFlag(int inAccessFlag) {
		this.inAccessFlag = inAccessFlag;
	}

}
