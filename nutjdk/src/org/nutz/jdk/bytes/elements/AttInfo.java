package org.nutz.jdk.bytes.elements;

abstract class AttInfo {

	public static final String ConstantValue = "ConstantValue", Code = "Code",
			Exceptions = "Exceptions", InnerClasses = "InnerClasses", Synthetic = "Synthetic",
			SourceFile = "SourceFile", LineNumberTable = "LineNumberTable",
			LocalVariableTable = "LocalVariableTable", Deprecated = "Deprecated";

	protected short attribute_name_index;
	protected int attribute_length;

	public short getAttribute_name_index() {
		return attribute_name_index;
	}

	public AttInfo setAttribute_name_index(short attribute_name_index) {
		this.attribute_name_index = attribute_name_index;
		return this;
	}

	public int getAttribute_length() {
		return attribute_length;
	}

	public AttInfo setAttribute_length(int attribute_length) {
		this.attribute_length = attribute_length;
		return this;
	}
}