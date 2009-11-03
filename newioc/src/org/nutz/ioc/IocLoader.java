package org.nutz.ioc;

import org.nutz.ioc.meta.IocObject;

public interface IocLoader {

	String[] getName();
	
	IocObject load(String name);
	
	boolean has(String name);
	
}
