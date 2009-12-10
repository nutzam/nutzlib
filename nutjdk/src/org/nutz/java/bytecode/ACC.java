package org.nutz.java.bytecode;

import static java.lang.System.out;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import org.nutz.lang.Strings;

/**
 * 关于修饰符的帮助类
 * 
 * @author zozoh(zozohtnt@gmail.com)
 */
public abstract class ACC {

	public static void dump() throws IllegalAccessException {
		for (Field f : ACC.class.getFields()) {
			int mod = f.getModifiers();
			if (Modifier.isStatic(mod))
				if (Modifier.isPublic(mod)) {
					int d = f.getInt(null);
					String bin = Strings.fillBinary(d, 32);
					bin = bin.replace('0', '.');
					out.printf("%s - %s\n", bin, f.getName());
				}
		}
	}

	public static final int PUBLIC = 0x0001;
	public static final int PRIVATE = 0x0002;
	public static final int PROTECTED = 0x0004;
	public static final int STATIC = 0x0008;
	public static final int FINAL = 0x0010;
	public static final int SYNCHRONIZED_OR_SUPER = 0x0020;
	public static final int VOLATILE = 0x0040;
	public static final int TRANSIENT = 0x0080;
	public static final int NATIVE = 0x0100;
	public static final int INTERFACE = 0x0200;
	public static final int ABSTRACT = 0x0400;
	public static final int STRICT = 0x0800;

	/**
	 * @param mods
	 *            位图
	 * @param mask
	 *            给定位图
	 * @return 位图是否完全包括给定位图
	 */
	public static boolean has(int mods, int mask) {
		return (mods & mask) == mask;
	}

	/**
	 * @param mods
	 *            位图
	 * @param m
	 *            修饰符
	 * @return 位图是否有一个修饰符
	 */
	public static boolean has(int mods, MOD m) {
		return 1 == ((mods >> m.ordinal()) & 1);
	}

	public static boolean isDefault(int mods) {
		return 0 == (mods & 7);
	}

	public static boolean isPublic(int mods) {
		return has(mods, MOD.PUBLIC);
	}

	public static boolean isPrivate(int mods) {
		return has(mods, MOD.PRIVATE);
	}

	public static boolean isProtected(int mods) {
		return has(mods, MOD.PROTECTED);
	}

	public static boolean isStatic(int mods) {
		return has(mods, MOD.STATIC);
	}

	public static boolean isFinal(int mods) {
		return has(mods, MOD.FINAL);
	}

	public static boolean isSynchronized_or_super(int mods) {
		return has(mods, MOD.SYNCHRONIZED_OR_SUPER);
	}

	public static boolean isVolatile(int mods) {
		return has(mods, MOD.VOLATILE);
	}

	public static boolean isTransient(int mods) {
		return has(mods, MOD.TRANSIENT);
	}

	public static boolean isNative(int mods) {
		return has(mods, MOD.NATIVE);
	}

	public static boolean isInterface(int mods) {
		return has(mods, MOD.INTERFACE);
	}

	public static boolean isAbstract(int mods) {
		return has(mods, MOD.ABSTRACT);
	}

	public static boolean isStrict(int mods) {
		return has(mods, MOD.STRICT);
	}

}
