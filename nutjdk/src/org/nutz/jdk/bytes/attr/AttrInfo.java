package org.nutz.jdk.bytes.attr;

public abstract class AttrInfo {

	public static final String ConstantValue = "ConstantValue", Code = "Code",
			Exceptions = "Exceptions", InnerClasses = "InnerClasses", Synthetic = "Synthetic",
			SourceFile = "SourceFile", LineNumberTable = "LineNumberTable",
			LocalVariableTable = "LocalVariableTable", Deprecated = "Deprecated";

	protected short attrNameIndex;
	protected int attrLength;

	public short getAttrNameIndex() {
		return attrNameIndex;
	}

	public AttrInfo setAttrNameIndex(short attrNameIndex) {
		this.attrNameIndex = attrNameIndex;
		return this;
	}

	public int getAttrLength() {
		return attrLength;
	}

	public AttrInfo setAttrLength(int attrLength) {
		this.attrLength = attrLength;
		return this;
	}
}