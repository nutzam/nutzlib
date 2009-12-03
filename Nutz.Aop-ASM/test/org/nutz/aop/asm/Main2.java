package org.nutz.aop.asm;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.nutz.aop.Aop;
import org.nutz.aop.ClassAgent;
import org.nutz.aop.asm.test.Aop1;
import org.nutz.aop.asm.test.Aop2;
import org.nutz.aop.asm.test.MyMethodInterceptor;
import org.nutz.lang.Mirror;

public class Main2 {

	public static void main(String[] args) {
		try {
			ClassAgent agent = new NutClassGenerator();
			agent.addListener(Aop.matcher(".*"), new MyMethodInterceptor());
			Class<Aop1> classZ = agent.define(Aop1.class);
			System.out.println(classZ);
			Field[] fields = classZ.getDeclaredFields();
			for (Field field : fields) {
				System.out.println("找到一个Field: " + field);
			}
			Method methods[] = classZ.getDeclaredMethods();
			for (Method method : methods) {
				System.out.println("找到一个Method: " + method);
			}
			classZ.newInstance();
			Aop1 a1 = Mirror.me(classZ).born("Wendal");
			a1.nonArgsVoid();
			a1.argsVoid("Wendal is the best!");
			a1.mixObjectsVoid("Arg1", new Object(), 1, null);
			a1.mixArgsVoid("XX", "WendalXXX", 0, 'c', 1L, 9090L);
			a1.mixArgsVoid2("Aop1", Boolean.TRUE, 8888, 'p', 34L, false, 'b', "Gp", null, null, 23L, 90L, 78L);
//			a1.x();
			a1.mixArgsVoid4("WendalXXX");
//			new Aop2("Wendal").mixArgsVoid4(null);
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

}
