package org.nutz.java.src;

import java.util.List;

import org.nutz.java.exception.ElementExistsException;

/**
 * 泛型
 * 
 * @author zozoh(zozohtnt@gmail.com)
 */
public abstract class JavaParameterizedType extends JavaType {

	/**
	 * 本类型声明的所有方法
	 */
	private List<JavaMethod> methods;

	protected JavaParameterizedType() {
		super();
		methods = Srcs.list(JavaMethod.class);
	}

	/**
	 * @return 当前类型所有的方法
	 */
	public JavaMethod[] getMethods() {
		return methods.toArray(new JavaMethod[methods.size()]);
	}

	/**
	 * @return 当前类型所有的方法列表，供子类使用
	 */
	protected List<JavaMethod> methods() {
		return this.methods;
	}

	/**
	 * 添加一个方法。
	 * 
	 * @param method
	 *            新的方法
	 * @return JavaParameterizedType
	 * @throws ElementExistsException
	 *             如果已经有了该方法
	 */
	public JavaParameterizedType addMethod(JavaMethod method) throws ElementExistsException {
		Srcs.assertNoExists(methods, method);
		methods.add(method);
		return this;
	}

	/**
	 * 根据一个 Java 方法的描述符，获得一个方法
	 * 
	 * @param descriptor
	 *            方法描述符
	 * @return 方法，null 表示不存在
	 * 
	 * @see org.nutz.java.src.Describable
	 */
	public JavaMethod getMethod(String descriptor) {
		return Srcs.get(methods, descriptor);
	}

	/**
	 * @param descriptor
	 *            方法描述符
	 * @return 方法是否存在
	 */
	public boolean hasMethod(String descriptor) {
		return null != getMethod(descriptor);
	}

	/**
	 * 清除当前类型声明的所有方法
	 * 
	 * @return JavaParameterizedType
	 */
	public JavaParameterizedType clearMethods() {
		methods.clear();
		return this;
	}

	/**
	 * 移除一个方法
	 * 
	 * @param method
	 *            方法
	 * @return JavaParameterizedType
	 */
	public JavaParameterizedType removeMethod(JavaMethod method) {
		methods.remove(method);
		return this;
	}

	/**
	 * 移除一个方法
	 * 
	 * @param descriptor
	 *            方法描述符
	 * @return JavaParameterizedType
	 */
	public JavaParameterizedType removeMethod(String descriptor) {
		JavaMethod m = getMethod(descriptor);
		if (null != m)
			return removeMethod(m);
		return this;
	}
}
