package org.nutz.mvc.security.provider.db;

import org.nutz.dao.entity.annotation.Table;

@Table("tb_auth_role")
public class Role {

	private long id;
	
	private String name;

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
	
}
