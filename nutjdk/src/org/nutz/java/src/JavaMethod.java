package org.nutz.java.src;

import org.nutz.java.src.stat.BodyBlock;
import org.nutz.lang.Lang;

/**
 * 方法
 * 
 * @author zozoh(zozohtnt@gmail.com)
 */
public class JavaMethod extends JavaElement {

	private BodyBlock body;

	public BodyBlock getBody() {
		return body;
	}

	public JavaMethod setBody(BodyBlock body) {
		this.body = body;
		return this;
	}

	@Override
	public String toString() {
		throw Lang.noImplement();
	}

	public String getDescriptor() {
		throw Lang.noImplement();
	}

}
