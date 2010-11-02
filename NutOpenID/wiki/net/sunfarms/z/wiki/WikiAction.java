package net.sunfarms.z.wiki;

import net.sunfarms.z.wiki.bean.WikiDoc;

import org.nutz.dao.Cnd;
import org.nutz.dao.Condition;
import org.nutz.dao.Dao;
import org.nutz.ioc.annotation.InjectName;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;
import org.nutz.mvc.view.ServerRedirectView;

@IocBean
@InjectName
public class WikiAction {
	
	private static final Log log = Logs.getLog(WikiAction.class);
	
	@At("/wiki/*")
	@Ok("zdoc")
	public Object view(String name, @Param("version")long version){
		Condition cnd = null;
		if (version > 0)
			cnd = Cnd.where("name", "=", name).and("version", "=", version);
		else
			cnd = Cnd.where("name", "=", name).desc("version");
		WikiDoc wikiDoc = dao.fetch(WikiDoc.class, cnd);
		if (wikiDoc == null) {
			if (log.isInfoEnabled())
				log.infof("WikiDoc not found , name = %s , version = %d",name,version);
			return new ServerRedirectView("/404");
		}
		
		return wikiDoc;
	}

	@At("/wiki/change")
	public void change(@Param("name")String name, @Param("content")String content){
		
	}
	
	@Inject
	private Dao dao;
	
	public void setDao(Dao dao) {
		this.dao = dao;
	}
	
	public void init() {
		if (!dao.exists(WikiDoc.class)) {
			
		}
	}
}
