package org.nutz.ioc.aop;

/**
 * 描述了一组切片，在 Ioc 容器中，你需要声明 名称 "$aop" 的对象，这个对象
 * 会被当作 AopSetting 的实例。当然， 整个容器中，只允许有一个这样的实例。
 * 
 * @author zozoh(zozohtnt@gmail.com)
 */
public class AopSetting {

	private AopItem[] items;

	public AopItem[] getItems() {
		return items;
	}

}
