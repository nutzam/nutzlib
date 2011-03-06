package org.nutz.xml;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class XmlMap{
	
	private Element element;
	
	public XmlMap(Element element) {
		this.element = element;
	}

	public List<XmlMap> getList(String name) {
		NodeList nList = element.getElementsByTagName(name);
		List<XmlMap> list = new ArrayList<XmlMap>(nList.getLength());
		for (int i = 0; i < nList.getLength(); i++) {
			list.add(new XmlMap((Element)nList.item(i)));
		}
		return list;
	}
	
	/**
	 * 获取一个属性的值
	 */
	protected String get(String name) {
		if (element.hasAttribute(name))
			return element.getAttribute(name);
		else if ("value".equals(name))
			return element.getTextContent();
		return null;
	}

	
	static DocumentBuilder db;
	
	static {
		try {
			db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 从输入流读取XML文件,构建XmlMap对象
	 * @param is 包含Xml文件的输入流
	 * @return XmlMap对象
	 */
	public static final XmlMap read(InputStream is) {
		try {
			Document doc = db.parse(is);
			doc.normalizeDocument();
			return new XmlMap((Element) doc.getDocumentElement());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 根据表达式从map中取值<p/>
	 * 示例XML文件:
<pre>
 * {@code
<http authBy="xxxUserSeriver">
	<url pattern="/login.jsp" filters='none'/>
	<url pattern="/user/login*" filters='none'/>
	<url pattern="/.+[.](css|js|html)" filters='none'/>
	<url pattern="/image/*" filters='none'/>
	<url pattern="/admin/*" access="ROLE_ADMIN" />
	<url pattern="/**" access="ROLE_USER" />
	<form-login login-page='/login.jsp'/>
	<other>abc</other>
	<other>exy</other>
	<levelA>
		<levelB myKey="nutz">
			<levelC>
				<levelD key="Wendal"/>
			</levelC>
			<levelC>
				<levelD key="XX1X"/>
				<levelD key="XX2X"/>
			</levelC>
		</levelB>
		<levelB>
			<levelC>
				<levelD key="Wendal"/>
			</levelC>
		</levelB>
	</levelA>
</http>
 * }
</pre>
	 * 使用示例:<p/>
<pre>
 * {@code
 XmlMap map = XmlMap.read(new FileInputStream("demo.xml"));获取XmlMap对象
 map.get("authBy");                       //返回值为xxxUserSeriver
 XmlMap.parse(map, "#authBy");            //返回值为xxxUserSeriver
 XmlMap.parse(map, "other#value");        //other没有value属性,则返回其文本值abc
 XmlMap.parse(map, "url[3]#pattern");     //获取第4个(索引值为3)url标签的pattern属性
 XmlMap.parse(map, "levelA.levelB.levelC[1].levelD#key");//返回值为XX2X
 XmlMap.parse(map, "levelA.levelB");      //返回值为一个包含全部levelB节点的List<XmlMap>
 XmlMap.parse(map, "levelA.levelB[1]");   //返回值为第2个levelB节点的XmlMap
 XmlMap.parse(map, "levelA.levelB#myKey");//返回值为nutz
 * }
</pre>
	 * 规则简介:<p/>
	 * 1. 如果表达式的包含#号,那么,返回值一定是最后一个节点的特定属性的值,即一个String<p/>
	 * 2. 在层次调用中, 如果没有指定某一层的索引值,那么默认为0. 即 abc.xyz#abc 相当于abc[0].xyz[0]#abc<p/>
	 * 3. 如果不包含#号,那么,返回值根据最后一个层次决定, 
	 * 例如 abc.xyz返回的是列表,而 abc.xyz[9]返回的是指定索引的XmlMap <p/>
	 * 4. 任何一层不存在或者指定的索引值不存在,均报错<p/>
	 * 5. 由于第3条的约定, 故 abc.xyz 不等于 abc.xyz[0]<p/>
	 * 6. 如果请求属性名value,而名为value的Attribute不存在,则返回这个节点的文本值<p/>
	 * <p/>
	 * 这个实现就是要求调用简单,无缓存,无预编译<p/>
	 * @param map XMLMap对象
	 * @param path 表达式
	 * @return 求值结果
	 */
	public static final Object parse(XmlMap map,String path) {
		if (path.indexOf('.') > 0)
			return parse(get2(map, path.substring(0,path.indexOf('.'))), path.substring(path.indexOf('.')+1));
		//到这里的话,应该只剩下 abc#attr 或者abc[12]#attr 或者 abc[12] 或者abc
		if (path.indexOf('#') > -1) //需要获取具体的属性值
			if (path.indexOf('#') > 0)
				return get2(map, path.substring(0,path.indexOf('#'))).get(path.substring(path.indexOf('#')+1));
			else
				return map.get(path.substring(1));
		else
			//如果指定了索引则返回XmlMap,否则返回List
			return path.indexOf('[') > 0 ? get3(map, path) : map.getList(path);
	}
	
	private static final XmlMap get2(XmlMap map, String path) {
		return path.indexOf('[') > 0 ? get3(map, path) : map.getList(path).get(0);
	}
	
	private static final XmlMap get3(XmlMap map, String path) {
		return map.getList(path.substring(0,path.lastIndexOf('['))).get(Integer.parseInt(path.substring(path.lastIndexOf('[')+1,path.length()-1)));
	}
}
