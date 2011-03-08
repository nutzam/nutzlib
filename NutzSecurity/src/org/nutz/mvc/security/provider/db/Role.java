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
	
	@Override
	public boolean equals(Object obj) {
		if (obj != null && obj instanceof Role) {
			Role out = (Role)obj;
			if (this.name.equalsIgnoreCase(out.name))
				return true;
		}
		return false;
	}
}
