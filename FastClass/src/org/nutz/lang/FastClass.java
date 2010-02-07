package org.nutz.lang;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;

import org.nutz.aop.ClassDefiner;
import org.nutz.aop.DefaultClassDefiner;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

public class  FastClass<T> extends AbstractInvoker<T> implements Opcodes{
	
	private static final String SUPERCLASS_NAME = AbstractInvoker.class.getName().replace('.', '/');
	
	private static ClassDefiner cd = new DefaultClassDefiner();
	
	private static long ID;
	
	private Class<T> klass;
	
	private ClassWriter cw;
	
	private Method [] methods;
	
	private String proxyClassName;
	
	private AbstractInvoker<T> invoker;
	
	private FastClass(Class<T> klass){
		this.klass = klass;
		proxyClassName = SUPERCLASS_NAME+"_"+getNewId();
	}
	
	private synchronized static long getNewId(){
		if (ID == Long.MAX_VALUE){
			ID = 0;
			cd = new DefaultClassDefiner();
		}else
			ID ++;
		return ID;
	}
	
	public static final <T> FastClass<T> create(Class<T> klass) throws Throwable{
		FastClass<T> fastClass = new FastClass<T>(klass);
		fastClass.init();
		fastClass.preper();
		return fastClass;
	}

	private void init() throws Throwable{
		createClass();
		createMethodField();
		addConstructor();
		createSetMethods();
		createMethods_A();
		createMethods_B();
		endClass();
	}
	
	@SuppressWarnings("unchecked")
	private void preper() throws Throwable{
		Constructor<?> constructor = cd.define(this.proxyClassName.replace('/', '.'), this.cw.toByteArray()).getConstructor();
		constructor.setAccessible(true);
		invoker = (AbstractInvoker) constructor.newInstance();
		invoker.setMethods(methods);
	}
	
	private void createClass(){
		this.cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
		cw.visit(Opcodes.V1_6, ACC_SUPER, proxyClassName, null, SUPERCLASS_NAME, null);
	}
	
	private void createMethodField(){
		ArrayList<Method> methodList = new ArrayList<Method>();
		for (Method method : klass.getDeclaredMethods()) {
			int modify = method.getModifiers();
			if ( ! Modifier.isPublic(modify)) // 只允许访问public方法
				continue;
			if (method.isVarArgs()) //暂时不支持可变参数的方法
				continue;
			methodList.add(method);
		}
		this.methods = methodList.toArray(new Method[methodList.size()]);
		for (int i = 0; i < methods.length; i++) {
			FieldVisitor fv = cw.visitField(ACC_PUBLIC + ACC_STATIC, "_method_"+i, "Ljava/lang/reflect/Method;", null, null);
			fv.visitEnd();
		}
	}
	
	private void addConstructor(){
        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
        mv.visitCode();
        mv.visitVarInsn(ALOAD, 0);
        mv.visitMethodInsn(INVOKESPECIAL, SUPERCLASS_NAME, "<init>", "()V");
        mv.visitInsn(RETURN);
        mv.visitMaxs(1, 1);
        mv.visitEnd();
	}
	
	private void createSetMethods(){
		MethodVisitor mv = cw.visitMethod(ACC_PROTECTED, "setMethods", "([Ljava/lang/reflect/Method;)V", null, null);
		mv.visitCode();
		for (int i = 0; i < methods.length; i++) {
			mv.visitVarInsn(ALOAD, 1);
			visitX(mv, i);
			mv.visitInsn(AALOAD);
			mv.visitFieldInsn(PUTSTATIC, proxyClassName, "_method_"+i, "Ljava/lang/reflect/Method;");
		}
		mv.visitInsn(RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();
	}
	
	private void createMethods_A(){
		MethodVisitor mv = cw.visitMethod(ACC_PUBLIC + ACC_VARARGS, "invoke_return_void", "(Ljava/lang/Object;Ljava/lang/reflect/Method;[Ljava/lang/Object;)V", null, new String[] { "java/lang/Throwable" });
		mv.visitCode();
		for (int i = 0; i < methods.length; i++) {
			Method method = methods[i];
			if (! "void".equals(method.getReturnType().toString()))
				continue;
			Label l0 = commandPart(method,mv,i);
			mv.visitInsn(RETURN);
			mv.visitLabel(l0);
		}
		mv.visitInsn(RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();
	}
	
	private void createMethods_B(){
		MethodVisitor mv = cw.visitMethod(ACC_PUBLIC + ACC_VARARGS, "invoke_return_Object", "(Ljava/lang/Object;Ljava/lang/reflect/Method;[Ljava/lang/Object;)Ljava/lang/Object;", null, new String[] { "java/lang/Throwable" });
		mv.visitCode();
		for (int i = 0; i < methods.length; i++) {
			Method method = methods[i];
			if ("void".equals(method.getReturnType().toString()))
				continue;
			Label l0 = commandPart(method,mv,i);
			//封装基本类型
			Type type = Type.getReturnType(method);
			if(type.equals(Type.BOOLEAN_TYPE)){
				mv.visitMethodInsn(INVOKESTATIC, "java/lang/Boolean", "valueOf", "(Z)Ljava/lang/Boolean;");
			}else if(type.equals(Type.BYTE_TYPE)){
				mv.visitMethodInsn(INVOKESTATIC, "java/lang/Byte", "valueOf", "(B)Ljava/lang/Byte;");
			}else if(type.equals(Type.CHAR_TYPE)){
				mv.visitMethodInsn(INVOKESTATIC, "java/lang/Character", "valueOf", "(C)Ljava/lang/Character;");
			}else if(type.equals(Type.SHORT_TYPE)){
				mv.visitMethodInsn(INVOKESTATIC, "java/lang/Short", "valueOf", "(S)Ljava/lang/Short;");
			}else if(type.equals(Type.INT_TYPE)){
				mv.visitMethodInsn(INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;");
			}else if(type.equals(Type.LONG_TYPE)){
				mv.visitMethodInsn(INVOKESTATIC, "java/lang/Long", "valueOf", "(J)Ljava/lang/Long;");
			}else if(type.equals(Type.FLOAT_TYPE)){
				mv.visitMethodInsn(INVOKESTATIC, "java/lang/Float", "valueOf", "(F)Ljava/lang/Float;");
			}else if(type.equals(Type.DOUBLE_TYPE)){
				mv.visitMethodInsn(INVOKESTATIC, "java/lang/Double", "valueOf", "(D)Ljava/lang/Double;");
			}
			mv.visitInsn(ARETURN);
			mv.visitLabel(l0);
		}
		mv.visitInsn(ACONST_NULL);
		mv.visitInsn(ARETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();
	}

	private Label commandPart(Method method, MethodVisitor mv, int i){
		boolean isStatic = Modifier.isStatic(method.getModifiers());
		mv.visitFieldInsn(GETSTATIC, proxyClassName, "_method_"+i, "Ljava/lang/reflect/Method;");
		mv.visitVarInsn(ALOAD, 2);
		mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/reflect/Method", "equals", "(Ljava/lang/Object;)Z");
		Label l0 = new Label();
		mv.visitJumpInsn(IFEQ, l0);
		if (! isStatic){
			mv.visitVarInsn(ALOAD, 1);
			mv.visitTypeInsn(CHECKCAST, klass.getName().replace('.', '/'));
		}
		Type argumentTypes [] = Type.getArgumentTypes(Type.getMethodDescriptor(method));
		if (argumentTypes.length > 0){
			//加载参数数组
			for (int x = 0; x < argumentTypes.length; ++x) {
				mv.visitVarInsn(ALOAD, 3);
				visitX(mv, x);
				mv.visitInsn(AALOAD);
				Type type = argumentTypes[x];
				String desc = type.getDescriptor();
				if(type.equals(Type.BOOLEAN_TYPE)){
					mv.visitTypeInsn(CHECKCAST, "java/lang/Boolean");
					mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Boolean", "booleanValue", "()Z");
				}else if(type.equals(Type.BYTE_TYPE)){
					mv.visitTypeInsn(CHECKCAST, "java/lang/Byte");
					mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Byte", "byteValue", "()B");
				}else if(type.equals(Type.CHAR_TYPE)){
					mv.visitTypeInsn(CHECKCAST, "java/lang/Character");
					mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Character", "charValue", "()C");
				}else if(type.equals(Type.SHORT_TYPE)){
					mv.visitTypeInsn(CHECKCAST, "java/lang/Short");
					mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Short", "shortValue", "()S");
				}else if(type.equals(Type.INT_TYPE)){
					mv.visitTypeInsn(CHECKCAST, "java/lang/Integer");
					mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Integer", "intValue", "()I");
				}else if(type.equals(Type.LONG_TYPE)){
					mv.visitTypeInsn(CHECKCAST, "java/lang/Long");
					mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Long", "longValue", "()J");
				}else if(type.equals(Type.FLOAT_TYPE)){
					mv.visitTypeInsn(CHECKCAST, "java/lang/Float");
					mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Float", "floatValue", "()F");
				}else if(type.equals(Type.DOUBLE_TYPE)){
					mv.visitTypeInsn(CHECKCAST, "java/lang/Double");
					mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Double", "doubleValue", "()D");
				}else if (desc.startsWith("L"))
						mv.visitTypeInsn(CHECKCAST, desc.substring(1,desc.length() - 1));
					else
						mv.visitTypeInsn(CHECKCAST, desc);
			}
		}
		int invokeOpCode = INVOKEVIRTUAL;
		if (isStatic)
			invokeOpCode = INVOKESTATIC;
		else if (klass.isInterface())
			invokeOpCode = INVOKEINTERFACE;
		mv.visitMethodInsn(invokeOpCode, klass.getName().replace('.', '/'), method.getName(), Type.getMethodDescriptor(method));
		return l0;
	}
	
	static void visitX(MethodVisitor mv,int i){
		if(i < 6){
			mv.visitInsn(i + ICONST_0);
		}else{
			mv.visitIntInsn(BIPUSH, i);
		}
	}
	
	private void endClass(){
		cw.visitEnd();
	}
	
	@Override
	public void invoke_return_void(T obj, Method method, Object... args)
			throws Throwable {
		invoker.invoke_return_void(obj, method, args);
	}
	
	public Object invoke_return_Object(T obj, Method method, Object...args) throws Throwable {
		return invoker.invoke_return_Object(obj, method, args);
	}
	
	/**
	 * 只允许调用public方法
	 * @param obj
	 * @param methodName
	 * @param args
	 * @return
	 * @throws Throwable
	 */
	public Object invoke(T obj,String methodName,Object...args) throws Throwable{
		Class<?> ts [] = new Class<?>[args.length];
		for (int i = 0; i < ts.length; i++) {
			ts[i] = args[i].getClass();
		}
		Method methodZ = obj.getClass().getMethod(methodName, ts);
		if ("void".equals(methodZ.getReturnType().toString())){
			invoker.invoke_return_void(obj, methodZ, args);
			return null;
		}else {
			System.out.println("I am here");
			return invoker.invoke_return_Object(obj, methodZ, args);
		}
	}
}