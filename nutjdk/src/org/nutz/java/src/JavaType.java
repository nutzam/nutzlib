package org.nutz.java.src;

/**
 * Java 的抽象对象类型
 * 
 * @author zozoh(zozohtnt@gmail.com)
 */
public abstract class JavaType extends JavaElement{

	private JavaPackage _package;

	/**
	 * @return 全名
	 */
	public String getFullName() {
		return _package.getFullName() + "." + getName();
	}

	/**
	 * 设置全名
	 * 
	 * @param name
	 *            全名
	 * @return JavaType
	 */
	public JavaType setFullName(String fullName) {
		int pos = fullName.lastIndexOf('.');
		this._package = new JavaPackage(fullName.substring(0, pos));
		this.setName(fullName.substring(pos + 1));
		return this;
	}

	/**
	 * 设置包
	 * 
	 * @param jp
	 *            包
	 * @return JavaType
	 */
	public JavaType setPackage(JavaPackage jp) {
		_package = jp;
		return this;
	}

	/**
	 * @return 包
	 */
	public JavaPackage getPackage() {
		return _package;
	}

	public String getDescriptor() {
		return "L" + getName() + ";";
	}

}
