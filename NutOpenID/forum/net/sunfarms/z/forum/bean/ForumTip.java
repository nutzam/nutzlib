package net.sunfarms.z.forum.bean;

import java.util.List;

import net.sunfarms.z.auth.AuthUserInfo;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Many;
import org.nutz.dao.entity.annotation.One;
import org.nutz.dao.entity.annotation.Table;

@Table("t_forum_tip")
public class ForumTip {

	@Id
	private long id;
	
	@Column
	private String content;
	
	@Column
	private long createTime;
	
	@Column
	private long uid;
	
	@Many(target=ForumComment.class,field="tipId")
	private List<ForumComment> comments;
	
	@One(target=AuthUserInfo.class,field="uid")
	private AuthUserInfo authUserInfo;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	public List<ForumComment> getComments() {
		return comments;
	}

	public void setComments(List<ForumComment> comments) {
		this.comments = comments;
	}

	public AuthUserInfo getAuthUserInfo() {
		return authUserInfo;
	}

	public void setAuthUserInfo(AuthUserInfo authUserInfo) {
		this.authUserInfo = authUserInfo;
	}
	
	
}
