package org.nutz.java.src.stat;

import java.util.List;

import org.nutz.java.src.Srcs;

public class TryCatchBlock extends JavaBlock {

	private BodyBlock tryBlock;

	private List<JavaBlock> catchs;

	private BodyBlock finallyBlock;

	public TryCatchBlock() {
		super();
		catchs = Srcs.list(JavaBlock.class, 10);
	}

	public BodyBlock getTry() {
		return tryBlock;
	}

	public TryCatchBlock setTry(BodyBlock tryBlock) {
		this.tryBlock = tryBlock;
		return this;
	}

	public BodyBlock getFinally() {
		return finallyBlock;
	}

	public TryCatchBlock setFinally(BodyBlock finallyBlock) {
		this.finallyBlock = finallyBlock;
		return this;
	}

	public List<JavaBlock> catchs() {
		return catchs;
	}

}
