package org.nutz.mvc.security.provider.db;

import java.sql.Timestamp;

public class AutoLoginInfo {

	private long id;
	
	private String name;
	
	private String code;
	
	private Timestamp expTime;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Timestamp getExpTime() {
		return expTime;
	}

	public void setExpTime(Timestamp expTime) {
		this.expTime = expTime;
	}
	
	
	
}
