package org.nutz.ioc;

public class IocMaking {

	private String objectName;

	private Ioc ioc;

	private IocContext context;

	public IocMaking(Ioc ioc, IocContext context, String objectName) {
		this.objectName = objectName;
		this.ioc = ioc;
		this.context = context;
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

}
