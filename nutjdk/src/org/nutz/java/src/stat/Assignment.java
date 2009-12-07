package org.nutz.java.src.stat;

/**
 * 赋值
 * 
 * @author zozoh(zozohtnt@gmail.com)
 */
public class Assignment extends JavaStatement {

	private Variable left;

	private Expression right;

	public Variable getLeft() {
		return left;
	}

	public Assignment setLeft(Variable left) {
		this.left = left;
		return this;
	}

	public Expression getRight() {
		return right;
	}

	public Assignment setRight(Expression right) {
		this.right = right;
		return this;
	}

}
