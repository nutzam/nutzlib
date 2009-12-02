package org.nutz.aop.asm;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Attribute;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodAdapter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

public abstract class NullMethodAdapter extends MethodAdapter {
	
	protected String desc;
	
	protected int access;
	
    /**
     * Argument types of the method visited by this adapter.
     */
    private final Type[] argumentTypes;

	public NullMethodAdapter(MethodVisitor mv,String desc,int access) {
		super(mv);
		this.desc = desc;
		this.access = access;
		argumentTypes = Type.getArgumentTypes(desc);
	}
	
	@Override
	public abstract void visitCode() ;
	
    /**
     * Generates the instructions to load all the method arguments on the stack.
     */
    public void loadArgs() {
        loadArgs(0, argumentTypes.length);
    }
    
    public void loadArgs(final int arg, final int count) {
        int index = getArgIndex(arg);
        for (int i = 0; i < count; ++i) {
            Type t = argumentTypes[arg + i];
            loadInsn(t, index);
            index += t.getSize();
        }
    }
    
    private int getArgIndex(final int arg) {
        int index = (access & Opcodes.ACC_STATIC) == 0 ? 1 : 0;
        for (int i = 0; i < arg; i++) {
            index += argumentTypes[i].getSize();
        }
        return index;
    }
    
    /**
     * Generates the instruction to push a local variable on the stack.
     * 
     * @param type the type of the local variable to be loaded.
     * @param index an index in the frame's local variables array.
     */
    private void loadInsn(final Type type, final int index) {
        mv.visitVarInsn(type.getOpcode(Opcodes.ILOAD), index);
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
