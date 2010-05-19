package org.nutz.aop2.tmp;

import javax.swing.JFrame;

import org.nutz.castor.Castors;
import org.nutz.log.Log;

public class TTT {
	
	String name;

	public void X (){
//		new Throwable().printStackTrace(System.out);
		System.out.println("XXX");
	}
	
	public int XY (){
//		new Throwable().printStackTrace(System.out);
		System.out.println("XXX");
		return 1;
	}
	
	public boolean XZ (){
//		new Throwable().printStackTrace(System.out);
		System.out.println("XXX");
		return true;
	}
	
	public void X2 (String x, int y){
//		new Throwable().printStackTrace(System.out);
		System.out.println("XXX");
	}
	
	public Object xxx(){
		return null;
	}
	

	/**
	 * 无参数,无返回
	 */
	public void nonArgsVoid() throws Throwable {
		System.out.println("My - " + name + " >> nonArgsVoid");
	}

	protected void voidZ() {}

	/**
	 * 有一个参数,无返回
	 */
	public void argsVoid(String x) {
		System.out.println("My - " + name + " >> argsVoid");
	}

	/**
	 * 有多个参数,无返回
	 */
	public void mixObjectsVoid(String x, Object obj, Integer i, JFrame f) {
		System.out.println("My - " + name + " >> mixObjectsVoid");
	}

	/**
	 * 有多个参数,无返回
	 */
	public void mixArgsVoid(String x, Object obj, int yy, char xp, long... z) {
		System.out.println("My - "
							+ name
							+ " >> mixArgsVoid"
							+ " 我的参数"
							+ Castors.me().castToString(new Object[]{x, obj, yy, xp, z}));
	}

	/**
	 * 有多个参数,无返回
	 */
	public void mixArgsVoid2(	String x,
								Object obj,
								int yy,
								char xp,
								long bb,
								boolean ser,
								char xzzz,
								String ppp,
								StringBuffer sb,
								Log log,
								long... z) throws Throwable {
		System.out.println("My - " + name + " >> mixArgsVoid2");
	}
}
