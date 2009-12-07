package org.nutz.java.src;

import java.util.List;

/**
 * 类
 * 
 * @author zozoh(zozohtnt@gmail.com)
 */
public class JavaClass extends JavaParameterizedType {

	/**
	 * 静态初始化话方法，相当于 static{...}
	 */
	private JavaMethod initMethod;

	/**
	 * 本类声明的所有字段
	 */
	private List<JavaField> fields;

	/**
	 * 本类所有的构造函数。如果 size() 为 0 ，则表示只有一个默认构造函数
	 */
	private List<JavaMethod> constructors;

	public JavaClass() {
		super();
		fields = Srcs.list(JavaField.class);
		constructors = Srcs.list(JavaMethod.class);
	}

	/**
	 * @return 初始化静态方法
	 */
	public JavaMethod getInitMethod() {
		return initMethod;
	}

	/**
	 * 设置初始化静态方法
	 * 
	 * @param initMethod
	 *            静态方法
	 */
	public void setInitMethod(JavaMethod initMethod) {
		this.initMethod = initMethod;
	}

	/**
	 * 增加一个构造函数
	 * 
	 * @param c
	 *            构造函数
	 * @return JavaClass
	 */
	public JavaClass addConstructor(JavaMethod c) {
		Srcs.assertNoExists(constructors, c);
		constructors.add(c);
		return this;
	}

	/**
	 * 移除所有构造函数
	 * 
	 * @return JavaClass
	 */
	public JavaClass clearConstructors() {
		constructors.clear();
		return this;
	}

	/**
	 * 获取一个构造函数
	 * 
	 * @param descriptor
	 *            构造函数描述符
	 * @return 构造函数
	 */
	public JavaMethod getConstructor(String descriptor) {
		return Srcs.get(constructors, descriptor);
	}

	/**
	 * @param descriptor
	 *            方法描述符
	 * @return 构造函数是否存在
	 */
	public boolean hasConstructor(String descriptor) {
		return null != getConstructor(descriptor);
	}

	/**
	 * 移除一个构造函数
	 * 
	 * @param method
	 *            构造函数
	 * @return JavaClass
	 */
	public JavaClass removeConstructor(JavaMethod c) {
		constructors.remove(c);
		return this;
	}

	/**
	 * 移除一个构造函数
	 * 
	 * @param descriptor
	 *            构造函数描述符
	 * @return JavaClass
	 */
	public JavaClass removeConstructor(String descriptor) {
		JavaMethod c = getConstructor(descriptor);
		if (null != c)
			return removeConstructor(c);
		return this;
	}

	/**
	 * @return 全部构造函数
	 */
	public JavaMethod[] getConstructors() {
		return constructors.toArray(new JavaMethod[constructors.size()]);
	}

	/**
	 * 增加一个字段
	 * 
	 * @param field
	 *            字段
	 * @return JavaClass
	 */
	public JavaClass addfField(JavaField field) {
		Srcs.assertNoExists(fields, field);
		fields.add(field);
		return this;
	}

	/**
	 * 移除一个字段
	 * 
	 * @param field
	 *            字段
	 * @return JavaClass
	 */
	public JavaClass removeField(String name) {
		JavaField f = getField(name);
		if (null != f)
			fields.remove(f);
		return this;
	}

	/**
	 * 清除所有字段
	 * 
	 * @return JavaClass
	 */
	public JavaClass clearFields() {
		fields.clear();
		return this;
	}

	/**
	 * 获取一个字段
	 * 
	 * @param name
	 *            字段名
	 * @return 字段
	 */
	public JavaField getField(String name) {
		return Srcs.get(fields, name);
	}

	/**
	 * @param descriptor
	 *            字段名
	 * @return 是否存在
	 */
	public boolean hasField(String name) {
		return null != getField(name);
	}

	/**
	 * @return 全部字段
	 */
	public JavaField[] getFields() {
		return fields.toArray(new JavaField[fields.size()]);
	}
}
