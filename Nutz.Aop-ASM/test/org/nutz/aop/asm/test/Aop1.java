package org.nutz.aop.asm.test;

public class Aop1 {
	
	private String name;
	
	public Aop1(String name) {
		this.name = name;
	}
	
	public Aop1() {
	}

	/**
	 * 无参数,无返回
	 */
	public void nonArgsVoid(){
		System.out.println("My - "+ name + " >> nonArgsVoid");
	}
	
	/**
	 * 有一个参数,无返回
	 */
	public void argsVoid(String x){
		System.out.println("My - "+ name + " >> argsVoid");
	}
	
	/**
	 * 有一个参数,无返回
	 */
	public void mixArgsVoid(String x,Object obj,int yy,char xp,long...z){
		System.out.println("My - "+ name + " >> mixArgsVoid");
	}
}
