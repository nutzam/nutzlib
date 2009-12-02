package org.nutz.jdk.bytes.elements;

import org.nutz.jdk.bytes.attr.AttrInfo;

public class MethodInfo {

	private short accessFlags;
	private short nameIndex;
	private short descriptorIndex;
	private short attributesCount;
	private AttrInfo attributes[];

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

	public short getAttributesCount() {
		return attributesCount;
	}

	public MethodInfo setAttributesCount(short attributesCount) {
		this.attributesCount = attributesCount;
		return this;
	}

	public AttrInfo[] getAttributes() {
		return attributes;
	}

	public MethodInfo setAttributes(AttrInfo[] attributes) {
		this.attributes = attributes;
		return this;
	}

}
