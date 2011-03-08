package org.nutz.xml;

import static org.junit.Assert.*;

import java.io.FileInputStream;

import org.junit.Test;

public class XmlMapTest {

	@Test
	public void testGetAsMap()  throws Throwable {
		XmlMap map = XmlMap.read(new FileInputStream("demo.xml"));
		assertEquals("xxxUserSeriver",map.get("authBy"));
		assertEquals("xxxUserSeriver",XmlMap.parse(map, "#authBy"));
	}

	@Test
	public void testGet() throws Throwable {
		XmlMap map = XmlMap.read(new FileInputStream("demo.xml"));
		assertEquals("/login.jsp",XmlMap.parse(map, "url#pattern"));
		assertEquals("/user/login*",XmlMap.parse(map, "url[1]#pattern"));
		assertEquals("/.+[.](css|js|html)",XmlMap.parse(map, "url[2]#pattern"));
	}

}
