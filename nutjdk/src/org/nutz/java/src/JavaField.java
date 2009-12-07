package org.nutz.java.src;

/**
 * 字段
 * 
 * @author zozoh(zozohtnt@gmail.com)
 */
public class JavaField extends JavaElement {

	private JavaType type;

	public JavaType getType() {
		return type;
	}

	public void setType(JavaType type) {
		this.type = type;
	}

	public String getDescriptor() {
		return this.getName();
	}

}
