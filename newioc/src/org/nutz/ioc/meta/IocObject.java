package org.nutz.ioc.meta;

import java.util.ArrayList;
import java.util.List;

public class IocObject {

	private Class<?> type;

	private boolean singleton;

	private IocEventSet events;

	private List<IocValue> args;

	private List<IocField> fields;

	private String level;

	public IocObject() {
		args = new ArrayList<IocValue>();
		fields = new ArrayList<IocField>();
		singleton = true;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public Class<?> getType() {
		return type;
	}

	public void setType(Class<?> type) {
		this.type = type;
	}

	public boolean isSingleton() {
		return singleton;
	}

	public void setSingleton(boolean singleton) {
		this.singleton = singleton;
	}

	public IocEventSet getEvents() {
		return events;
	}

	public void setEvents(IocEventSet events) {
		this.events = events;
	}

	public IocValue[] getArgs() {
		return args.toArray(new IocValue[args.size()]);
	}

	public void addArg(IocValue arg) {
		this.args.add(arg);
	}

	public IocField[] getFields() {
		return fields.toArray(new IocField[fields.size()]);
	}

	public void addField(IocField field) {
		this.fields.add(field);
	}

}
