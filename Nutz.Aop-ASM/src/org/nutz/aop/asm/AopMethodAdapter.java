package org.nutz.aop.asm;

import static org.objectweb.asm.Opcodes.*;

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
		if(Type.getReturnType(desc).equals(Type.VOID_TYPE)){
			enhandMethod_Void(mv, methodName, methodIndex, myName,
				enhancedSuperName);
		}else{
			enhandMethod_Object(mv, methodName, methodIndex, myName, enhancedSuperName);
		}
	}
	
	private void enhandMethod_Void(MethodVisitor mv, String methodName, int methodIndex, String myName, String superName) {
		int lastIndex = getLastIndex();
		
		mv.visitCode();
		Label l0 = new Label();
		Label l1 = new Label();
		Label l2 = new Label();
		mv.visitTryCatchBlock(l0, l1, l2, "java/lang/Exception");
		Label l3 = new Label();
		mv.visitTryCatchBlock(l0, l1, l3, "java/lang/Throwable");
		mv.visitLabel(l0);
		mv.visitVarInsn(ALOAD, 0);
		mv.visitIntInsn(SIPUSH, methodIndex);
		mv.visitIntInsn(BIPUSH, argumentTypes.length);
		mv.visitTypeInsn(ANEWARRAY, "java/lang/Object");
		loadArgsAsArray();
		mv.visitMethodInsn(INVOKESPECIAL, myName, "_Nut_before", "(I[Ljava/lang/Object;)Z");
		Label l4 = new Label();
		mv.visitJumpInsn(IFEQ, l4);
		mv.visitVarInsn(ALOAD, 0);
		loadArgs();
		mv.visitMethodInsn(INVOKESPECIAL, enhancedSuperName, methodName, desc);
		mv.visitVarInsn(ALOAD, 0);
		mv.visitIntInsn(SIPUSH, methodIndex);
		mv.visitInsn(ACONST_NULL);
		mv.visitIntInsn(BIPUSH, argumentTypes.length);
		mv.visitTypeInsn(ANEWARRAY, "java/lang/Object");
		loadArgsAsArray();
		mv.visitMethodInsn(INVOKESPECIAL, myName, "_Nut_after", "(ILjava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;");
		mv.visitInsn(POP);
		mv.visitLabel(l1);
		mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
		mv.visitJumpInsn(GOTO, l4);
		mv.visitLabel(l2);
		mv.visitFrame(Opcodes.F_SAME1, 0, null, 1, new Object[] {"java/lang/Exception"});
		mv.visitVarInsn(ASTORE, lastIndex);
		mv.visitVarInsn(ALOAD, 0);
		mv.visitIntInsn(SIPUSH, methodIndex);
		mv.visitVarInsn(ALOAD, lastIndex);
		mv.visitIntInsn(BIPUSH, argumentTypes.length);
		mv.visitTypeInsn(ANEWARRAY, "java/lang/Object");
		loadArgsAsArray();
		mv.visitMethodInsn(INVOKESPECIAL, myName, "_Nut_Exception", "(ILjava/lang/Exception;[Ljava/lang/Object;)V");
		mv.visitVarInsn(ALOAD, lastIndex);
		mv.visitMethodInsn(INVOKESTATIC, "org/nutz/lang/Lang", "wrapThrow", "(Ljava/lang/Throwable;)Ljava/lang/RuntimeException;");
		mv.visitInsn(ATHROW);
		mv.visitLabel(l3);
		mv.visitFrame(Opcodes.F_SAME1, 0, null, 1, new Object[] {"java/lang/Throwable"});
		mv.visitVarInsn(ASTORE, lastIndex);
		mv.visitVarInsn(ALOAD, 0);
		mv.visitIntInsn(SIPUSH, methodIndex);
		mv.visitVarInsn(ALOAD, lastIndex);
		mv.visitInsn(ACONST_NULL);
		mv.visitIntInsn(BIPUSH, argumentTypes.length);
		mv.visitTypeInsn(ANEWARRAY, "java/lang/Object");
		loadArgsAsArray();
		mv.visitMethodInsn(INVOKESPECIAL, myName, "_Nut_Error", "(ILjava/lang/Throwable;Ljava/lang/Object;[Ljava/lang/Object;)V");
		mv.visitVarInsn(ALOAD, lastIndex);
		mv.visitMethodInsn(INVOKESTATIC, "org/nutz/lang/Lang", "wrapThrow", "(Ljava/lang/Throwable;)Ljava/lang/RuntimeException;");
		mv.visitInsn(ATHROW);
		mv.visitLabel(l4);
		mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
		mv.visitInsn(RETURN);
		mv.visitMaxs(5, 2);
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
	
	int getLastIndex(){
		int index = getArgIndex(0);
		for (int i = 0; i < argumentTypes.length; i++) {
			Type t = argumentTypes[i];
	        index += t.getSize();
		}
		return index;
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
	
	private void enhandMethod_Object(MethodVisitor mv, String methodName, int methodIndex, String myName, String superName) {
		int lastIndex = getLastIndex();
		
		mv.visitCode();
		Label l0 = new Label();
		Label l1 = new Label();
		Label l2 = new Label();
		mv.visitTryCatchBlock(l0, l1, l2, "java/lang/Exception");
		Label l3 = new Label();
		mv.visitTryCatchBlock(l0, l1, l3, "java/lang/Throwable");
		mv.visitLabel(l0);
		mv.visitVarInsn(ALOAD, 0);
		mv.visitIntInsn(SIPUSH, methodIndex);
		mv.visitIntInsn(BIPUSH, argumentTypes.length);
		mv.visitTypeInsn(ANEWARRAY, "java/lang/Object");
		loadArgsAsArray();
		mv.visitMethodInsn(INVOKESPECIAL, myName, "_Nut_before", "(I[Ljava/lang/Object;)Z");
		Label l4 = new Label();
		mv.visitJumpInsn(IFEQ, l4);
		mv.visitVarInsn(ALOAD, 0);
		loadArgs();
		mv.visitMethodInsn(INVOKESPECIAL, superName, methodName, desc);
		mv.visitVarInsn(ASTORE, lastIndex);
		mv.visitVarInsn(ALOAD, 0);
		mv.visitIntInsn(SIPUSH, methodIndex);
		mv.visitVarInsn(ALOAD, lastIndex);
		mv.visitIntInsn(BIPUSH, argumentTypes.length);
		mv.visitTypeInsn(ANEWARRAY, "java/lang/Object");
		loadArgsAsArray();
		mv.visitMethodInsn(INVOKESPECIAL, myName, "_Nut_after", "(ILjava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;");
		mv.visitLabel(l1);
		mv.visitInsn(ARETURN);
		mv.visitLabel(l4);
		mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
		mv.visitInsn(ACONST_NULL);
		mv.visitInsn(ARETURN);
		mv.visitLabel(l2);
		mv.visitFrame(Opcodes.F_SAME1, 0, null, 1, new Object[] {"java/lang/Exception"});
		mv.visitVarInsn(ASTORE, lastIndex);
		mv.visitVarInsn(ALOAD, 0);
		mv.visitIntInsn(SIPUSH, methodIndex);
		mv.visitVarInsn(ALOAD, lastIndex);
		mv.visitIntInsn(BIPUSH, argumentTypes.length);
		mv.visitTypeInsn(ANEWARRAY, "java/lang/Object");
		loadArgsAsArray();
		mv.visitMethodInsn(INVOKESPECIAL, myName, "_Nut_Exception", "(ILjava/lang/Exception;[Ljava/lang/Object;)V");
		mv.visitVarInsn(ALOAD, lastIndex);
		mv.visitMethodInsn(INVOKESTATIC, "org/nutz/lang/Lang", "wrapThrow", "(Ljava/lang/Throwable;)Ljava/lang/RuntimeException;");
		mv.visitInsn(ATHROW);
		mv.visitLabel(l3);
		mv.visitFrame(Opcodes.F_SAME1, 0, null, 1, new Object[] {"java/lang/Throwable"});
		mv.visitVarInsn(ASTORE, lastIndex);
		mv.visitVarInsn(ALOAD, 0);
		mv.visitIntInsn(SIPUSH, methodIndex);
		mv.visitVarInsn(ALOAD, lastIndex);
		mv.visitInsn(ACONST_NULL);
		mv.visitIntInsn(BIPUSH, argumentTypes.length);
		mv.visitTypeInsn(ANEWARRAY, "java/lang/Object");
		loadArgsAsArray();
		mv.visitMethodInsn(INVOKESPECIAL, myName, "_Nut_Error", "(ILjava/lang/Throwable;Ljava/lang/Object;[Ljava/lang/Object;)V");
		mv.visitVarInsn(ALOAD, lastIndex);
		mv.visitMethodInsn(INVOKESTATIC, "org/nutz/lang/Lang", "wrapThrow", "(Ljava/lang/Throwable;)Ljava/lang/RuntimeException;");
		mv.visitInsn(ATHROW);
		mv.visitMaxs(8, 3);
		mv.visitEnd();
	}
}