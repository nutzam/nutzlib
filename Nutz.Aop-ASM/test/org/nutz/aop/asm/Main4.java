package org.nutz.aop.asm;

import org.nutz.aop.asm.test.Aop2;
import org.objectweb.asm.util.ASMifierClassVisitor;

public class Main4 {
	
	public static void main(String[] args) throws Throwable {
		ASMifierClassVisitor.main(new String[] { Aop2.class.getName() });
	}
}
