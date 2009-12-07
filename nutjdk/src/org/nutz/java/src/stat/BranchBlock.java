package org.nutz.java.src.stat;

import java.util.List;

import org.nutz.java.src.Srcs;
import org.nutz.lang.Mirror;

public abstract class BranchBlock<T extends JavaBlock> extends JavaBlock {

	private BodyBlock dft;

	private List<T> cases;

	@SuppressWarnings("unchecked")
	public BranchBlock() {
		cases = Srcs.list((Class<T>) Mirror.getTypeParams(getClass())[0]);
	}

	public List<T> cases() {
		return cases;
	}

	public BodyBlock getDefault() {
		return dft;
	}

	public BranchBlock<T> setDefault(BodyBlock dft) {
		this.dft = dft;
		return this;
	}

}
