package org.nutz.java.bytecode.cp;

public abstract class AbstractCPInfo implements CPInfo {

	@Override
	public boolean equals(Object obj) {
		if (null == obj)
			return false;
		String txt = getText();
		if (null == txt)
			return false;
		if (obj instanceof CPInfo) {
			String taTxt = ((CPInfo) obj).getText();
			if (null == taTxt)
				return false;
			return txt.equals(taTxt);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return getText().hashCode();
	}

}
