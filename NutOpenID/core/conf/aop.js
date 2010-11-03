var ioc = {
	$aop : {/*定义Aop配置*/
        type : 'org.nutz.ioc.aop.config.impl.ComboAopConfigration',/**/
        fields : {
                aopConfigrations  : [
                        { type : 'org.nutz.ioc.aop.config.impl.XmlAopConfigration',/*加载使用XML声明的Aop*/
                        	args : ['conf/declaration-aop.xml']},
                        { type : 'org.nutz.ioc.aop.config.impl.AnnotationAopConfigration'}/*加载使用@Aop标注的bean*/
                ]
        }
	}
}