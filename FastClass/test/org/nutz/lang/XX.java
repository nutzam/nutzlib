package org.nutz.lang;

import java.lang.reflect.Method;

import org.nutz.aop.ClassAgent;
import org.nutz.aop.ClassDefiner;

public class XX extends AbstractInvoker {

	private static Method _method_0;
	private static Method _method_1;
	
	@Override
	public void invoke_return_void(Object obj, Method method, Object... args)
			throws Throwable {
		System.out.println(args);
		if (_method_0.equals(method)){
			((AClass)obj).pp();
			return;
		}
		if (_method_1.equals(method))
			((AClass)obj).yy((String)args[0]);
//		Object x = args[0];
//		x = args[1];
//		x = args[2];
//		x = args[3];
//		x = args[4];
		yy((String)args[0],(String)args[1],(int[])args[2]);
	}
	
	public void yy(Object x, Object y,Object z){}

	public void xxx(Object vv,String uu,Object...args){
		for (Object object : args) 
			System.out.println(object);;
			((ClassDefiner)args[0]).define(null, null);
			((ClassAgent)args[0]).define(null, null);
			((ClassAgent)args[0]).toString();
	}
	
	public Object pp(){
		return "XXX";
	}
}
