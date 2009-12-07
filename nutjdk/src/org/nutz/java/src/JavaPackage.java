package org.nutz.java.src;

public class JavaPackage {

	public JavaPackage(String name) {
		this.fullName = name;
		int pos = name.lastIndexOf('.');
		this.name = name.substring(pos + 1);
		this.parent = name.substring(0, pos);
	}

	private String name;

	private String fullName;

	private String parent;

	public String getName() {
		return name;
	}

	public String getFullName() {
		return fullName;
	}

	public JavaPackage getParent() {
		return new JavaPackage(parent);
	}
	
}
