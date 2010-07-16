package org.nutz.lang.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import org.nutz.aop.ClassDefiner;
import org.nutz.aop.DefaultClassDefiner;
import org.nutz.repo.org.objectweb.asm.ClassWriter;
import org.nutz.repo.org.objectweb.asm.MethodVisitor;
import org.nutz.repo.org.objectweb.asm.Opcodes;
import org.nutz.repo.org.objectweb.asm.Type;
import org.objectweb.asm.util.ASMifierClassVisitor;

public final class FastClassFactory implements Opcodes {

	private ClassDefiner classDefiner = new DefaultClassDefiner(FastClassFactory.class.getClassLoader());

	private int count;

	public static final String MethodArray_FieldName = "_$$Fast_methodArray";
	public static final String ConstructorArray_FieldName = "_$$Fast_constructorArray";
	public static final String SrcClass_FieldName = "_$$Fast_srcClass";

	public synchronized Class<?> create(Class<?> classZ) {
		count++;
		String myName = FastClass.CLASSNAME + count;
		ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
		cw.visit(V1_6, ACC_PUBLIC, myName, null, "org/nutz/lang/reflect/AbstractFastClass", null);
		// 添加默认构造方法
		{
			MethodVisitor mv = cw.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
			mv.visitCode();
			mv.visitVarInsn(ALOAD, 0);
			mv.visitMethodInsn(	INVOKESPECIAL,
								"org/nutz/lang/reflect/AbstractFastClass",
								"<init>",
								"()V");
			mv.visitInsn(RETURN);
			mv.visitMaxs(1, 1);
			mv.visitEnd();
		}
		// 添加默认字段
		{
			cw.visitField(	ACC_PUBLIC + ACC_STATIC,
							FastClassFactory.MethodArray_FieldName,
							"[Ljava/lang/reflect/Method;",
							null,
							null).visitEnd();
			cw.visitField(	ACC_PUBLIC + ACC_STATIC,
							ConstructorArray_FieldName,
							"[Ljava/lang/reflect/Constructor;",
							null,
							null).visitEnd();
			cw.visitField(	ACC_PUBLIC + ACC_STATIC,
							SrcClass_FieldName,
							"Ljava/lang/Class;",
							"Ljava/lang/Class<*>;",
							null).visitEnd();
		}
		// 实现默认字段的getter
		{
			MethodVisitor mv = cw.visitMethod(	ACC_PROTECTED,
												"getMethods",
												"()[Ljava/lang/reflect/Method;",
												null,
												null);
			mv.visitCode();
			mv.visitFieldInsn(GETSTATIC, myName, FastClassFactory.MethodArray_FieldName, "[Ljava/lang/reflect/Method;");
			mv.visitInsn(ARETURN);
			mv.visitMaxs(1, 1);
			mv.visitEnd();

			mv = cw.visitMethod(ACC_PROTECTED,
								"getConstructors",
								"()[Ljava/lang/reflect/Constructor;",
								"()[Ljava/lang/reflect/Constructor<*>;",
								null);
			mv.visitCode();
			mv.visitFieldInsn(GETSTATIC, myName, ConstructorArray_FieldName, "[Ljava/lang/reflect/Constructor;");
			mv.visitInsn(ARETURN);
			mv.visitMaxs(1, 1);
			mv.visitEnd();

			mv = cw.visitMethod(ACC_PROTECTED,
								"getSrcClass",
								"()Ljava/lang/Class;",
								"()Ljava/lang/Class<*>;",
								null);
			mv.visitCode();
			mv.visitFieldInsn(GETSTATIC, myName, SrcClass_FieldName, "Ljava/lang/Class;");
			mv.visitInsn(ARETURN);
			mv.visitMaxs(1, 1);
			mv.visitEnd();
		}
		Method[] methods = classZ.getMethods();
		//构建_invoke方法
		{
			String[] methodNames = new String[methods.length];
			String[] descs = new String[methods.length];
			int[] modifies = new int[methods.length];
			int[] invokeOps = new int[methods.length];
			for (int i = 0; i < methods.length; i++) {
				methodNames[i] = methods[i].getName();
				descs[i] = Type.getMethodDescriptor(methods[i]);
				modifies[i] = methods[i].getModifiers();
				if (classZ.isInterface())
					invokeOps[i] = INVOKEINTERFACE;
				else if (Modifier.isAbstract(methods[i].getModifiers()))
					invokeOps[i] = INVOKESPECIAL;
				else if (Modifier.isStatic(methods[i].getModifiers()))
					invokeOps[i] = INVOKESTATIC;
				else 
					invokeOps[i] = INVOKEVIRTUAL;
			}
			new FastClassAdpter(cw.visitMethod(	ACC_PUBLIC + ACC_VARARGS,
												"_invoke",
												"(Ljava/lang/Object;I[Ljava/lang/Object;)Ljava/lang/Object;",
												null,
												null),
								methodNames,
								descs,
								modifies,
								invokeOps,
								classZ.getName().replace('.', '/')).visitCode();
		}

		Constructor<?>[] constructors = classZ.getConstructors();
		if (constructors.length > 0) {

		}
		cw.visitEnd();

		Class<?> xClass = classDefiner.define(myName.replace('/', '.'), cw.toByteArray());
		try {
			xClass.getField(SrcClass_FieldName).set(null, classZ);
			xClass.getField(MethodArray_FieldName).set(null, methods);
			xClass.getField(ConstructorArray_FieldName).set(null, constructors);
		}
		catch (Throwable e) {
			e.printStackTrace();
		}
		return xClass;
	}

	public static void main(String[] args) throws Throwable {
		ASMifierClassVisitor.main(new String[]{"org.nutz.lang.reflect.XXXXXXXXXXX"});
		// System.out.println(Type.getObjectType(AbstractFastClass.class.getName().replace('.',
		// '/')));
	}
}
