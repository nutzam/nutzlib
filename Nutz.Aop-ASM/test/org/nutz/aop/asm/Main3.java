package org.nutz.aop.asm;

import java.io.FileOutputStream;

import org.nutz.aop.asm.test.Aop1;
import org.objectweb.asm.util.ASMifierClassVisitor;

public class Main3 {


	public static void main(String[] args) throws Throwable{
		String newName = Aop1.class.getName().replace('1', '2');
		byte [] data = ClassX.enhandClass(Aop1.class, newName, Aop1.class.getMethods());
		printClass(newName, data);
	}
	
	static void printClass(String newName, byte [] tmpData){
		try {
			FileOutputStream fw = new FileOutputStream(newName + ".class");
			fw.write(tmpData);
			fw.flush();
			fw.close();
			ASMifierClassVisitor.main(new String[] { newName + ".class" });
		} catch (Throwable e1) {
			e1.printStackTrace();
		}
	}
}
