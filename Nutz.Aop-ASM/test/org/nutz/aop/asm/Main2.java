package org.nutz.aop.asm;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.nutz.aop.Aop;
import org.nutz.aop.ClassAgent;

public class Main2 {

	public static void main(String[] args) throws Throwable {
		ClassAgent agent = new NutClassGenerator();
		agent.addListener(Aop.matcher(".*"), new MyMethodInterceptor());
		Class<Account> classZ = agent.define(Account.class);
		classZ.getConstructor(String.class).newInstance("Wendal").operationA();
		System.out.println(classZ);
		System.out.println(classZ.getField("xString"));
		Field[] fields = classZ.getDeclaredFields();
		for (Field field : fields) {
			System.out.println("找到一个Field: " + field);
		}
		Method methods[] = classZ.getDeclaredMethods();
		for (Method method : methods) {
			System.out.println("找到一个Method: " + method);
		}
		classZ.getConstructor(String.class).newInstance("Wendal").dMethod();
	}

}
