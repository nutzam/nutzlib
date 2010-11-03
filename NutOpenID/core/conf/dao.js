var ioc = {
        dataSource : {
                type : "com.jolbox.bonecp.BoneCPDataSource",
                events : {
                        depose : 'close'
                },
                fields : {
                        driverClass : 'org.h2.Driver',
                        jdbcUrl : 'jdbc:h2:ke;LOCK_MODE=0;AUTO_RECONNECT=TRUE;CACHE_SIZE=65536;TRACE_LEVEL_FILE=3',
                        username : 'sa',
                        password : '',
                        minConnectionsPerPartition : 5 ,
                        maxConnectionsPerPartition : 20
                }
        },
        dao : {
        		type : "org.nutz.dao.impl.NutDao",
        		fields : {
        				dataSource : {refer : 'dataSource'}
        		}
        }
}