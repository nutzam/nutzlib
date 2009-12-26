package org.nutz.ioc.loader.xml;

import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;

import org.nutz.ioc.IocLoader;
import org.nutz.ioc.ObjectLoadException;
import org.nutz.ioc.meta.IocEventSet;
import org.nutz.ioc.meta.IocField;
import org.nutz.ioc.meta.IocObject;
import org.nutz.ioc.meta.IocValue;
import org.nutz.json.Json;
import org.nutz.lang.Files;
import org.nutz.lang.Lang;
import org.nutz.lang.Strings;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * 使用XML做为Ioc配置文件
 * <p/>
 * <b>不支持import</b>
 * <p/>
 * <b>不支持vars</b>
 * 
 * @author wendal(wendal1985@gmail.com)
 * @version 1.0
 */
public class XmlLoader implements IocLoader {

	private static final Log LOG = Logs.getLog(XmlLoader.class);

	private Map<String, String> iocMap;

	public XmlLoader(String... fileNames) {
		iocMap = new HashMap<String, String>();
		try {
			DocumentBuilder builder = Lang.xmls();
			Document document;
			for (String fileName : fileNames) {
				document = builder.parse(Files.findFile(fileName));
				document.normalize();
				NodeList nodeList = document.getDocumentElement().getElementsByTagName("ioc");
				if (nodeList.getLength() > 0)
					scanBeans((Element) nodeList.item(0));
			}
		} catch (Throwable e) {
			e.printStackTrace();
			throw Lang.makeThrow("Error");
		}
	}

	public String[] getName() {
		return iocMap.keySet().toArray(new String[iocMap.keySet().size()]);
	}

	public boolean has(String name) {
		return iocMap.containsKey(name);
	}

	public IocObject load(String name) throws ObjectLoadException {
		String iobjData = iocMap.get(name);
		if(iobjData == null)
			return null;
		return Json.fromJson(IocObject.class, iobjData);
	}

	void scanBeans(Element iocElement) throws Throwable {
		NodeList beansNode = iocElement.getElementsByTagName("bean");
		int len = beansNode.getLength();
		if (len > 0) {
			for (int i = 0; i < len; i++)
					paserBean((Element) beansNode.item(i));
		}
	}

	void paserBean(Element beanElement) throws Throwable {
		IocObject iocObject = new IocObject();
		String beanId = beanElement.getAttribute("id");
		if (beanId == null)
			throw Lang.makeThrow("其中一个bean没有id!");
		if (iocMap.containsKey(beanId))
			throw Lang.makeThrow("发现重复的Bean id! id=" + beanId);
		String beanType = beanElement.getAttribute("type");
		if (!Strings.isBlank(beanType))
			iocObject.setType(Class.forName(beanType));
		String beanScope = beanElement.getAttribute("scope");
		if (!Strings.isBlank(beanScope))
			iocObject.setScope(beanScope);
		parseArgs(beanElement, beanId, iocObject);
		parseFields(beanElement, beanId, iocObject);
		parseEvents(beanElement, beanId, iocObject);

		iocMap.put(beanId, Json.toJson(iocObject));
		if (LOG.isDebugEnabled())
			LOG.debug("处理完一个bean: " + beanId);
	}

	void parseArgs(Element beanElement, String beanId, IocObject iocObject) {
		NodeList argsNodeList = beanElement.getElementsByTagName("args");
		if (argsNodeList.getLength() > 0) {
			Element argsElement = (Element) argsNodeList.item(0);
			NodeList argNodeList = argsElement.getElementsByTagName("arg");
			int len = argNodeList.getLength();
			if (len > 0) {
				for (int i = 0; i < len; i++) {
					Element argElement = (Element) argNodeList.item(i);
					IocValue iocValue = new IocValue();
					iocValue.setType(argElement.getAttribute("type"));
					iocValue.setValue(argElement.getAttribute("value"));
					iocObject.addArg(iocValue);
				}
			}
		}
	}

	void parseFields(Element beanElement, String beanId, IocObject iocObject) {
		NodeList fieldsNodeList = beanElement.getElementsByTagName("fields");
		if (fieldsNodeList.getLength() > 0) {
			Element fieldsElement = (Element) fieldsNodeList.item(0);
			NodeList fieldNodeList = fieldsElement.getElementsByTagName("field");
			if (fieldsNodeList.getLength() > 0) {
				int len = fieldNodeList.getLength();
				for (int i = 0; i < len; i++) {
					Element fieldElement = (Element) fieldNodeList.item(i);
					IocField iocField = new IocField();
					iocField.setName(fieldElement.getAttribute("name"));
					IocValue fieldValue = new IocValue();
					fieldValue.setType(fieldElement.getAttribute("type"));
					fieldValue.setValue(fieldElement.getAttribute("value"));
					iocField.setValue(fieldValue);
					iocObject.addField(iocField);
				}
			}
		}
	}

	void parseEvents(Element beanElement, String beanId, IocObject iocObject) {
		NodeList eventsNodeList = beanElement.getElementsByTagName("events");
		if (eventsNodeList.getLength() > 0) {
			Element eventsElement = (Element) eventsNodeList.item(0);
			IocEventSet iocEventSet = new IocEventSet();
			NodeList fetchNodeList = eventsElement.getElementsByTagName("fetch");
			if (fetchNodeList.getLength() > 0)
				iocEventSet.setFetch(((Element) fetchNodeList.item(0)).getAttribute("value"));
			NodeList createNodeList = eventsElement.getElementsByTagName("create");
			if (createNodeList.getLength() > 0)
				iocEventSet.setCreate(((Element) createNodeList.item(0)).getAttribute("value"));
			NodeList deposeNodeList = eventsElement.getElementsByTagName("depose");
			if (deposeNodeList.getLength() > 0)
				iocEventSet.setDepose(((Element) deposeNodeList.item(0)).getAttribute("value"));
			iocObject.setEvents(iocEventSet);
		}
	}

	public static void main(String[] args) throws Throwable {
		XmlLoader loader = new XmlLoader("simple.xml");
		for (String string : loader.iocMap.values())
			System.out.println(string);
	}
}
