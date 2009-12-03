package org.nutz.jdk.bytes.attr;

public class Attr {

	public static final String ConstantValue = "ConstantValue", Code = "Code",
			Exceptions = "Exceptions", InnerClasses = "InnerClasses", Synthetic = "Synthetic",
			SourceFile = "SourceFile", LineNumberTable = "LineNumberTable",
			LocalVariableTable = "LocalVariableTable", Deprecated = "Deprecated";

	// u2 attribute_name_index
	private int nameIndex;
	// u4 attribute_length
	private long attrLength;

	public int getNameIndex() {
		return nameIndex;
	}

	public void setNameIndex(int nameIndex) {
		this.nameIndex = nameIndex;
	}

	public long getAttrLength() {
		return attrLength;
	}

	public void setAttrLength(long attrLength) {
		this.attrLength = attrLength;
	}

}