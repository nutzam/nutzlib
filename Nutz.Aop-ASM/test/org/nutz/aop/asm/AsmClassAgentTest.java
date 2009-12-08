package org.nutz.aop.asm;

import org.nutz.aop.ClassAgent;
import org.nutz.aop.javassist.JavassistClassAgentTest;

public class AsmClassAgentTest extends JavassistClassAgentTest{

	@Override
	public ClassAgent getNewClassAgent() {
		return new NutClassGenerator();
	}
}
