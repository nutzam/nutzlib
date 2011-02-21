package net.sunfarms.z.wiki;

import java.util.List;

import javax.servlet.http.HttpSession;

import net.sunfarms.z.auth.AuthUserInfo;
import net.sunfarms.z.wiki.bean.WikiDoc;

import org.nutz.dao.Cnd;
import org.nutz.dao.Condition;
import org.nutz.dao.Dao;
import org.nutz.ioc.annotation.InjectName;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.adaptor.JsonAdaptor;
import org.nutz.mvc.annotation.AdaptBy;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;
import org.nutz.mvc.view.ServerRedirectView;

@IocBean
@InjectName
public class WikiAction {
	
	private static final Log log = Logs.getLog(WikiAction.class);
	
	@At("/wiki/list")
	@Ok("->:/WEB-INF/wiki/list.ftl")
	public Object list(){
		List<WikiDoc> list = dao.query(WikiDoc.class, null, null);
		return list;
	}
	
	
//	@At("/wiki/*")
	@Ok("zdoc")
	public Object view(String name, @Param("version")long version){
		Condition cnd = null;
		if (version > 0)
			cnd = Cnd.where("name", "like", name).and("version", "=", version);
		else
			cnd = Cnd.where("name", "like", name).desc("version");
		WikiDoc wikiDoc = dao.fetch(WikiDoc.class, cnd);
		if (wikiDoc == null) {
			if (log.isInfoEnabled())
				log.infof("WikiDoc not found , name = %s , version = %d",name,version);
			return new ServerRedirectView("/404");
		}
		
		return wikiDoc.getContent();
	}

	@At("/wiki/change")
	public void change(@Param("name")String name, @Param("content")String content){
		
	}
	
	@At("/wiki/create")
	@AdaptBy(type=JsonAdaptor.class)
	public void create(WikiDoc doc, HttpSession session){
		if (dao.fetch(WikiDoc.class, doc.getName()) == null) {
			AuthUserInfo authUserInfo = (AuthUserInfo) session.getAttribute(AuthUserInfo.class.getName());
			if (authUserInfo == null)
				return;
			doc.setCreateTime(System.currentTimeMillis());
			doc.setUid(authUserInfo.getUid());
			doc.setVersion(0);
			dao.insert(doc);
		}
	}
	
	@Inject
	private Dao dao;
	
	public void setDao(Dao dao) {
		this.dao = dao;
	}
}
