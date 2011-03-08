package org.nutz.mvc.security.provider.db;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.nutz.lang.Encoding;
import org.nutz.lang.Lang;

public class MD5 {

	private static final MessageDigest MD5;
	static {
		try {
			MD5 = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			throw Lang.wrapThrow(e);
		}
	}
	
	public static final String encode(String...values) {
		StringBuilder sb = new StringBuilder();
		for (String str : values) {
			sb.append(str);
		}
		byte[] data = sb.toString().getBytes(Encoding.CHARSET_UTF8);
		return new String(MD5.digest(data),Encoding.CHARSET_UTF8);
	}
}
