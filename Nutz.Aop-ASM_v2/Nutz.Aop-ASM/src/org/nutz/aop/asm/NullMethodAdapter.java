package org.nutz.aop.asm;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

public abstract class NullMethodAdapter{
	
	protected String desc;
	
	protected int access;
	
	protected MethodVisitor mv;
	
    /**
     * Argument types of the method visited by this adapter.
     */
    protected final Type[] argumentTypes;

	public NullMethodAdapter(MethodVisitor mv,String desc,int access) {
//		super(mv);
		this.mv = mv;
		this.desc = desc;
		this.access = access;
		argumentTypes = Type.getArgumentTypes(desc);
	}
	
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
    
    protected int getArgIndex(final int arg) {
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
    protected void loadInsn(final Type type, final int index) {
        mv.visitVarInsn(type.getOpcode(Opcodes.ILOAD), index);
    }

}
