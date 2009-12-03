package org.nutz.aop.asm.test;

import javax.swing.JFrame;

import org.nutz.castor.Castors;
import org.nutz.lang.Lang;
import org.nutz.log.Log;

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
	 * 有多个参数,无返回
	 */
	public void mixObjectsVoid(String x,Object obj,Integer i,JFrame f){
		System.out.println("My - "+ name + " >> mixObjectsVoid");
	}
	
	/**
	 * 有多个参数,无返回
	 */
	public void mixArgsVoid(String x,Object obj,int yy,char xp,long...z){
		System.out.println("My - "+ name + " >> mixArgsVoid" 
				+" 我的参数"+Castors.me().castToString(new Object[]{x,obj,yy,xp,z}));
	}
	
	/**
	 * 有多个参数,无返回
	 */
	public void mixArgsVoid2(String x,Object obj,
			int yy,char xp,
			long bb,boolean ser,
			char xzzz,String ppp,
			StringBuffer sb,
			Log log,
			long...z){
		System.out.println("My - "+ name + " >> mixArgsVoid2");
	}
	
	public void  x(){
		throw Lang.noImplement();
	}
}
