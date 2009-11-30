package org.nutz.jdk.bytes;

public class ByteVersion {
	private int major;
	private int minor;

	public ByteVersion(int major, int minor) {
		this.major = major;
		this.minor = minor;
	}

	public static ByteVersion getSysVersion() {
		String[] vs = System.getProperty("java.class.version").split("\\.");
		return new ByteVersion(Integer.parseInt(vs[0]), Integer.parseInt(vs[1]));
	}

	public int getMajor() {
		return major;
	}

	public int getMinor() {
		return minor;
	}

}
