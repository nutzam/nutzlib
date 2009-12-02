package org.nutz.aop.asm;

import static org.objectweb.asm.Opcodes.*;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import org.nutz.aop.MethodInterceptor;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import com.sun.xml.internal.ws.org.objectweb.asm.Type;

public final class AopToolkit {

	public static <T> void injectFieldValue(Class<T> newClass, Method[] methodArray, List<MethodInterceptor>[] methodInterceptorList) {
		try {
			{
				Field field = newClass.getDeclaredField("_$$Nut_methodArray");
				field.setAccessible(true);
				field.set(null, methodArray);
			}
			{
				Field field = newClass.getDeclaredField("_$$Nut_methodInterceptorList");
				field.setAccessible(true);
				field.set(null, methodInterceptorList);
			}
			System.out.println("Aop变量注入成功");
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	public static int findMethodIndex(String name, String desc, Method[] methods) {
		for (int i = 0; i < methods.length; i++) {
			Method method = methods[i];
			if (Type.getMethodDescriptor(method).equals(desc) && method.getName().equals(name))
				return i;
		}
		return -1;
	}

	public static void enhandMethods(MethodVisitor mv, String methodName, int methodIndex, String myName, String superName) {
		enhandMethod_Void(mv, methodName, methodIndex, myName, superName);
	}

	private static void enhandMethod_Void(MethodVisitor mv, String methodName, int methodIndex, String myName, String superName) {
		mv.visitCode();
		mv.visitVarInsn(ALOAD, 0);
		mv.visitIntInsn(SIPUSH, methodIndex);
		mv.visitInsn(ICONST_0);
		mv.visitTypeInsn(ANEWARRAY, "java/lang/Object");
		mv.visitMethodInsn(INVOKESPECIAL, myName, "_Nut_before", "(I[Ljava/lang/Object;)Z");
		Label l0 = new Label();
		mv.visitJumpInsn(IFEQ, l0);
		mv.visitVarInsn(ALOAD, 0);
		mv.visitMethodInsn(INVOKESPECIAL, superName, methodName, "()V");
		mv.visitVarInsn(ALOAD, 0);
		mv.visitIntInsn(SIPUSH, methodIndex);
		mv.visitInsn(ACONST_NULL);
		mv.visitInsn(ICONST_0);
		mv.visitTypeInsn(ANEWARRAY, "java/lang/Object");
		mv.visitMethodInsn(INVOKESPECIAL, myName, "_Nut_after", "(ILjava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;");
		mv.visitInsn(POP);
		mv.visitLabel(l0);
		mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
		mv.visitInsn(RETURN);
		mv.visitMaxs(4, 1);
		mv.visitEnd();
	}

	public static void addFields(ClassVisitor cv) {
		FieldVisitor fv;
		{
			fv = cv.visitField(ACC_PRIVATE + ACC_STATIC, "_$$Nut_methodArray", "[Ljava/lang/reflect/Method;", null, null);
			fv.visitEnd();
		}
		{
			fv = cv.visitField(ACC_PRIVATE + ACC_STATIC, "_$$Nut_methodInterceptorList", "[Ljava/util/List;", "[Ljava/util/List<Lorg/nutz/aop/MethodInterceptor;>;", null);
			fv.visitEnd();
		}
	}

	public static void addMethods(ClassVisitor cv, String _Nut_myName) {
		MethodVisitor mv;
		{
			mv = cv.visitMethod(ACC_PRIVATE + ACC_VARARGS, "_Nut_before", "(I[Ljava/lang/Object;)Z", null, null);
			mv.visitCode();
			mv.visitFieldInsn(GETSTATIC, _Nut_myName, "_$$Nut_methodArray", "[Ljava/lang/reflect/Method;");
			mv.visitVarInsn(ILOAD, 1);
			mv.visitInsn(AALOAD);
			mv.visitVarInsn(ASTORE, 3);
			mv.visitFieldInsn(GETSTATIC, _Nut_myName, "_$$Nut_methodInterceptorList", "[Ljava/util/List;");
			mv.visitVarInsn(ILOAD, 1);
			mv.visitInsn(AALOAD);
			mv.visitVarInsn(ASTORE, 4);
			mv.visitInsn(ICONST_1);
			mv.visitVarInsn(ISTORE, 5);
			mv.visitVarInsn(ALOAD, 4);
			mv.visitMethodInsn(INVOKEINTERFACE, "java/util/List", "iterator", "()Ljava/util/Iterator;");
			mv.visitVarInsn(ASTORE, 7);
			Label l0 = new Label();
			mv.visitJumpInsn(GOTO, l0);
			Label l1 = new Label();
			mv.visitLabel(l1);
			mv.visitFrame(F_FULL, 8, new Object[] { _Nut_myName, INTEGER, "[Ljava/lang/Object;", "java/lang/reflect/Method", "java/util/List", INTEGER, TOP, "java/util/Iterator" }, 0, new Object[] {});
			mv.visitVarInsn(ALOAD, 7);
			mv.visitMethodInsn(INVOKEINTERFACE, "java/util/Iterator", "next", "()Ljava/lang/Object;");
			mv.visitTypeInsn(CHECKCAST, "org/nutz/aop/MethodInterceptor");
			mv.visitVarInsn(ASTORE, 6);
			mv.visitVarInsn(ILOAD, 5);
			mv.visitVarInsn(ALOAD, 6);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitVarInsn(ALOAD, 3);
			mv.visitVarInsn(ALOAD, 2);
			mv.visitMethodInsn(INVOKEINTERFACE, "org/nutz/aop/MethodInterceptor", "beforeInvoke", "(Ljava/lang/Object;Ljava/lang/reflect/Method;[Ljava/lang/Object;)Z");
			mv.visitInsn(IAND);
			mv.visitVarInsn(ISTORE, 5);
			mv.visitLabel(l0);
			mv.visitFrame(F_SAME, 0, null, 0, null);
			mv.visitVarInsn(ALOAD, 7);
			mv.visitMethodInsn(INVOKEINTERFACE, "java/util/Iterator", "hasNext", "()Z");
			mv.visitJumpInsn(IFNE, l1);
			mv.visitVarInsn(ILOAD, 5);
			mv.visitInsn(IRETURN);
			mv.visitMaxs(5, 8);
			mv.visitEnd();
		}
		{
			mv = cv.visitMethod(ACC_PRIVATE + ACC_VARARGS, "_Nut_after", "(ILjava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;", null, null);
			mv.visitCode();
			mv.visitFieldInsn(GETSTATIC, _Nut_myName, "_$$Nut_methodArray", "[Ljava/lang/reflect/Method;");
			mv.visitVarInsn(ILOAD, 1);
			mv.visitInsn(AALOAD);
			mv.visitVarInsn(ASTORE, 4);
			mv.visitFieldInsn(GETSTATIC, _Nut_myName, "_$$Nut_methodInterceptorList", "[Ljava/util/List;");
			mv.visitVarInsn(ILOAD, 1);
			mv.visitInsn(AALOAD);
			mv.visitVarInsn(ASTORE, 5);
			mv.visitVarInsn(ALOAD, 5);
			mv.visitMethodInsn(INVOKEINTERFACE, "java/util/List", "iterator", "()Ljava/util/Iterator;");
			mv.visitVarInsn(ASTORE, 7);
			Label l0 = new Label();
			mv.visitJumpInsn(GOTO, l0);
			Label l1 = new Label();
			mv.visitLabel(l1);
			mv.visitFrame(F_FULL, 8, new Object[] { _Nut_myName, INTEGER, "java/lang/Object", "[Ljava/lang/Object;", "java/lang/reflect/Method", "java/util/List", TOP, "java/util/Iterator" }, 0, new Object[] {});
			mv.visitVarInsn(ALOAD, 7);
			mv.visitMethodInsn(INVOKEINTERFACE, "java/util/Iterator", "next", "()Ljava/lang/Object;");
			mv.visitTypeInsn(CHECKCAST, "org/nutz/aop/MethodInterceptor");
			mv.visitVarInsn(ASTORE, 6);
			mv.visitVarInsn(ALOAD, 6);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitVarInsn(ALOAD, 2);
			mv.visitVarInsn(ALOAD, 4);
			mv.visitVarInsn(ALOAD, 3);
			mv.visitMethodInsn(INVOKEINTERFACE, "org/nutz/aop/MethodInterceptor", "afterInvoke", "(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Method;[Ljava/lang/Object;)Ljava/lang/Object;");
			mv.visitVarInsn(ASTORE, 2);
			mv.visitLabel(l0);
			mv.visitFrame(F_SAME, 0, null, 0, null);
			mv.visitVarInsn(ALOAD, 7);
			mv.visitMethodInsn(INVOKEINTERFACE, "java/util/Iterator", "hasNext", "()Z");
			mv.visitJumpInsn(IFNE, l1);
			mv.visitVarInsn(ALOAD, 2);
			mv.visitInsn(ARETURN);
			mv.visitMaxs(5, 8);
			mv.visitEnd();
		}
	}

}
