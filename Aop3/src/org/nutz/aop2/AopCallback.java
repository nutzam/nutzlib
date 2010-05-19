package org.nutz.aop2;

public interface AopCallback {
	
	Object invoke(int methodIndex,Object [] args);

}
