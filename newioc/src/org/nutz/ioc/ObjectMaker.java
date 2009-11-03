package org.nutz.ioc;

import org.nutz.ioc.meta.IocObject;

/**
 * 这个接口封装了对象注入逻辑。
 * 
 * @author zozoh(zozohtnt@gmail.com)
 */
public interface ObjectMaker {

	ObjectProxy make(IocContext context, IocObject obj);

}
