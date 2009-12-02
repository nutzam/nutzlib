package org.nutz.aop.asm;

import java.lang.reflect.Method;

import org.nutz.lang.Maths;
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Attribute;
import org.objectweb.asm.ClassAdapter;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
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
			return new ChangeToChildConstructorMethodAdapter(mv,desc,access,
					enhancedSuperName);
		} else {
			int methodIndex = AopToolkit.findMethodIndex(name, desc, methods);
			if (methodIndex > -1) {
				MethodVisitor mv = cv.visitMethod(access, name, desc,
						signature, exceptions);
				return new AopMethodAdapter(mv, name, desc, access,methodIndex, myName,
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



