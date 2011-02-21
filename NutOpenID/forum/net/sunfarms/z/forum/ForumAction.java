package net.sunfarms.z.forum;

import java.util.List;

import javax.servlet.http.HttpSession;

import net.sunfarms.z.auth.AuthUserInfo;
import net.sunfarms.z.forum.bean.ForumComment;
import net.sunfarms.z.forum.bean.ForumTip;

import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.OrderBy;
import org.nutz.ioc.annotation.InjectName;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Strings;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

@IocBean(create="init")
@InjectName
public class ForumAction {
	
	@At("/f/list")
	@Ok("ftl:forum/list")
	public Object list(String page , String pageSize){
		OrderBy cnd = Cnd.orderBy().desc("createTime");
		List<ForumTip> list = dao.query(ForumTip.class, cnd, null);
		for (ForumTip forumTip : list) {
			dao.fetchLinks(forumTip, null);
		}
		System.out.println(list.size());
		return list;
	}
	
	@At("/f/newTip")
	public boolean newTip(@Param("content")String content, HttpSession session) {
		if (Strings.isBlank(content))
			return false;
		AuthUserInfo authUserInfo = (AuthUserInfo) session.getAttribute(AuthUserInfo.class.getName());
		if (authUserInfo == null)
			return false;
		ForumTip forumTip = new ForumTip();
		forumTip.setAuthUserInfo(authUserInfo);
		forumTip.setUid(authUserInfo.getUid());
		forumTip.setContent(content);
		forumTip.setCreateTime(System.currentTimeMillis());
		dao.insert(forumTip);
		return true;
	}
	
	@At("/f/viewTip")
	@Ok("ftl:forum/viewTip")
	public Object viewTip(@Param("tipId")long tipId) {
		if (tipId < 0)
			return null;
		ForumTip forumTip = dao.fetch(ForumTip.class, tipId);
		if (forumTip == null)
			return null;
		return dao.fetchLinks(forumTip,null);
	}
	
	@At("/f/addComment")
	public boolean addComment(@Param("comment")String comment, @Param("tipId")long tipId, HttpSession session) {
		if (Strings.isBlank(comment) || tipId < 0)
			return false;
		AuthUserInfo authUserInfo = (AuthUserInfo) session.getAttribute(AuthUserInfo.class.getName());
		if (authUserInfo == null)
			return false;
		ForumTip forumTip = dao.fetch(ForumTip.class, tipId);
		if (forumTip == null)
			return false;
		ForumComment forumComment = new ForumComment();
		forumComment.setAuthUserInfo(authUserInfo);
		forumComment.setUid(authUserInfo.getUid());
		forumComment.setTipId(tipId);
		forumComment.setContent(comment);
		forumComment.setCreateTime(System.currentTimeMillis());
		dao.insert(forumComment);
		return true;
	}

	private Dao dao;
	
	@Inject
	public void setDao(Dao dao) {
		this.dao = dao;
	}
	
	public void init() {
		if (dao.count(ForumTip.class) < 1) {
			ForumTip forumTip = new ForumTip();
			forumTip.setUid(1);
			forumTip.setContent("第一条测试信息--论坛信息");
			forumTip.setCreateTime(System.currentTimeMillis());
			dao.insert(forumTip);
		}
	}
}
