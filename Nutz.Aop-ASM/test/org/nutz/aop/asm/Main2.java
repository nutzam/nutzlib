package org.nutz.aop.asm;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.nutz.aop.Aop;
import org.nutz.aop.ClassAgent;
import org.nutz.aop.asm.test.Aop1;

public class Main2 {

	public static void main(String[] args) throws Throwable {
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
		
		Aop1 a1 = classZ.newInstance();
		a1.mixArgsVoid(null, null, 0, 'c');
		a1.nonArgsVoid();
		a1.argsVoid(null);
		
		//会出错
		classZ.getConstructor(String.class).newInstance("Wendal");
	}

}
