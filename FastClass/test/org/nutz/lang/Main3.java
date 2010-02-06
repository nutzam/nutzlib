package org.nutz.lang;

import java.io.FileOutputStream;

import org.objectweb.asm.util.ASMifierClassVisitor;

public class Main3 {
	
	public static void printClass(String newName, byte [] tmpData){
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
	
	public static void main(String[] args) throws Throwable{
		ASMifierClassVisitor.main(new String[]{XX.class.getName()});
	}
}
