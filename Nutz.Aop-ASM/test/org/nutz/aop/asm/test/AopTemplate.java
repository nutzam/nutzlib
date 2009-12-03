package org.nutz.aop.asm.test;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.lang.reflect.Method;
import java.util.List;

import org.nutz.aop.MethodInterceptor;
import org.nutz.aop.asm.NutClassGenerator;
import org.objectweb.asm.util.ASMifierClassVisitor;

public class AopTemplate {

	private static Method[] _$$Nut_methodArray;

	private static List<MethodInterceptor>[] _$$Nut_methodInterceptorList;

	@SuppressWarnings("unused")
	private boolean _Nut_before(int flag_int, Object... args) {
		Method method = _$$Nut_methodArray[flag_int];
		List<MethodInterceptor> miList = _$$Nut_methodInterceptorList[flag_int];
		boolean flag = true;
		for (MethodInterceptor methodInterceptor : miList)
			flag &= methodInterceptor.beforeInvoke(this, method, args);
		return flag;
	}

	@SuppressWarnings("unused")
	private Object _Nut_after(int flag_int, Object src_return, Object... args) {
		Method method = _$$Nut_methodArray[flag_int];
		List<MethodInterceptor> miList = _$$Nut_methodInterceptorList[flag_int];
		for (MethodInterceptor methodInterceptor : miList)
			src_return = methodInterceptor.afterInvoke(this, src_return,
					method, args);
		return src_return;
	}

	public static void main(String[] args) throws Throwable {
		// ASMifierClassVisitor.main(new String[]{AopTemplate.class.getName()});
		ASMifierClassVisitor.main(new String[] { Aop2.class.getName() });
		
	}
}
