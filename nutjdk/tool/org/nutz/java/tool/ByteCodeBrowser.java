package org.nutz.java.tool;

import static java.lang.System.*;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.nutz.lang.ExitLoop;
import org.nutz.lang.Files;
import org.nutz.lang.Lang;
import org.nutz.lang.Strings;
import org.nutz.lang.util.LinkedIntArray;

/**
 * @author zozoh(zozohtnt@gmail.com)
 * 
 */
public class ByteCodeBrowser extends ByteCodeSupport {

	public static void main(String[] args) {
		if (args.length < 1) {
			err.println("Lack file path");
			System.exit(0);
		}

		String path = args[0];
		File f = Files.findFile(path);
		if (null == f) {
			err.printf("File '%s' didn't exists!\n", path);
			System.exit(0);
		}

		ByteCodeBrowser bcb = new ByteCodeBrowser(f);
		bcb.parse();
	}

	private ByteCodeBrowser(File f) {
		this.file = f;
		bytes = new LinkedIntArray();
	}

	private void read_magic() {
		next(4);
		dump("Magic");
	}

	private void read_version(String s) {
		next(2);
		dump(s);
	}

	private int read_count(String s) {
		next(2);
		int re = asInt();
		dump(s + "(%d)", re);
		return re;
	}

	/**
	 * The constant_pool table is indexed from 1 to constant_pool_count-1.
	 * 
	 * @param count
	 */
	private void read_constant_pool(int count) {
		br();
		hr('=');
		out.println("Constants: [" + count + "]");
		for (int i = 1; i < count; i++) {
			out.printf("%s[%d]\n", Strings.dup('-', 40), i);
			read_constant_pool_info();
		}
		hr('=');
	}

	private void read_constant_pool_info() {
		byte tag = next();
		bytes.clear();
		switch (tag) {
		// Class
		case 7:
			next(2);
			dumps("class[%d]", asInt());
			break;
		// Fieldref
		case 9:
			read_method_field_info("filed");
			break;
		// Methodref
		case 10:
			read_method_field_info("method");
			break;
		// InterfaceMethodref
		case 11:
			read_method_field_info("AMethod");
			break;
		// String
		case 8:
			next(2);
			dumps("string[%d]", asInt());
			break;
		// Integer
		case 3:
			next(4);
			dump("int");
			break;
		// Float
		case 4:
			next(4);
			dumps("float");
			break;
		// Long
		case 5:
			next(8);
			dumps("long");
			break;
		// Double
		case 6:
			next(8);
			dumps("double");
			break;
		// NameAndType
		case 12:
			next(2);
			dumps("name");
			next(2);
			dumps("descriptor");
			break;
		// Utf8
		case 1:
			next(2);
			int len = asInt();
			bytes.clear();
			next(len);
			String s = asString();
			dumps("utf8[%d]: '%s'\n", len, s);
			break;
		default:
			throw Lang.makeThrow("Unknown tag %d", tag);
		}
	}

	private void read_method_field_info(String title) {
		next(2);
		int ci = asInt();
		next(2);
		int ni = asInt();
		dumps(title + "[%d, %d]", ci, ni);
	}

	/**
	 * <pre>
	 *  ClassFile {
	 *     	u4 magic;
	 *     	u2 minor_version;
	 *     	u2 major_version;
	 *     	u2 constant_pool_count;
	 *     	cp_info constant_pool[constant_pool_count-1];
	 *     	u2 access_flags;
	 *     	u2 this_class;
	 *     	u2 super_class;
	 *     	u2 interfaces_count;
	 *     	u2 interfaces[interfaces_count];
	 *     	u2 fields_count;
	 *     	field_info fields[fields_count];
	 *     	u2 methods_count;
	 *     	method_info methods[methods_count];
	 *     	u2 attributes_count;
	 *     	attribute_info attributes[attributes_count];
	 *     }
	 * </pre>
	 */
	public void parse() {
		try {
			input = new DataInputStream(new FileInputStream(file));
			out.print(file.getName());
			br();
			try {
				int count;
				read_magic();
				read_version("minor");
				read_version("major");
				count = read_count("constants");
				read_constant_pool(count);
				while (true)
					next();
			} catch (ExitLoop exit) {
				dump();
				hr();
				out.println("Done");
			}
		} catch (FileNotFoundException e) {
			throw Lang.wrapThrow(e);
		}
	}

}
