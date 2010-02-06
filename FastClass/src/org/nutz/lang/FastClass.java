package org.nutz.lang;

import static org.nutz.aop.asm.org.asm.Opcodes.BIPUSH;
import static org.nutz.aop.asm.org.asm.Opcodes.ICONST_0;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import org.nutz.aop.ClassDefiner;
import org.nutz.aop.DefaultClassDefiner;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

public class FastClass extends AbstractInvoker implements Opcodes{
	
	private static final String SUPERCLASS_NAME = AbstractInvoker.class.getName().replace('.', '/');
	
	private static final ClassDefiner cd = new DefaultClassDefiner();
	
	private Class<?> klass;
	
	private ClassWriter cw;
	
	private Method [] methods;
	
	private String proxyClassName;
	
//	private Class<?> proxyClass;
	
	private AbstractInvoker invoker;
	
	private FastClass(Class<?> klass){
		this.klass = klass;
		proxyClassName = SUPERCLASS_NAME+"$$"+klass.getName().replace('.', '$');
	}
	
	public static void main(String[] args) throws Throwable{
		FastClass fastClass = FastClass.create(AClass.class);
		fastClass.invoke_instant_void(new AClass(), AClass.class.getMethod("pp"));
		fastClass.invoke_instant_void(new AClass(), AClass.class.getMethod("xxx"));
		fastClass.invoke_instant_void(new AClass(), AClass.class.getMethod("yy",Object.class),"Wendal");
		fastClass.invoke_instant_void(new AClass(), AClass.class.getMethod("yy",Object.class),new Object());
	}
	
	public static final FastClass create(Class<?> klass) throws Throwable{
		FastClass fastClass = new FastClass(klass);
		fastClass.init();
		fastClass.preper();
		return fastClass;
	}

	private void init() throws Throwable{
		createClass();
		createMethodField();
		addConstructor();
		createMethods();
		endClass();
	}
	
	private void preper() throws Throwable{
		Constructor<?> constructor = cd.define(this.proxyClassName.replace('/', '.'), this.cw.toByteArray()).getConstructor();
		constructor.setAccessible(true);
		invoker = (AbstractInvoker) constructor.newInstance();
		for (int i = 0; i < methods.length; i++) {
			Field field = invoker.getClass().getField("_method_"+i);
			field.setAccessible(true);
			field.set(invoker, methods[i]);
		}
	}
	
	private void createClass(){
		this.cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
		cw.visit(Opcodes.V1_6, ACC_SUPER, proxyClassName, null, SUPERCLASS_NAME, null);
	}
	
	private void createMethodField(){
		this.methods = Mirror.me(klass).getAllDeclaredMethodsWithoutTop();
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
	
	private void createMethods(){
		MethodVisitor mv = cw.visitMethod(ACC_PUBLIC + ACC_VARARGS, "invoke_instant_void", "(Ljava/lang/Object;Ljava/lang/reflect/Method;[Ljava/lang/Object;)V", null, new String[] { "java/lang/Throwable" });
		mv.visitCode();
//		System.out.println(methods.length);
		for (int i = 0; i < methods.length; i++) {
			Method method = methods[i];
			int modify = method.getModifiers();
			if (Modifier.isPrivate(modify))
				continue;
			if ( (! Modifier.isStatic(modify)) && "void".equals(method.getReturnType().toString())){
				mv.visitFieldInsn(GETSTATIC, proxyClassName, "_method_"+i, "Ljava/lang/reflect/Method;");
				mv.visitVarInsn(ALOAD, 2);
				mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/reflect/Method", "equals", "(Ljava/lang/Object;)Z");
				Label l0 = new Label();
				mv.visitJumpInsn(IFEQ, l0);
				mv.visitVarInsn(ALOAD, 1);
				mv.visitTypeInsn(CHECKCAST, klass.getName().replace('.', '/'));
				{
					Type argumentTypes [] = Type.getArgumentTypes(Type.getMethodDescriptor(method));
					if (argumentTypes.length > 0){
						//加载参数数组
						for (int x = 0; x < argumentTypes.length; ++x) {
							mv.visitVarInsn(ALOAD, 3);
							visitX(mv, i);
							mv.visitInsn(AALOAD);
							Type type = argumentTypes[i];
							System.out.println(type.getClassName());
							mv.visitTypeInsn(CHECKCAST, type.getClassName().replace('.', '/'));
						}
					}
				}
				mv.visitMethodInsn(INVOKEVIRTUAL, klass.getName().replace('.', '/'), method.getName(), Type.getMethodDescriptor(method));
				mv.visitLabel(l0);
				mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
			}
		}
		mv.visitInsn(RETURN);
		mv.visitMaxs(0, 4);
		mv.visitEnd();
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
	public void invoke_instant_void(Object obj, Method method, Object... args)
			throws Throwable {
		invoker.invoke_instant_void(obj, method, args);
	}
}