package org.nutz.java.src;

import java.util.List;

import org.nutz.java.exception.ElementExistsException;

public abstract class JavaElement {

	private String name;

	private List<JavaAnnotation> anns;

	protected JavaElement() {
		anns = Srcs.list(JavaAnnotation.class);
	}

	/**
	 * @return 当前对象的字符描述
	 */
	public abstract String getDescriptor();

	/**
	 * @return 元素名
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            元素名
	 * @return JavaElement
	 */
	public JavaElement setName(String name) {
		this.name = name;
		return this;
	}

	/**
	 * 当前方法是否匹配描述符
	 * 
	 * @param descriptor
	 *            描述符
	 * @return 是否匹配
	 * 
	 * @see org.nutz.java.src.Describable
	 */
	public boolean is(String descriptor) {
		return this.getDescriptor().equals(descriptor);
	}

	/**
	 * 是否和另外一个元素相等
	 * 
	 * @param ele
	 *            另外的元素
	 * @return 是否相等
	 */
	public boolean is(JavaElement ele) {
		if (null == ele)
			return false;
		if (this.getClass() != ele.getClass())
			return false;
		return is(ele.getDescriptor());
	}

	@Override
	public boolean equals(Object obj) {
		if (null == obj)
			return false;
		if (obj instanceof JavaElement)
			return false;
		return is((JavaElement) obj);
	}

	@Override
	public int hashCode() {
		return this.getDescriptor().hashCode();
	}

	/**
	 * 增加一个注解
	 * 
	 * @param ann
	 *            注解
	 * @return JavaElement
	 */
	public JavaElement addAnnotation(JavaAnnotation ann) throws ElementExistsException {
		Srcs.assertNoExists(anns, ann);
		return this;
	}

	/**
	 * 移除一个注解
	 * 
	 * @param ann
	 *            注解类型
	 * @return JavaElement
	 */
	public JavaElement removeAnnotation(JavaAnnotationType annType) {
		JavaAnnotation ann = this.getAnnotation(annType);
		if (null != ann)
			anns.remove(ann);
		return this;
	}

	/**
	 * 根据注解类型获得一个注解实例
	 * 
	 * @param annType
	 *            注解类型
	 * @return 注解实例
	 */
	public JavaAnnotation getAnnotation(JavaAnnotationType annType) {
		return Srcs.get(anns, "@" + annType.getDescriptor());
	}

	/**
	 * @return 注解列表，子类专用
	 */
	protected List<JavaAnnotation> annotations() {
		return anns;
	}

	/**
	 * 清除所有的注解
	 * 
	 * @return JavaElement
	 */
	public JavaElement clearAnnotations() {
		anns.clear();
		return this;
	}

	/**
	 * @param annType
	 *            注解类型
	 * @return 是否存在注解
	 */
	public boolean hasAnnotation(JavaAnnotationType annType) {
		return null != getAnnotation(annType);
	}
}
