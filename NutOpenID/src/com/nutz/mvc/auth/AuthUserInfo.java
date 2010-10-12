package com.nutz.mvc.auth;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Table;

@Table("t_auth_user_info")
public class AuthUserInfo {
	
	/**
	 * 用户Id,自增
	 */
	@Column
	@Id
	private long uid;
	
	/**
	 * 用户唯一标识符
	 */
	@Column
	@Name
	private String identity;
	
	/**
	 * 电子邮件地址
	 */
	@Column
	private String email;

public long getUid() {
	return uid;
}

public void setUid(long uid) {
	this.uid = uid;
}

	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
