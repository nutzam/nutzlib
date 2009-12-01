package org.nutz.jdk.bytes.elements;

public class MethodInfo {

	private short accessFlags;
	private short nameIndex;
	private short descriptorIndex;
	private short attributes_count;
	private AttInfo attributes[];

	public short getAccessFlags() {
		return accessFlags;
	}

	public MethodInfo setAccessFlags(short accessFlags) {
		this.accessFlags = accessFlags;
		return this;
	}

	public short getNameIndex() {
		return nameIndex;
	}

	public MethodInfo setNameIndex(short nameIndex) {
		this.nameIndex = nameIndex;
		return this;
	}

	public short getDescriptorIndex() {
		return descriptorIndex;
	}

	public MethodInfo setDescriptorIndex(short descriptorIndex) {
		this.descriptorIndex = descriptorIndex;
		return this;
	}

	public short getAttributes_count() {
		return attributes_count;
	}

	public MethodInfo setAttributes_count(short attributesCount) {
		attributes_count = attributesCount;
		return this;
	}

	public AttInfo[] getAttributes() {
		return attributes;
	}

	public MethodInfo setAttributes(AttInfo[] attributes) {
		this.attributes = attributes;
		return this;
	}

}
