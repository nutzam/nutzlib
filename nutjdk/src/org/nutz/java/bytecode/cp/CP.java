package org.nutz.java.bytecode.cp;

import java.util.ArrayList;
import java.util.List;

/**
 * JVM 常量池
 * <p>
 * 根据 JVM 的规范，常量池的项目数量为 constant_pool_count -1。因此本实现，采用 一个 ArrayList
 * 作为缓冲容器，当创建时，会自动在数据的第一个元素插入一个占位常量项 (HeadInfo)
 * <p>
 * <b style=color:red>本实现没有考虑线程安全</b>
 * <p>
 * 更多内容请参看： <a href=
 * "http://java.sun.com/docs/books/jvms/second_edition/html/ClassFile.doc.html">
 * Virtual Machine Specification</a> <em>(Second Edition)</em>
 * 
 * @author zozoh(zozohtnt@gmail.com)
 * 
 * @see org.nutz.java.bytecode.cp.HeadInfo
 * @see org.nutz.java.bytecode.cp.CPInfo
 */
public class CP {

	private List<CPInfo> infos;

	/**
	 * 建立一个常量池，为其设定一个初始化缓冲大小为 100 -- 即 99 个可用常量位置
	 */
	public CP() {
		this(100);
	}

	/**
	 * 建立一个常量池，为其设定一个初始化缓冲大小
	 * 
	 * @param count
	 *            初始化数组大小
	 */
	public CP(int count) {
		infos = new ArrayList<CPInfo>(count);
		infos.add(new HeadInfo());
	}

	/**
	 * 根据一个索引，获取一个常量项
	 * 
	 * @param index
	 *            索引值， 从 1 开始
	 * @return 常量项。如果超出范围，将返回 null，如果索引值为 0 ，则返回 HeadInfo
	 */
	public CPInfo getInfo(int index) {
		try {
			return infos.get(index);
		} catch (IndexOutOfBoundsException e) {}
		return null;
	}

	/**
	 * 根据一个索引，获取一个常量的文本信息
	 * 
	 * @param index
	 *            索引值， 从 1 开始
	 * @return 常量项文本描述。如果超出范围，将返回 null，如果索引值为 0 ，则返回 HeadInfo
	 */
	public String getInfoText(int index) {
		CPInfo info = getInfo(index);
		return null == info ? null : info.getText();
	}

	/**
	 * 在常量池中寻找一个常量项，并返回其索引
	 * 
	 * @param info
	 *            常量项
	 * @return 常量索引。如果不存在，返回 -1
	 */
	public int searchInfo(CPInfo info) {
		for (int i = 0; i < infos.size(); i++)
			if (infos.get(i).equals(info))
				return i;
		return -1;
	}

	/**
	 * 增加一个常量项，如果该常量项已经存在，返回其索引，否则添加一个常量项
	 * 
	 * @param info
	 * @return
	 */
	public int addInfo(CPInfo info) {
		// Check is exists
		int re = searchInfo(info);
		if (re > 0)
			return re;
		// Create new item
		re = infos.size();
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

	/**
	 * 为了调试的便利，重载此函数
	 */
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
