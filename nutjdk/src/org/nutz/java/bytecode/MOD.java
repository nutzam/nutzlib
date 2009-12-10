package org.nutz.java.bytecode;

/**
 * For more information, please access:
 * <p>
 * <a href=
 * "http://java.sun.com/docs/books/jvms/second_edition/html/ClassFile.doc.html">
 * Virtual Machine Specification</a> <em>(Second Edition)</em>
 * 
 * @author zozoh(zozohtnt@gmail.com)
 */
public enum MOD {
	/**
	 * ACC_PUBLIC 0x0001 Declared public; may be accessed from outside its
	 * package.
	 */
	PUBLIC,
	/**
	 * ACC_PRIVATE 0x0002 Declared private; accessible only within the defining
	 * class.
	 */
	PRIVATE,
	/**
	 * ACC_PROTECTED 0x0004 Declared protected; may be accessed within
	 * subclasses.
	 */
	PROTECTED,
	/**
	 * ACC_STATIC 0x0008 Declared static.
	 */
	STATIC,
	/**
	 * ACC_FINAL 0x0010 Declared final; no subclasses allowed.
	 */
	FINAL,
	/**
	 * <ul>
	 * <li>ACC_SUPER 0x0020 Treat superclass methods specially when invoked by
	 * the invokespecial instruction.
	 * <li>ACC_SYNCHRONIZED 0x0020 Declared synchronized; invocation is wrapped
	 * in a monitor lock.
	 * </ul>
	 */
	SYNCHRONIZED_OR_SUPER,
	/**
	 * ACC_VOLATILE 0x0040 Declared volatile; cannot be cached.
	 */
	VOLATILE,
	/**
	 * ACC_TRANSIENT 0x0080 Declared transient; not written or read by a
	 * persistent object manager.
	 */
	TRANSIENT,
	/**
	 * ACC_NATIVE 0x0100 Declared native; implemented in a language other than
	 * Java.
	 */
	NATIVE,
	/**
	 * ACC_INTERFACE 0x0200 Is an interface, not a class.
	 */
	INTERFACE,
	/**
	 * ACC_ABSTRACT 0x0400 Declared abstract; may not be instantiated.
	 */
	ABSTRACT,
	/**
	 * ACC_STRICT 0x0800 Declared strictfp; floating-point mode is FP-strict
	 */
	STRICT
}
