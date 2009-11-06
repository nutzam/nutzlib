package org.nutz.ioc;

import java.util.ArrayList;
import java.util.List;

import org.nutz.ioc.aop.MirrorFactory;
import org.nutz.ioc.meta.IocValue;
import org.nutz.json.Json;
import org.nutz.lang.Lang;

public class IocMaking {

	private String objectName;

	private ObjectMaker objectMake;

	private Ioc ioc;

	private IocContext context;

	private List<ValueProxyMaker> vpms;

	private MirrorFactory mirrors;

	public IocMaking(	Ioc ioc,
						MirrorFactory mirrors,
						IocContext context,
						ObjectMaker maker,
						String objName) {
		this.objectName = objName;
		this.objectMake = maker;
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

	public ObjectMaker getObjectMake() {
		return objectMake;
	}

	public MirrorFactory getMirrors() {
		return mirrors;
	}

	public ValueProxy makeValue(IocValue iv) {
		for (ValueProxyMaker vpm : vpms) {
			ValueProxy vp = vpm.make(this, iv);
			if (null != vp)
				return vp;
		}
		throw Lang.makeThrow("Unknown value {'%s':%s} for object [%s]", iv.getType(), Json
				.toJson(iv.getValue()), objectName);
	}

}
