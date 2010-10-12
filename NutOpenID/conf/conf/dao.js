var ioc = {
        dataSource : {
                type : "com.jolbox.bonecp.BoneCPDataSource",
                events : {
                        depose : 'close'
                },
                fields : {
                        driverClass : 'org.h2.Driver',
                        jdbcUrl : 'jdbc:h2:nutzpan',
                        username : 'sa',
                        password : ''
                }
        },
        dao : {
        		type : "org.nutz.dao.impl.NutDao",
        		fields : {
        				dataSource : {refer : 'dataSource'}
        		}
        }
}