package org.nutz.ioc;

import java.util.ArrayList;
import java.util.List;

import org.nutz.ioc.aop.MirrorFactory;
import org.nutz.ioc.meta.IocValue;
import org.nutz.json.Json;
import org.nutz.lang.Lang;
import org.nutz.lang.Mirror;

public class IocMaking {

	private String objectName;

	private Ioc ioc;

	private IocContext context;

	private List<ValueProxyMaker> vpms;

	private MirrorFactory mirrors;

	public IocMaking(Ioc ioc, MirrorFactory mirrors, IocContext context, String objectName) {
		this.objectName = objectName;
		this.ioc = ioc;
		this.context = context;
		this.vpms = new ArrayList<ValueProxyMaker>();
		this.mirrors = mirrors;
	}

	public Ioc getIoc() {
		return ioc;
	}

	public IocContext getContext() {
		return context;
	}

	public String getObjectName() {
		return objectName;
	}

	public ValueProxy makeValue(IocValue iv) {
		for (ValueProxyMaker vpm : vpms) {
			ValueProxy vp = vpm.make(iv);
			if (null != vp)
				return vp;
		}
		throw Lang.makeThrow("Unknown value {'%s':%s} for object [%s]", iv.getType(), Json
				.toJson(iv.getValue()), objectName);
	}

	public Mirror<?> getMirror(Class<?> type) {
		return mirrors.getMirror(type, objectName);
	}
}
