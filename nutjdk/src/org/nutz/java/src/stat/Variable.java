package org.nutz.java.src.stat;

import org.nutz.java.src.JavaType;

/**
 * 局部变量定义
 * 
 * @author zozoh(zozohtnt@gmail.com)
 */
public class Variable extends JavaStatement {

	private JavaType type;

	private String name;

	public JavaType getType() {
		return type;
	}

	public Variable setType(JavaType type) {
		this.type = type;
		return this;
	}

	public String getName() {
		return name;
	}

	public Variable setName(String name) {
		this.name = name;
		return this;
	}

}
