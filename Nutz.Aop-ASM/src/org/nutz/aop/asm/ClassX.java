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
			addConstructor(constructor);
		}
	}
	
	protected void addConstructor(Constructor<?> constructor){
		String desc = Type.getConstructorDescriptor(constructor);
		int access = getAccess(constructor);
		MethodVisitor mv = cw.visitMethod(access, "<init>", desc,null, null);
		new ChangeToChildConstructorMethodAdapter(mv,desc,access,enhancedSuperName).visitCode();
	}
	
	
	
	protected void addAopMethods() {
		AopToolkit.addMethods(cw, myName);
	}
	
	protected void enhandMethod() {
		for (Method method : methodArray) {
			String methodName = method.getName();
			String methodDesc = Type.getMethodDescriptor(method);
			int methodAccess = getAccess(method);
			MethodVisitor mv = cw.visitMethod(methodAccess, methodName, 
					methodDesc,null, null);
			int methodIndex = findMethodIndex(methodName, methodDesc, methodArray);
			new AopMethodAdapter(mv,methodAccess,methodName,
					methodDesc,methodIndex,
					myName,enhancedSuperName).visitCode();
		}
	}
	
	protected int getAccess(Method method) {
		int methodAccess = ACC_PUBLIC;
		if(Modifier.isProtected(method.getModifiers()))
			methodAccess = ACC_PROTECTED;
		return methodAccess;
	}
	
	protected int getAccess(Constructor<?> constructor) {
		int methodAccess = 0x00; //缺省
		if(Modifier.isProtected(constructor.getModifiers()))
			methodAccess = ACC_PROTECTED;
		if(Modifier.isPublic(constructor.getModifiers()))
			methodAccess = ACC_PUBLIC;
		return methodAccess;
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
		addField();
		addConstructors();
		addAopMethods();
		enhandMethod();
		return cw.toByteArray();
	}
	
	public static byte [] enhandClass(Class<?> kclass,String myName,Method[] methodArray){
		return new ClassX(kclass,myName,methodArray).toByteArray();
	}
	
}
