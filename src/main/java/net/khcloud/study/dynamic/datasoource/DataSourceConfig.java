package net.khcloud.study.dynamic.datasoource;

import net.khcloud.study.dynamic.Interceptor.DynamicDataSourceInterceptor;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Import({DynamicDataSourceHolder.class, DynamicDataSourceInterceptor.class})
@Configuration
@Slf4j
public class DataSourceConfig {
    @Value("${mybatis.mapper-locations}")
    private String mapperLocation;

    @Autowired
    private DynamicDataSourceInterceptor dynamicDataSourceInterceptor;

    @Autowired
    private MasterProperties masterProperties;

    @Autowired
    private SlaveProperties slaveProperties;

    //默认是master数据源
    @Bean(name = "masterDataSource")
    @Primary
    public DataSource masterProperties(){
        log.info("masterDataSource初始化");
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(masterProperties.getUrl());
        dataSource.setUsername(masterProperties.getUsername());
        dataSource.setPassword(masterProperties.getPassword());
        dataSource.setDriverClassName(masterProperties.getDriverClassName());
        return dataSource;
    }

    @Bean(name = "slaveDataSource")
    public DataSource dataBase2DataSource(){
        log.info("slaveDataSource初始化");
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(slaveProperties.getUrl());
        if(!StringUtils.isEmpty(slaveProperties.getUsername()))
            dataSource.setUsername(slaveProperties.getUsername());
        if(!StringUtils.isEmpty(slaveProperties.getPassword()))
            dataSource.setPassword(slaveProperties.getPassword());
        dataSource.setDriverClassName(slaveProperties.getDriverClassName());
        return dataSource;
    }


//    @Bean(name = "dynamicDataSource")
    @Bean(name = "dynDataSource")
    public DynamicDataSource DataSource(@Qualifier("masterDataSource") DataSource masterDataSource,
                                        @Qualifier("slaveDataSource") DataSource slaveDataSource) {
        //配置多数据源
        Map<Object, Object> targetDSMap = new HashMap<>();
        targetDSMap.put(DynamicDataSourceHolder.DB_MASTER, masterDataSource);
        targetDSMap.put(DynamicDataSourceHolder.DB_SLAVE, slaveDataSource);
        DynamicDataSource dataSource = new DynamicDataSource();
        //多数据源
        dataSource.setTargetDataSources(targetDSMap);
        //默认数据源
        dataSource.setDefaultTargetDataSource(masterDataSource);
        return dataSource;
    }

    @Bean(name = "SqlSessionFactory")
    public SqlSessionFactory test1SqlSessionFactory(@Qualifier("dynamicDataSource") DataSource dynamicDataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dynamicDataSource);

        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = resolver.getResources(mapperLocation);
        bean.setMapperLocations(resources);
        bean.setPlugins(new Interceptor[]{new DynamicDataSourceInterceptor()});

        return bean.getObject();
    }

    @Bean(name = "JdbcTemplate")
    public JdbcTemplate test1JdbcTemplate(@Qualifier("dynamicDataSource") DataSource dynamicDataSource) {
        return new JdbcTemplate(dynamicDataSource);
    }
}

