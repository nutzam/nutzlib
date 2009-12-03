package org.nutz.aop.asm;

import static org.objectweb.asm.Opcodes.AALOAD;
import static org.objectweb.asm.Opcodes.ACC_PRIVATE;
import static org.objectweb.asm.Opcodes.ACC_STATIC;
import static org.objectweb.asm.Opcodes.ACC_VARARGS;
import static org.objectweb.asm.Opcodes.ALOAD;
import static org.objectweb.asm.Opcodes.ARETURN;
import static org.objectweb.asm.Opcodes.ASTORE;
import static org.objectweb.asm.Opcodes.CHECKCAST;
import static org.objectweb.asm.Opcodes.F_FULL;
import static org.objectweb.asm.Opcodes.F_SAME;
import static org.objectweb.asm.Opcodes.GETSTATIC;
import static org.objectweb.asm.Opcodes.GOTO;
import static org.objectweb.asm.Opcodes.IAND;
import static org.objectweb.asm.Opcodes.ICONST_1;
import static org.objectweb.asm.Opcodes.IFNE;
import static org.objectweb.asm.Opcodes.ILOAD;
import static org.objectweb.asm.Opcodes.INTEGER;
import static org.objectweb.asm.Opcodes.INVOKEINTERFACE;
import static org.objectweb.asm.Opcodes.IRETURN;
import static org.objectweb.asm.Opcodes.ISTORE;
import static org.objectweb.asm.Opcodes.TOP;

import java.lang.reflect.Method;
import java.util.List;

import org.nutz.aop.MethodInterceptor;
import org.nutz.lang.Mirror;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;

import com.sun.xml.internal.ws.org.objectweb.asm.Type;

public final class AopToolkit {

	public static <T> void injectFieldValue(Class<T> newClass, Method[] methodArray, List<MethodInterceptor>[] methodInterceptorList) {
		try {
			Mirror<T> mirror = Mirror.me(newClass);
			mirror.setValue(null, "_$$Nut_methodArray", methodArray);
			mirror.setValue(null, "_$$Nut_methodInterceptorList", methodInterceptorList);
			System.out.println("Aop变量赋值成功");
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
