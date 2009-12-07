package org.nutz.java.src.stat;

import java.util.List;

import org.nutz.java.src.Srcs;

/**
 * 代码块
 * 
 * @author zozoh(zozohtnt@gmail.com)
 */
public abstract class JavaBlock extends JavaStatement {

	private List<JavaStatement> statements;

	public JavaBlock() {
		statements = Srcs.list(JavaStatement.class, 10);
	}

	public List<JavaStatement> statements() {
		return statements;
	}

}
