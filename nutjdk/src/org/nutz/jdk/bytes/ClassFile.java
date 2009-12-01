package org.nutz.jdk.bytes;

import org.nutz.jdk.bytes.elements.JDKVersion;
import org.nutz.jdk.bytes.elements.ConstantPool;
import org.nutz.jdk.bytes.elements.FieldInfo;

public class ClassFile {
	public static final int magic = 0xCAFEBABE;
	private JDKVersion version;
	private short constantPoolCount;
	private ConstantPool constantPool;

	private short accessFlags;
	private short thisClass;
	private short superClass;

	private short interfacesCount;
	private short[] interfaces;

	private short fieldsCount;
	private FieldInfo fields[];

	private short methodsCount;
	
	
	
}
