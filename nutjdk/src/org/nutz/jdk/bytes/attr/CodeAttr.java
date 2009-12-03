package org.nutz.jdk.bytes.attr;

public class CodeAttr extends Attr {

	private int maxStack;
	private int maxLocals;
	private long codeLength;
	private short code[];
	// /u2 exception_table_length;
	private int excTLength;
	private ExcEntry[] excTable;
	private int attrCount;
	private Attr attrs[];

	public int getMaxStack() {
		return maxStack;
	}

	public void setMaxStack(int maxStack) {
		this.maxStack = maxStack;
	}

	public int getMaxLocals() {
		return maxLocals;
	}

	public void setMaxLocals(int maxLocals) {
		this.maxLocals = maxLocals;
	}

	public long getCodeLength() {
		return codeLength;
	}

	public void setCodeLength(long codeLength) {
		this.codeLength = codeLength;
	}

	public short[] getCode() {
		return code;
	}

	public void setCode(short[] code) {
		this.code = code;
	}

	public int getExcTLength() {
		return excTLength;
	}

	public void setExcTLength(int excTLength) {
		this.excTLength = excTLength;
	}

	public ExcEntry[] getExcTable() {
		return excTable;
	}

	public void setExcTable(ExcEntry[] excTable) {
		this.excTable = excTable;
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
