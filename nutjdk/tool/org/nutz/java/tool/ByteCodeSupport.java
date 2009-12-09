package org.nutz.java.tool;

import static java.lang.System.out;

import java.io.DataInput;
import java.io.EOFException;
import java.io.File;
import java.io.IOException;

import org.nutz.lang.ExitLoop;
import org.nutz.lang.Lang;
import org.nutz.lang.Strings;
import org.nutz.lang.util.LinkedIntArray;

public class ByteCodeSupport {

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

	protected DataInput input;
	protected byte b;
	protected LinkedIntArray bytes;

	public ByteCodeSupport() {
		super();
	}

	protected int asInt() {
		if (bytes.size() < 2)
			throw Lang.makeThrow("You can not evalute %d byte[] to int!", bytes.size());
		int last = bytes.size() - 1;
		byte low = (byte) (bytes.get(last--) & 255);
		byte high = (byte) (bytes.get(last) & 255);
		return low + (high << 8);
	}

	protected int asInt4() {
		if (bytes.size() < 4)
			throw Lang.makeThrow("You can not evalute %d byte[] to int!", bytes.size());
		int last = bytes.size() - 1;
		byte low = (byte) (bytes.get(last--) & 255);
		byte lowh = (byte) (bytes.get(last--) & 255);
		byte higl = (byte) (bytes.get(last--) & 255);
		byte high = (byte) (bytes.get(last) & 255);
		return low + (lowh << 8) + (higl << 16) + (high << 24);
	}

	protected String asString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < bytes.size(); i++)
			sb.append((char) bytes.get(i));
		return sb.toString();
	}

	protected void print(String fmt, Object... args) {
		out.printf(fmt, args);
		bytes.clear();
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
		if (bytes.isEmpty()) {
			out.println("<!empty>");
		} else {
			for (int i = 0; i < bytes.size(); i++) {
				if (i > 0)
					if (i % 16 == 0)
						br();
				out.printf("%s ", Strings.toHex(bytes.get(i), 2));
			}
		}
		bytes.clear();
		br();
	}

	protected void next(int num) {
		for (int i = 0; i < num; i++)
			next();
	}

	protected byte next() {
		try {
			b = input.readByte();
			bytes.push(b);
			return b;
		} catch (EOFException e) {
			throw new ExitLoop();
		} catch (IOException e) {
			throw Lang.wrapThrow(e);
		}
	}

}