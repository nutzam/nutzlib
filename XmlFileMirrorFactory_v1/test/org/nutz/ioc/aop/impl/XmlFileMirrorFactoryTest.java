package org.nutz.ioc.aop.impl;

import static org.junit.Assert.*;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Test;
import org.nutz.dao.impl.NutDao;
import org.nutz.ioc.aop.MirrorFactory;
import org.nutz.ioc.impl.NutIoc;
import org.xml.sax.SAXException;

@SuppressWarnings("unused")
public class XmlFileMirrorFactoryTest {

	@Test
	public void testGetMirror() throws ParserConfigurationException, SAXException, IOException {
		MirrorFactory mirrorFactory = new XmlFileMirrorFactory(null, "xmlfile-aop.xml");
		mirrorFactory.getMirror(XmlFileMirrorFactory.class, null);
		mirrorFactory.getMirror(DefaultMirrorFactory.class, null);
		mirrorFactory.getMirror(NutDao.class, null);
		mirrorFactory.getMirror(NutIoc.class, null);
	}

}
