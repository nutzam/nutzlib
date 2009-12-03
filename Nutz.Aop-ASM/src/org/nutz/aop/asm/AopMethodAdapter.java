package org.nutz.aop.asm;

import static org.objectweb.asm.Opcodes.AASTORE;
import static org.objectweb.asm.Opcodes.ACONST_NULL;
import static org.objectweb.asm.Opcodes.ALOAD;
import static org.objectweb.asm.Opcodes.ANEWARRAY;
import static org.objectweb.asm.Opcodes.BIPUSH;
import static org.objectweb.asm.Opcodes.DUP;
import static org.objectweb.asm.Opcodes.ICONST_0;
import static org.objectweb.asm.Opcodes.IFEQ;
import static org.objectweb.asm.Opcodes.INVOKESPECIAL;
import static org.objectweb.asm.Opcodes.INVOKESTATIC;
import static org.objectweb.asm.Opcodes.POP;
import static org.objectweb.asm.Opcodes.RETURN;
import static org.objectweb.asm.Opcodes.SIPUSH;

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

class AopMethodAdapter extends NullMethodAdapter {
	private int methodIndex;

	private String myName;

	private String enhancedSuperName;

	private String methodName;

	public AopMethodAdapter(MethodVisitor mv, int access,String methodName,String desc,
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
		mv.visitIntInsn(BIPUSH, argumentTypes.length);
		mv.visitTypeInsn(ANEWARRAY, "java/lang/Object");
		loadArgsAsArray();
		mv.visitMethodInsn(INVOKESPECIAL, myName, "_Nut_before", "(I[Ljava/lang/Object;)Z");
		Label l0 = new Label();
		mv.visitJumpInsn(IFEQ, l0);
		mv.visitVarInsn(ALOAD, 0);
		loadArgs();
		mv.visitMethodInsn(INVOKESPECIAL, superName, methodName, desc);
		mv.visitVarInsn(ALOAD, 0);
		mv.visitIntInsn(SIPUSH, methodIndex);
		mv.visitInsn(ACONST_NULL);
		mv.visitIntInsn(BIPUSH, argumentTypes.length);
		mv.visitTypeInsn(ANEWARRAY, "java/lang/Object");
		loadArgsAsArray();
		mv.visitMethodInsn(INVOKESPECIAL, myName, "_Nut_after", "(ILjava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;");
		mv.visitInsn(POP);
		mv.visitLabel(l0);
		mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
		mv.visitInsn(RETURN);
		mv.visitMaxs(4, 1);
		mv.visitEnd();
	}

	void loadArgsAsArray(){
		int index = getArgIndex(0);
		for (int i = 0; i < argumentTypes.length; i++) {
			mv.visitInsn(DUP);
			if(i < 6){
				mv.visitInsn(i + ICONST_0);
			}else{
				mv.visitIntInsn(BIPUSH, i);
			}
			Type t = argumentTypes[i];
	        loadInsn(t, index);
	        index += t.getSize();
	        handlePrivateData(t);
			mv.visitInsn(AASTORE);
		}
	}
	
	void handlePrivateData(Type type){
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
		}else{
			// Do nothing
		}
	}
}