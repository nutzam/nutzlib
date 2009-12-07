package org.nutz.java.src;

/**
 * 注解实例
 * 
 * @author zozoh(zozohtnt@gmail.com)
 */
public class JavaAnnotation extends JavaElement {

	private JavaAnnotationType type;

	/**
	 * @return 注解类型
	 */
	public JavaAnnotationType getType() {
		return type;
	}

	/**
	 * 设置注解类型
	 * 
	 * @param type
	 *            注解类型
	 * @return JavaAnnotation
	 */
	public JavaAnnotation setType(JavaAnnotationType type) {
		this.type = type;
		return this;
	}

	@Override
	public String getDescriptor() {
		return "@" + type.getDescriptor();
	}

}
