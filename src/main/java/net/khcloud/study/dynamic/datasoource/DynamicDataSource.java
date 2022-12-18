package net.khcloud.study.dynamic.datasoource;


import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class DynamicDataSource extends AbstractRoutingDataSource {

    //注入主从数据源
    @Resource(name="masterDataSource")
    private DataSource masterDataSource;

    @Resource(name="slaveDataSource")
    private DataSource slaveDataSource;


    @Override
    public void afterPropertiesSet() {
        log.debug("afterPropertiesSet in DynamicDataSource...");
        setDefaultTargetDataSource(masterDataSource);
        Map<Object, Object> dataSourceMap = new HashMap<>();

        //将两个数据源set入目标数据源
        dataSourceMap.put(DynamicDataSourceHolder.DB_MASTER, masterDataSource);
        dataSourceMap.put(DynamicDataSourceHolder.DB_SLAVE, slaveDataSource);

        setTargetDataSources(dataSourceMap);
        super.afterPropertiesSet();
    }

    @Override
    protected Object determineCurrentLookupKey() {
        log.info("determineCurrentLookupKey in DynamicDataSource...");
        //确定最终的目标数据源
        return DynamicDataSourceHolder.getDbType();
    }
}

