package org.nutz.java.tool;

import static java.lang.System.out;

import org.nutz.java.bytecode.cp.CP;

public class CodeAttributeBrowser implements AttributeBrowser {
	
	ByteCodeSupport bc;
	
	CP cp;

	/**
	 *     
	Code_attribute {
    	u2 attribute_name_index;
    	u4 attribute_length;
    	u2 max_stack;
    	u2 max_locals;
    	u4 code_length;
    	u1 code[code_length];
    	u2 exception_table_length;
    	{    	u2 start_pc;
    	      	u2 end_pc;
    	      	u2  handler_pc;
    	      	u2  catch_type;
    	}	exception_table[exception_table_length];
    	u2 attributes_count;
    	attribute_info attributes[attributes_count];
    }

	 */
	public void load(int[] bytes, CP cp) {
		this.cp = cp;
		out.printf("-'Code'- %dbytes\n", bytes.length);
		bc = new ByteCodeSupport(bytes);

		bc.next(2);
		bc.dump("max_stack");
		bc.next(2);
		bc.dump("max_locals");
		
		bc.next(4);
		int len = bc.getInt4();
		bc.dump("code_length");
		bc.next(len);
		bc.dump("JVM Code");
		
		bc.next(2);
		len = bc.getInt2();
		bc.dump("exception_table_length");
		for (int i = 0; i < len; i++) {
			bc.next(2);
			bc.dump("start_pc");
			bc.next(2);
			bc.dump("end_pc");
			bc.next(2);
			bc.dump("handler_pc");
			bc.next(2);
			bc.dump("catch_type");
		}
		
		bc.next(2);
		len = bc.getInt2();
		bc.dump("attributes_count");
		for (int i = 0; i < len; i++) {
			bc.next(2);
			int index = bc.getInt2();
			bc.dump("attribute_name_index");
			bc.next(4);
			int currentLen = bc.getInt4();
			bc.dump("attribute_length");
			if ("LineNumberTable".equals(cp.getInfoText(index))) {
				bc.next(2);
				int len2 = bc.getInt2();
				bc.dump("line_number_table_length");
				bc.hr();
				for (int j = 0; j < len2; j++) {
					bc.next(2);
					bc.dump("start_pc");
					bc.next(2);
					bc.dump("line_number");
				}
			} else if ("LocalVariableTable".equals(cp.getInfoText(index))) {
				bc.next(2);
				int len3 = bc.getInt2();
				bc.dump("local_variable_table_length");
				bc.hr();
				for (int j = 0; j < len3; j++) {
					bc.next(2);
					bc.dump("start_pc");
					bc.next(2);
					bc.dump("length");
					bc.next(2);
					bc.dump("name_index [%s]",cp.getInfoText(bc.getInt2()));
					bc.next(2);
					bc.dump("descriptor_index [%s]",cp.getInfoText(bc.getInt2()));
					bc.next(2);
					bc.dump("index");
					bc.hr();
				}
			}else {
				bc.next(currentLen);
				bc.dump("Unknown attibute data [%s]:",cp.getInfo(index).getText());
//				break;
			}
		}
	}

	
}
