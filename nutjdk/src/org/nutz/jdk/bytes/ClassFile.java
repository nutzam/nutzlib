package org.nutz.jdk.bytes;

import org.nutz.jdk.bytes.elements.AttInfo;
import org.nutz.jdk.bytes.elements.ConstantPool;
import org.nutz.jdk.bytes.elements.FieldInfo;
import org.nutz.jdk.bytes.elements.JDKVersion;
import org.nutz.jdk.bytes.elements.MethodInfo;

@SuppressWarnings("unused")
public class ClassFile {
	public static final int magic = 0xCAFEBABE;
	private JDKVersion version;
	// u2 length
	private int constantPoolCount;
	private ConstantPool constantPool;

	private short accessFlags;
	// u2, cp index
	private int thisClass;
	private int superClass;
	// u2 length
	private int interfacesCount;
	private int[] interfaces;
	// u2 length
	private short fieldsCount;
	private FieldInfo fields[];
	// u2 length
	private int methodsCount;
	private MethodInfo[] methods;
	// u2 length
	private int attributesCount;
	private AttInfo attributes[];

}
