package org.nutz.java.bytecode.cp;

import java.util.ArrayList;
import java.util.List;

public class CP {

	private List<CPInfo> infos;

	public CP(int count) {
		infos = new ArrayList<CPInfo>(count);
		infos.add(new HeadInfo());
	}

	public CPInfo getInfo(int index) {
		return infos.get(index);
	}

	public int addInfo(CPInfo info) {
		int re = infos.size();
		infos.add(info);
		return re;
	}

	public int addUtf8(String s) {
		return addInfo(new Utf8Info(s));
	}

	public int addClass(int nameIndex) {
		return addInfo(new ClassInfo(this, nameIndex));
	}

	public int addMember(int classIndex, int nameAndTypeIndex) {
		return addInfo(new MemberInfo(this, classIndex, nameAndTypeIndex));
	}

	public int addNameAndType(int nameIndex, int descriptorIndex) {
		return addInfo(new NameTypeInfo(this, nameIndex, descriptorIndex));
	}

	public int addInt(int num) {
		return addInfo(new IntInfo(num));
	}

	public int addString(int index) {
		return addInfo(new StringInfo(this, index));
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("%d infos\n", infos.size()));
		int i = 0;
		for (CPInfo info : infos) {
			String name = info.getClass().getSimpleName().toLowerCase();
			name = name.substring(0, name.length() - 4);
			sb.append(String.format("%3d - %s: '%s'\n", i, name, info.getText()));
			i++;
		}
		return sb.toString();
	}
}
