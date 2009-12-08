package org.nutz.aop.asm;

import java.io.FileOutputStream;
import java.lang.reflect.Method;
import java.util.List;

import org.nutz.aop.MethodInterceptor;
import org.nutz.aop.asm.test.Aop1;
import org.nutz.lang.Mirror;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.util.ASMifierClassVisitor;

public final class AopToolkit implements Opcodes{

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

	public static void addFields(ClassVisitor cv) {
		addMethodArrayFiled(cv);
		addMethodInterceptorListField(cv);
	}
	
	static void addMethodArrayFiled(ClassVisitor cv){
		FieldVisitor fv = cv.visitField(ACC_PRIVATE + ACC_STATIC, "_$$Nut_methodArray", "[Ljava/lang/reflect/Method;", null, null);
		fv.visitEnd();
	}
	
	static void addMethodInterceptorListField(ClassVisitor cv){
		FieldVisitor fv = cv.visitField(ACC_PRIVATE + ACC_STATIC, "_$$Nut_methodInterceptorList", "[Ljava/util/List;", "[Ljava/util/List<Lorg/nutz/aop/MethodInterceptor;>;", null);
		fv.visitEnd();
	}
	
	public static void addMethods(ClassVisitor cv, String _Nut_myName) {
		addMethod_before(cv, _Nut_myName);
		addMethod_after(cv, _Nut_myName);
		addMethod_whenExption(cv, _Nut_myName);
		addMethod_whenError(cv, _Nut_myName);
	}
	
	static void addMethod_before(ClassVisitor cv, String _Nut_myName){
		MethodVisitor mv = cv.visitMethod(ACC_PRIVATE + ACC_VARARGS, "_Nut_before", "(I[Ljava/lang/Object;)Z", null, null);
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

	private static void addMethod_after(ClassVisitor cv, String _Nut_myName) {
		MethodVisitor mv = cv.visitMethod(ACC_PRIVATE + ACC_VARARGS, "_Nut_after", "(ILjava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;", null, null);
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

	private static void addMethod_whenExption(ClassVisitor cw, String _Nut_myName) {
		MethodVisitor mv = cw.visitMethod(ACC_PRIVATE + ACC_VARARGS, "_Nut_Exception", "(ILjava/lang/Exception;[Ljava/lang/Object;)Z", null, null);
		mv.visitCode();
		mv.visitFieldInsn(GETSTATIC, _Nut_myName, "_$$Nut_methodArray", "[Ljava/lang/reflect/Method;");
		mv.visitVarInsn(ILOAD, 1);
		mv.visitInsn(AALOAD);
		mv.visitVarInsn(ASTORE, 4);
		mv.visitFieldInsn(GETSTATIC, _Nut_myName, "_$$Nut_methodInterceptorList", "[Ljava/util/List;");
		mv.visitVarInsn(ILOAD, 1);
		mv.visitInsn(AALOAD);
		mv.visitVarInsn(ASTORE, 5);
		mv.visitInsn(ICONST_1);
		mv.visitVarInsn(ISTORE, 6);
		mv.visitVarInsn(ALOAD, 5);
		mv.visitMethodInsn(INVOKEINTERFACE, "java/util/List", "iterator", "()Ljava/util/Iterator;");
		mv.visitVarInsn(ASTORE, 8);
		Label l0 = new Label();
		mv.visitJumpInsn(GOTO, l0);
		Label l1 = new Label();
		mv.visitLabel(l1);
		mv.visitFrame(Opcodes.F_FULL, 9, new Object[] {_Nut_myName, Opcodes.INTEGER, "java/lang/Exception", "[Ljava/lang/Object;", "java/lang/reflect/Method", "java/util/List", Opcodes.INTEGER, Opcodes.TOP, "java/util/Iterator"}, 0, new Object[] {});
		mv.visitVarInsn(ALOAD, 8);
		mv.visitMethodInsn(INVOKEINTERFACE, "java/util/Iterator", "next", "()Ljava/lang/Object;");
		mv.visitTypeInsn(CHECKCAST, "org/nutz/aop/MethodInterceptor");
		mv.visitVarInsn(ASTORE, 7);
		mv.visitVarInsn(ILOAD, 6);
		mv.visitVarInsn(ALOAD, 7);
		mv.visitVarInsn(ALOAD, 2);
		mv.visitVarInsn(ALOAD, 0);
		mv.visitVarInsn(ALOAD, 4);
		mv.visitVarInsn(ALOAD, 3);
		mv.visitMethodInsn(INVOKEINTERFACE, "org/nutz/aop/MethodInterceptor", "whenException", "(Ljava/lang/Exception;Ljava/lang/Object;Ljava/lang/reflect/Method;[Ljava/lang/Object;)Z");
		mv.visitInsn(IAND);
		mv.visitVarInsn(ISTORE, 6);
		mv.visitLabel(l0);
		mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
		mv.visitVarInsn(ALOAD, 8);
		mv.visitMethodInsn(INVOKEINTERFACE, "java/util/Iterator", "hasNext", "()Z");
		mv.visitJumpInsn(IFNE, l1);
		mv.visitVarInsn(ILOAD, 6);
		mv.visitInsn(IRETURN);
		mv.visitMaxs(6, 9);
		mv.visitEnd();
	}

	private static void addMethod_whenError(ClassVisitor cw, String _Nut_myName) {
		MethodVisitor mv = cw.visitMethod(ACC_PRIVATE + ACC_VARARGS, "_Nut_Error", "(ILjava/lang/Throwable;[Ljava/lang/Object;)Z", null, null);
		mv.visitCode();
		mv.visitFieldInsn(GETSTATIC, _Nut_myName, "_$$Nut_methodArray", "[Ljava/lang/reflect/Method;");
		mv.visitVarInsn(ILOAD, 1);
		mv.visitInsn(AALOAD);
		mv.visitVarInsn(ASTORE, 4);
		mv.visitFieldInsn(GETSTATIC, _Nut_myName, "_$$Nut_methodInterceptorList", "[Ljava/util/List;");
		mv.visitVarInsn(ILOAD, 1);
		mv.visitInsn(AALOAD);
		mv.visitVarInsn(ASTORE, 5);
		mv.visitInsn(ICONST_1);
		mv.visitVarInsn(ISTORE, 6);
		mv.visitVarInsn(ALOAD, 5);
		mv.visitMethodInsn(INVOKEINTERFACE, "java/util/List", "iterator", "()Ljava/util/Iterator;");
		mv.visitVarInsn(ASTORE, 8);
		Label l0 = new Label();
		mv.visitJumpInsn(GOTO, l0);
		Label l1 = new Label();
		mv.visitLabel(l1);
		mv.visitFrame(Opcodes.F_FULL, 9, new Object[] {_Nut_myName, Opcodes.INTEGER, "java/lang/Throwable", "[Ljava/lang/Object;", "java/lang/reflect/Method", "java/util/List", Opcodes.INTEGER, Opcodes.TOP, "java/util/Iterator"}, 0, new Object[] {});
		mv.visitVarInsn(ALOAD, 8);
		mv.visitMethodInsn(INVOKEINTERFACE, "java/util/Iterator", "next", "()Ljava/lang/Object;");
		mv.visitTypeInsn(CHECKCAST, "org/nutz/aop/MethodInterceptor");
		mv.visitVarInsn(ASTORE, 7);
		mv.visitVarInsn(ILOAD, 6);
		mv.visitVarInsn(ALOAD, 7);
		mv.visitVarInsn(ALOAD, 2);
		mv.visitVarInsn(ALOAD, 0);
		mv.visitVarInsn(ALOAD, 4);
		mv.visitVarInsn(ALOAD, 3);
		mv.visitMethodInsn(INVOKEINTERFACE, "org/nutz/aop/MethodInterceptor", "whenError", "(Ljava/lang/Throwable;Ljava/lang/Object;Ljava/lang/reflect/Method;[Ljava/lang/Object;)Z");
		mv.visitInsn(IAND);
		mv.visitVarInsn(ISTORE, 6);
		mv.visitLabel(l0);
		mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
		mv.visitVarInsn(ALOAD, 8);
		mv.visitMethodInsn(INVOKEINTERFACE, "java/util/Iterator", "hasNext", "()Z");
		mv.visitJumpInsn(IFNE, l1);
		mv.visitVarInsn(ILOAD, 6);
		mv.visitInsn(IRETURN);
		mv.visitMaxs(6, 9);
		mv.visitEnd();
	}

}
