package net.sunfarms.z.wiki.bean;

import net.sunfarms.z.auth.AuthUserInfo;

import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.One;
import org.nutz.dao.entity.annotation.Table;

@Table("t_wiki_doc")
public class WikiDoc {

	@Id
	private long id;
	private String name;
	private String content;
	private long uid;
	private long version;
	private long createTime;
	@One(target=AuthUserInfo.class,field="uid")
	private AuthUserInfo authUserInfo;
	
	//---------------------------------------------------------------
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
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public long getUid() {
		return uid;
	}
	public void setUid(long uid) {
		this.uid = uid;
	}
	public long getVersion() {
		return version;
	}
	public void setVersion(long version) {
		this.version = version;
	}
	public long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
	public AuthUserInfo getAuthUserInfo() {
		return authUserInfo;
	}
	public void setAuthUserInfo(AuthUserInfo authUserInfo) {
		this.authUserInfo = authUserInfo;
	}
	
	
}
