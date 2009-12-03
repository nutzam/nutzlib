package org.nutz.jdk.bytes.elements;

import org.nutz.jdk.bytes.attr.Attr;

public class FieldInfo {
	private short access_flags;
	private short name_index;
	private short descriptor_index;
	private short attributes_count;
	private Attr[] attributes;

	public short getAccess_flags() {
		return access_flags;
	}

	public FieldInfo setAccess_flags(short access_flags) {
		this.access_flags = access_flags;
		return this;
	}

	public short getName_index() {
		return name_index;
	}

	public FieldInfo setName_index(short name_index) {
		this.name_index = name_index;
		return this;
	}

	public short getDescriptor_index() {
		return descriptor_index;
	}

	public FieldInfo setDescriptor_index(short descriptor_index) {
		this.descriptor_index = descriptor_index;
		return this;
	}

	public short getAttributes_count() {
		return attributes_count;
	}

	public FieldInfo setAttributes_count(short attributes_count) {
		this.attributes_count = attributes_count;
		return this;
	}

	public Attr[] getAttributes() {
		return attributes;
	}

	public FieldInfo setAttributes(Attr[] attributes) {
		this.attributes = attributes;
		return this;
	}

}
