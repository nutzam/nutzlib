package org.nutz.ioc.loader.xml;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

import javax.xml.parsers.DocumentBuilder;

import org.nutz.ioc.IocLoader;
import org.nutz.ioc.Iocs;
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

	private Map<String, IocObject> iocMap;
	
	private Map<String, String> parentMap;

	public XmlLoader(String... fileNames) {
		iocMap = new LinkedHashMap<String, IocObject>();
		parentMap = new TreeMap<String, String>();
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
			handleParent();
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
		IocObject iocObject = iocMap.get(name);
		if(iocObject == null)
			return null;
		return Json.fromJson(IocObject.class, Json.toJson(iocObject));
	}

	private void scanBeans(Element iocElement) throws Throwable {
		NodeList beansNode = iocElement.getElementsByTagName("bean");
		int len = beansNode.getLength();
		if (len > 0)
			for (int i = 0; i < len; i++)
					paserBean((Element) beansNode.item(i));
	}

	private void paserBean(Element beanElement) throws Throwable {
		String beanId = beanElement.getAttribute("id");
		if (beanId == null)
			throw Lang.makeThrow("其中一个bean没有id!");
		if (iocMap.containsKey(beanId))
			throw Lang.makeThrow("发现重复的Bean id! id=" + beanId);
		IocObject iocObject = new IocObject();
		String beanType = beanElement.getAttribute("type");
		if (!Strings.isBlank(beanType))
			iocObject.setType(Class.forName(beanType));
		String beanScope = beanElement.getAttribute("scope");
		if (!Strings.isBlank(beanScope))
			iocObject.setScope(beanScope);
		String beanParent = beanElement.getAttribute("parent");
		if(! Strings.isBlank(beanParent))
			parentMap.put(beanId, beanParent);
		parseArgs(beanElement, beanId, iocObject);
		parseFields(beanElement, beanId, iocObject);
		parseEvents(beanElement, beanId, iocObject);

		iocMap.put(beanId,iocObject);
		if (LOG.isDebugEnabled())
			LOG.debug("处理完一个bean: " + beanId);
	}

	private void parseArgs(Element beanElement, String beanId, IocObject iocObject) {
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

	private void parseFields(Element beanElement, String beanId, IocObject iocObject) {
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

	private void parseEvents(Element beanElement, String beanId, IocObject iocObject) {
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
			if(iocEventSet.getCreate() == null)
				if(iocEventSet.getDepose() == null)
					if(iocEventSet.getFetch() == null)
						return;
			iocObject.setEvents(iocEventSet);
		}
	}
	
	private void handleParent(){
		//检查parentId是否都存在.
		for (String parentId : parentMap.values()) {
			if(! iocMap.containsKey(parentId))
				throw Lang.makeThrow("发现无效的parent=%s", parentId);
		}
		//检查循环依赖
		for (Entry<String, String> entry : parentMap.entrySet()) {
			List<String> parentList = new ArrayList<String>();
			if(entry.getKey().equals(entry.getValue()))
				throw Lang.makeThrow("发现循环依赖! bean id=%s", entry.getKey());
			if(! check(parentList, entry.getKey()))
				throw Lang.makeThrow("发现循环依赖! bean id=%s", entry.getKey());
		}
		while(parentMap.size() != 0){
			Iterator<Entry<String, String>> it = parentMap.entrySet().iterator();
			while(it.hasNext()){
				Entry<String, String> entry = it.next();
				String beanId = entry.getKey();
				String parentId = entry.getValue();
				if(parentMap.get(parentId) == null){
					IocObject newIocObject = Iocs.mergeWith(iocMap.get(beanId), iocMap.get(parentId));
					iocMap.put(beanId, newIocObject);
					parentMap.remove(beanId);
					break;
				}
			}
		}
		parentMap = null;
	}
	
	private boolean check(List<String> parentList,String currentBeanId){
		if(parentList.contains(currentBeanId))
			return false;
		String parentBeanId = parentMap.get(currentBeanId);
		if(parentBeanId == null)
			return true;
		parentList.add(currentBeanId);
		return check(parentList, parentBeanId);
	}

	public static void main(String[] args) throws Throwable {
		XmlLoader loader = new XmlLoader("simple.xml");
		System.out.println(Json.toJson(loader.iocMap));
	}
}
