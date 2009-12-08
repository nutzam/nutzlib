package org.nutz.jdk.bytes.elements;

import org.nutz.jdk.bytes.attr.Attr;

public class MethodInfo {

	private int accFlag;
	private int nameIdx;
	private int desIdx;
	private int attrCount;
	private Attr attrs[];

	public int getAccFlag() {
		return accFlag;
	}

	public void setAccFlag(int accFlag) {
		this.accFlag = accFlag;
	}

	public int getNameIdx() {
		return nameIdx;
	}

	public void setNameIdx(int nameIdx) {
		this.nameIdx = nameIdx;
	}

	public int getDesIdx() {
		return desIdx;
	}

	public void setDesIdx(int desIdx) {
		this.desIdx = desIdx;
	}

	public int getAttrCount() {
		return attrCount;
	}

	public void setAttrCount(int attrCount) {
		this.attrCount = attrCount;
	}

	public Attr[] getAttrs() {
		return attrs;
	}

	public void setAttrs(Attr[] attrs) {
		this.attrs = attrs;
	}

}
