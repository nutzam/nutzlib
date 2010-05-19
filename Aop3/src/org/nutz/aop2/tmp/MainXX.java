package org.nutz.aop2.tmp;

import java.lang.reflect.Method;

import org.nutz.aop.DefaultClassDefiner;
import org.nutz.aop.MethodMatcherFactory;
import org.nutz.aop2.AsmClassAgent;
import org.nutz.aop2.ClassAgent2;
import org.nutz.aop2.interceptor.LogInterceptor;

public class MainXX {

	public static void main(String[] args) throws Throwable{
		ClassAgent2 agent2 = new AsmClassAgent();
		agent2.addInterceptor(MethodMatcherFactory.matcher(Method.PUBLIC), new LogInterceptor());
		Class<TTT> x =  agent2.define(new DefaultClassDefiner(), TTT.class);
		System.out.println(x.getName());
		System.out.println(x);
//		x.newInstance().X();
//		x.newInstance().XY();
//		x.newInstance().XZ();
//		x.newInstance().X2("",2);
	}
}
