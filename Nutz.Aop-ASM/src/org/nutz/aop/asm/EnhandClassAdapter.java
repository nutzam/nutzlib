package org.nutz.aop.asm;

import java.lang.reflect.Method;

import org.nutz.lang.Maths;
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Attribute;
import org.objectweb.asm.ClassAdapter;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodAdapter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

class EnhandClassAdapter extends ClassAdapter {
	private String enhancedSuperName;

	private String myName;

	private Method[] methods;

	public EnhandClassAdapter(ClassVisitor cv, String myName, Method[] methods) {
		super(cv);
		this.myName = myName.replace('.', '/');
		this.methods = methods;
	}

	public void visit(final int version, final int access, final String name,
			final String signature, final String superName,
			final String[] interfaces) {
		enhancedSuperName = name; // 父类
		super.visit(version, access, myName, signature, enhancedSuperName,
				interfaces);
	}

	// 重写或过滤Method
	public MethodVisitor visitMethod(final int access, final String name,
			final String desc, final String signature, final String[] exceptions) {

		if (Maths.isMask(access, Opcodes.ACC_ABSTRACT)
				|| Maths.isMask(access, Opcodes.ACC_STATIC)
				|| Maths.isMask(access, Opcodes.ACC_FINAL)
				|| Maths.isMask(access, Opcodes.ACC_PRIVATE))
			return null;

		if (name.equals("<init>")) {
			MethodVisitor mv = cv.visitMethod(access, name, desc, signature,
					exceptions);
			return new ChangeToChildConstructorMethodAdapter(mv,
					enhancedSuperName);
		} else {
			int methodIndex = AopToolkit.findMethodIndex(name, desc, methods);
			if (methodIndex > -1) {
				MethodVisitor mv = cv.visitMethod(access, name, desc,
						signature, exceptions);
				return new AopMethodAdapter(mv, name, methodIndex, myName,
						enhancedSuperName);
			}
		}
		return null;
	}

	@Override
	public FieldVisitor visitField(int arg0, String arg1, String arg2,
			String arg3, Object arg4) {
		return null;
	}

	@Override
	public AnnotationVisitor visitAnnotation(String arg0, boolean arg1) {
		return null;
	}

	@Override
	public void visitAttribute(Attribute arg0) {
	}

	@Override
	public void visitInnerClass(String arg0, String arg1, String arg2, int arg3) {
	}

	@Override
	public void visitOuterClass(String arg0, String arg1, String arg2) {
	}

	@Override
	public void visitEnd() {
		AopToolkit.addFields(cv);
		AopToolkit.addMethods(cv, myName);
		super.visitEnd();
	}

}

class ChangeToChildConstructorMethodAdapter extends MethodAdapter {

	private String superClassName;

	public ChangeToChildConstructorMethodAdapter(MethodVisitor mv,
			String superClassName) {
		super(mv);
		this.superClassName = superClassName;
	}

	public void visitMethodInsn(int opcode, String owner, String name,
			String desc) {
		// 调用父类的方法
		if (opcode == Opcodes.INVOKESPECIAL && name.equals("<init>")) {
			owner = superClassName;
		}
		super.visitMethodInsn(opcode, owner, name, desc);//
	}
}

class AopMethodAdapter extends MethodAdapter {
	private int methodIndex;

	private String myName;

	private String enhancedSuperName;

	private String methodName;

	public AopMethodAdapter(MethodVisitor mv, String methodName,
			int methodIndex, String myName, String enhancedSuperName) {
		super(mv);
		this.methodIndex = methodIndex;
		this.myName = myName;
		this.enhancedSuperName = enhancedSuperName;
		this.methodName = methodName;
	}

	public void visitCode() {
		AopToolkit.enhandMethods(mv, methodName, methodIndex, myName,
				enhancedSuperName);
	}

	@Override
	public AnnotationVisitor visitAnnotation(String arg0, boolean arg1) {
		return null;
	}

	@Override
	public AnnotationVisitor visitAnnotationDefault() {
		return null;
	}

	@Override
	public void visitAttribute(Attribute arg0) {

	}

	@Override
	public void visitEnd() {
	}

	@Override
	public void visitFieldInsn(int arg0, String arg1, String arg2, String arg3) {
	}

	@Override
	public void visitFrame(int arg0, int arg1, Object[] arg2, int arg3,
			Object[] arg4) {
	}

	@Override
	public void visitIincInsn(int arg0, int arg1) {
	}

	@Override
	public void visitInsn(int arg0) {
	}

	@Override
	public void visitIntInsn(int arg0, int arg1) {
	}

	@Override
	public void visitJumpInsn(int arg0, Label arg1) {
	}

	@Override
	public void visitLabel(Label arg0) {
	}

	@Override
	public void visitLdcInsn(Object arg0) {
	}

	@Override
	public void visitLineNumber(int arg0, Label arg1) {
	}

	@Override
	public void visitLocalVariable(String arg0, String arg1, String arg2,
			Label arg3, Label arg4, int arg5) {
	}

	@Override
	public void visitLookupSwitchInsn(Label arg0, int[] arg1, Label[] arg2) {
	}

	@Override
	public void visitMaxs(int arg0, int arg1) {
	}

	@Override
	public void visitMethodInsn(int arg0, String arg1, String arg2, String arg3) {
	}

	@Override
	public void visitMultiANewArrayInsn(String arg0, int arg1) {
	}

	@Override
	public AnnotationVisitor visitParameterAnnotation(int arg0, String arg1,
			boolean arg2) {
		return null;
	}

	@Override
	public void visitTableSwitchInsn(int arg0, int arg1, Label arg2,
			Label[] arg3) {
	}

	@Override
	public void visitTryCatchBlock(Label arg0, Label arg1, Label arg2,
			String arg3) {
	}

	@Override
	public void visitTypeInsn(int arg0, String arg1) {

	}

	@Override
	public void visitVarInsn(int arg0, int arg1) {

	}
}