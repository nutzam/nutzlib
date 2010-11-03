package net.sunfarms.z;

import net.sunfarms.z.ext.ViewMakerProxy;
import net.sunfarms.z.init.ZLoading;

import org.nutz.mvc.annotation.Encoding;
import org.nutz.mvc.annotation.Fail;
import org.nutz.mvc.annotation.IocBy;
import org.nutz.mvc.annotation.LoadingBy;
import org.nutz.mvc.annotation.Localization;
import org.nutz.mvc.annotation.Modules;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Views;
import org.nutz.mvc.ioc.provider.ComboIocProvider;

@IocBy(type=ComboIocProvider.class,args={"*org.nutz.ioc.loader.json.JsonLoader","conf",
	  "*org.nutz.ioc.loader.annotation.AnnotationIocLoader","net.sunfarms.z"})
@Encoding(input="utf8",output="utf8")
@Modules(scanPackage=true)
@LoadingBy(ZLoading.class)
@Localization("msg")
@Ok("json")
@Fail(">>:/50x.jsp")
@Views(ViewMakerProxy.class)
public class MainModule {
}
