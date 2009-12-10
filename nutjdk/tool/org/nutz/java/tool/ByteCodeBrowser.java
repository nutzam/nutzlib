package org.nutz.java.tool;

import static java.lang.System.*;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.nutz.java.bytecode.cp.CP;
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
		dump(s + "_count(%d)", re);
		return re;
	}

	/**
	 * The constant_pool table is indexed from 1 to constant_pool_count-1.
	 * 
	 * @param count
	 */
	private void read_constant_pool(int count) {
		cp = new CP(count);
		hr('=');
		out.println("Constants: [" + count + "]");
		for (int i = 1; i < count; i++) {
			out.printf("%s[%d]\n", Strings.dup('-', 40), i);
			read_constant_pool_info();
		}
		hr('=');
	}

	private CP cp;

	private void read_constant_pool_info() {
		byte tag = next();
		bytes.clear();
		switch (tag) {
		// Class
		case 7:
			next(2);
			cp.addClass(asInt());
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
			cp.addString(asInt());
			dumps("string[%d]", asInt());
			break;
		// Integer
		case 3:
			next(4);
			cp.addInt(asInt4());
			dump("int");
			break;
		// Float
		case 4:
			next(4);
			dumps("float");
			/* break; */
			throw Lang.noImplement();
			// Long
		case 5:
			next(8);
			dumps("long");
			/* break; */
			throw Lang.noImplement();
			// Double
		case 6:
			next(8);
			dumps("double");
			/* break; */
			throw Lang.noImplement();
			// NameAndType
		case 12:
			next(2);
			int ni = asInt();
			dumps("name");
			next(2);
			int di = asInt();
			dumps("descriptor");
			cp.addNameAndType(ni, di);
			break;
		// Utf8
		case 1:
			next(2);
			int len = asInt();
			bytes.clear();
			next(len);
			String s = asString();
			cp.addUtf8(s);
			out.printf("utf8[%d]: '%s'\n", len, s);
			bytes.clear();
			// dumps("utf8[%d]: '%s'\n", len, s);
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
		cp.addMember(ci, ni);
		dumps(title + "[%d, %d]", ci, ni);
	}

	private void read_access_flags() {
		next(2);
		dump("access flags");
	}

	private void read_this_class() {
		next(2);
		dump("this_class[%d]", asInt());
	}

	private void read_super_class() {
		next(2);
		dump("super_class[%d]", asInt());
	}

	private void read_interfaces(int count) {
		hr('=');
		out.println("Interfaces: [" + count + "]");
		for (int i = 0; i < count; i++) {
			next(2);
			dumps("[%d]", i);
		}
		hr('=');
	}

	private void read_field_and_method_infos(int count, String type) {
		hr('=');
		out.println(type + ": [" + count + "]");
		for (int i = 0; i < count; i++) {
			print("-[%d]-%s\n", i, Strings.dup('-', 37));
			next(2);
			//dumps("ACC");
			print("ACC:0x%X",asInt());

			next(2);
			int ni = asInt();
			String name = cp.getInfo(ni).getText();
			next(2);
			int di = asInt();
			String descriptor = cp.getInfo(di).getText();
			//dump("name-de");
			print("%10s: %s", "[" + name + "]", descriptor);
			br();

			next(2);
			int attLen = asInt();
			print("<has %d attibutes>\n", attLen);

			for (int x = 0; x < attLen; x++)
				read_attribute_info(x);
		}
		hr('=');
	}

	private void read_attribute_info(int index) {
		next(2);
		int nameIndex = asInt();
		bytes.clear();

		next(4);
		int len = asInt4();
		//dump("len");
		bytes.clear();

		next(len);
		dump("%3d - '%s' :: %dbytes:\n", index, cp.getInfo(nameIndex).getText(), len);
	}
	
	private void read_class_attributes(int count) {
		hr('~');
		for(int i=0;i<count;i++){
			this.read_attribute_info(i);
		}
		hr('=');
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
				read_access_flags();
				read_this_class();
				read_super_class();
				count = read_count("interface");
				read_interfaces(count);
				count = read_count("field");
				read_field_and_method_infos(count, "Fields");
				count = read_count("method");
				read_field_and_method_infos(count, "Methods");
				count = read_count("attribute");
				read_class_attributes(count);
				hr('~');
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
		// hr('*');
		// out.println(cp.toString());
	}

	

}
