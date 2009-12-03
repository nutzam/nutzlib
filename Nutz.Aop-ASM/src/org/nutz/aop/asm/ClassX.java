package org.nutz.aop.asm;

import java.io.FileOutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import org.nutz.aop.asm.test.Aop1;
import org.nutz.lang.Mirror;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.util.ASMifierClassVisitor;

import com.sun.xml.internal.ws.org.objectweb.asm.Type;

public class ClassX implements Opcodes{
	
	private Class<?> klass;
	
	private ClassWriter cw;
	
	private String myName;
	
	private String enhancedSuperName;
	
	private Method[] methodArray ;
	
	protected ClassX(Class<?> kclass,String myName,Method[] methodArray){
		this.klass = kclass;
		this.myName = myName.replace('.', '/');
		this.enhancedSuperName = klass.getName().replace('.', '/');
		this.cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
		cw.visit(V1_6, ACC_PUBLIC, this.myName, "", enhancedSuperName, new String[]{});
		this.methodArray = methodArray;
	}
	
	protected void addField() {
		AopToolkit.addFields(cw);
	}
	
	protected void addConstructors(){
		Constructor<?> [] constructors = klass.getDeclaredConstructors();
		for (int i = 0; i < constructors.length; i++) {
			Constructor<?> constructor = constructors[i];
			if(Modifier.isPrivate(constructor.getModifiers())){
				continue;
			}
			addConstructor(Type.getConstructorDescriptor(constructor));
		}
	}
	
	protected void addConstructor(String desc){
		MethodVisitor mv = cw.visitMethod(ACC_PUBLIC, "<init>", desc,null, null);
		new ChangeToChildConstructorMethodAdapter(mv,desc,ACC_PUBLIC,enhancedSuperName).visitCode();
	}
	
	
	
	protected void addAopMethods() {
		AopToolkit.addMethods(cw, myName);
	}
	
	protected void enhandMethod() {
		for (Method method : methodArray) {
			String methodName = method.getName();
			String methodDesc = Type.getMethodDescriptor(method);
			MethodVisitor mv = cw.visitMethod(ACC_PUBLIC, methodName, 
					methodDesc,null, null);
			int methodIndex = AopToolkit.findMethodIndex(methodName, methodDesc, methodArray);
			new AopMethodAdapter(mv,ACC_PUBLIC,methodName,
					methodDesc,methodIndex,
					myName,enhancedSuperName).visitCode();
		}
	}

	public byte[] toByteArray(){
		addField();
		addConstructors();
		addAopMethods();
		enhandMethod();
		return cw.toByteArray();
	}
	
	public static byte [] enhandClass(Class<?> kclass,String myName,Method[] methodArray){
		return new ClassX(kclass,myName,methodArray).toByteArray();
	}
	
	public static void main(String[] args) throws Throwable{
		String newName = Aop1.class.getName()+"$$Nut";
		byte [] data = ClassX.enhandClass(Aop1.class, newName, new Method[]{});
		Class<?> x = new NutClassGenerator.GeneratorClassLoader().defineClassFromClassFile(newName, data);
		System.out.println(Mirror.me(x).born("Wendal"));
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
