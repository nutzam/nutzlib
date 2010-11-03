package net.sunfarms.z.init;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.nutz.dao.Dao;
import org.nutz.dao.sql.Sql;
import org.nutz.dao.tools.DTable;
import org.nutz.dao.tools.TableDefinition;
import org.nutz.dao.tools.Tables;
import org.nutz.lang.Streams;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.init.DefaultLoading;
import org.nutz.mvc.init.NutConfig;
import org.nutz.resource.NutResource;
import org.nutz.resource.Scans;

public class ZLoading extends DefaultLoading {

	@Override
	public void load(NutConfig config, Class<?> mainModule) {
		super.load(config, mainModule);
		
		//开始加载数据库定义文件,如果对应的表不存在的话就创建
		Dao dao = getIoc().get(Dao.class,"dao");
		List<NutResource> dods = Scans.me().scan("dod",".+[.]dod");
		Log log = Logs.getLog(getClass());
		for (NutResource nutResource : dods) {
			if (log.isDebugEnabled())
				log.debug("Found dod define : " + nutResource);
			try {
				List<DTable> tables = Tables.load(Streams.readAndClose(new InputStreamReader(nutResource.getInputStream())));
				TableDefinition maker = Tables.newInstance(dao.meta());
				for (DTable dt : tables) {
					Sql sql;
					if (dao.exists(dt.getName())) 
						continue;
					sql = maker.makeCreateSql(dt);
					dao.execute(sql);
				}
			} catch (IOException e) {
				if (log.isWarnEnabled())
					log.warn("Fail to load dod define from "+ nutResource , e);
			}
		}
		
		/*提前初始化全部bean,不过ViewMaker已经遍历过了.*/
		//for (String beanName : getIoc().getNames())
		//	getIoc().get(null, beanName);
	}
}
