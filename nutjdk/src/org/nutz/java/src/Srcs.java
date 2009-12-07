package org.nutz.java.src;

import java.util.ArrayList;
import java.util.List;

import org.nutz.java.exception.ElementExistsException;

public class Srcs {

	static <T> List<T> list(Class<T> classOfT) {
		return new ArrayList<T>();
	}

	/**
	 * 确保某一个 Java 元素集合没有某一个元素
	 * 
	 * @param list
	 *            集合
	 * @param je
	 *            元素
	 * @throws ElementExistsException
	 *             如果已存在，抛出该异常
	 */
	static void assertNoExists(List<? extends JavaElement> list, JavaElement je)
			throws ElementExistsException {
		if (null == list || list.size() == 0)
			return;
		if (null != get(list, je.getDescriptor()))
			throw new ElementExistsException(je.getDescriptor());
	}

	/**
	 * 从一个元素集合中获取一个元素
	 * 
	 * @param list
	 *            集合
	 * @param e
	 *            元素描述符
	 * @return 集合元素，如果不存在返回 null
	 */
	static <T extends JavaElement> T get(List<T> list, String descriptor) {
		for (T item : list)
			if (item.is(descriptor))
				return item;
		return null;
	}
}
