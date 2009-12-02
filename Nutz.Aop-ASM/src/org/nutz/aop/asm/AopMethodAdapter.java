package org.nutz.aop.asm;

import static org.objectweb.asm.Opcodes.*;

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

class AopMethodAdapter extends NullMethodAdapter {
	private int methodIndex;

	private String myName;

	private String enhancedSuperName;

	private String methodName;

	public AopMethodAdapter(MethodVisitor mv, String methodName,String desc,int access,
			int methodIndex, String myName, String enhancedSuperName) {
		super(mv,desc,access);
		this.methodIndex = methodIndex;
		this.myName = myName;
		this.enhancedSuperName = enhancedSuperName;
		this.methodName = methodName;
	}

	public void visitCode() {
		enhandMethod_Void(mv, methodName, methodIndex, myName,
				enhancedSuperName);
	}
	
	private void enhandMethod_Void(MethodVisitor mv, String methodName, int methodIndex, String myName, String superName) {
		mv.visitCode();
		mv.visitVarInsn(ALOAD, 0);
		mv.visitIntInsn(SIPUSH, methodIndex);
		mv.visitInsn(ICONST_0);
		mv.visitTypeInsn(ANEWARRAY, "java/lang/Object");
		mv.visitMethodInsn(INVOKESPECIAL, myName, "_Nut_before", "(I[Ljava/lang/Object;)Z");
		Label l0 = new Label();
		mv.visitJumpInsn(IFEQ, l0);
		mv.visitVarInsn(ALOAD, 0);
		loadArgs();
		mv.visitMethodInsn(INVOKESPECIAL, superName, methodName, desc);
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

}