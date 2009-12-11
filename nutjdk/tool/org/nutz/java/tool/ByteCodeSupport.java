package org.nutz.java.tool;

import static java.lang.System.out;

import java.io.File;

import org.nutz.lang.Strings;
import org.nutz.lang.util.ByteReading;

public class ByteCodeSupport extends ByteReading{

	public ByteCodeSupport(int[] bytes) {
		super(bytes);
	}

	protected File file;

	protected void br() {
		out.println();
	}

	protected void hr(char c) {
		out.printf("%s\n", Strings.dup(c, 40));
	}

	protected void hr() {
		hr('-');
	}

	protected void print(String fmt, Object... args) {
		out.printf(fmt, args);
		mark();
	}

	protected void dump() {
		dump(null);
	}

	protected void dump(String fmt, Object... args) {
		dump(20, fmt, args);
	}

	protected void dumps(String fmt, Object... args) {
		dump(10, fmt, args);
	}

	protected void dump(int width, String fmt, Object... args) {
		if (!Strings.isBlank(fmt)) {
			String s = String.format(fmt, args);
			out.print(Strings.alignRight(s, width, ' '));
			if (!s.endsWith("\n") && !s.endsWith(":"))
				out.print(": ");
		}
		out.print(this.toHexString(16));
		br();
		mark();
	}

}