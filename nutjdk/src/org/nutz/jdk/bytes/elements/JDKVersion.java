package org.nutz.jdk.bytes.elements;

public class JDKVersion {
	private int majorVersion;
	private int minorVersion;

	public JDKVersion(int majorVersion, int minorVersion) {
		this.majorVersion = majorVersion;
		this.minorVersion = minorVersion;
	}

	public static JDKVersion getSysVersion() {
		String[] vs = System.getProperty("java.class.version").split("\\.");
		return new JDKVersion(Integer.parseInt(vs[0]), Integer.parseInt(vs[1]));
	}

	public int getMajorVersion() {
		return majorVersion;
	}

	public int getMinorVersion() {
		return minorVersion;
	}

}
