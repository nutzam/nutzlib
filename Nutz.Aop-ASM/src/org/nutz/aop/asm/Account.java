package org.nutz.aop.asm;

public class Account {

	public void operationA() {
		System.out.println("op... " + name);
	}

	protected String name;

	public Account(String name) {
		this.name = name;
	}

	public Account() {

	}

	@SuppressWarnings("unused")
	private Account(int x) {
	}

	static {
		System.out.println("In static ...");
	}

	@SuppressWarnings("unused")
	private void pMethod() {
		System.out.println("Here");
	}

	public static void sMethod() {
	}

	public final void fMethod() {
	}

	void dMethod() {
		System.out.println("dMethod...." + getClass());
	}

	public String xString;
}