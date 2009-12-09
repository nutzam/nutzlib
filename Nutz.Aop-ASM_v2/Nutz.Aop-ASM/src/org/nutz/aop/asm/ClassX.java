package org.nutz.aop.asm;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

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
	
	protected void addConstructors(){
		Constructor<?> [] constructors = klass.getDeclaredConstructors();
		for (int i = 0; i < constructors.length; i++) {
			Constructor<?> constructor = constructors[i];
			if(Modifier.isPrivate(constructor.getModifiers()))
				continue;
			addConstructor(constructor);
		}
	}
	
	protected void addConstructor(Constructor<?> constructor){
		String [] expClasses = convertExp(constructor.getExceptionTypes());
		String desc = Type.getConstructorDescriptor(constructor);
		int access = getAccess(constructor);
		MethodVisitor mv = cw.visitMethod(access, "<init>", desc,null, expClasses);
		new ChangeToChildConstructorMethodAdapter(mv,desc,access,enhancedSuperName).visitCode();
	}
	
	private String [] convertExp(Class<?> [] expClasses){
		if(expClasses.length == 0) return null;
		String [] results = new String[expClasses.length];
		for (int i = 0; i < results.length; i++) {
			results[i] = expClasses[i].getName().replace('.', '/');
		}
		return results;
	}
	
	protected void enhandMethod() {
		for (Method method : methodArray) {
			String methodName = method.getName();
			String methodDesc = Type.getMethodDescriptor(method);
			int methodAccess = getAccess(method);
			MethodVisitor mv = cw.visitMethod(methodAccess, methodName, 
					methodDesc,null, convertExp(method.getExceptionTypes()));
			int methodIndex = findMethodIndex(methodName, methodDesc, methodArray);
			new AopMethodAdapter(mv,methodAccess,methodName,
					methodDesc,methodIndex,
					myName,enhancedSuperName).visitCode();
		}
	}
	
	protected int getAccess(Method method) {
		if(Modifier.isProtected(method.getModifiers()))
			return ACC_PROTECTED;
		if(Modifier.isPublic(method.getModifiers()))
			return ACC_PUBLIC;
		return 0x00;
	}
	
	protected int getAccess(Constructor<?> constructor) {
		if(Modifier.isProtected(constructor.getModifiers()))
			return ACC_PROTECTED;
		if(Modifier.isPublic(constructor.getModifiers()))
			return ACC_PUBLIC;
		return 0x00;
	}
	
	protected static int findMethodIndex(String name, String desc, Method[] methods) {
		for (int i = 0; i < methods.length; i++) {
			Method method = methods[i];
			if (Type.getMethodDescriptor(method).equals(desc) && method.getName().equals(name))
				return i;
		}
		return -1;
	}

	public byte[] toByteArray(){
		addConstructors();
		enhandMethod();
		return cw.toByteArray();
	}
	
	public static byte [] enhandClass(Class<?> kclass,String myName,Method[] methodArray){
		return new ClassX(kclass,myName,methodArray).toByteArray();
	}
	
}
