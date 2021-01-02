package com.smile24es.resource.management.conf;

import liquibase.integration.spring.SpringLiquibase;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * This configuration class setup class db configs.
 */
@Configuration
@EnableTransactionManagement
public class DataSourceConfig {

    @Value("${rmsdb.jdbc.driverClassName}")
    private String classDbJdbcDriverClassName;

    @Value("${rmsdb.jdbc.url}")
    private String classDbJdbcUrl;

    @Value("${rmsdb.jdbc.username}")
    private String classDbJdbcUsername;

    @Value("${rmsdb.jdbc.password}")
    private String classDbJdbcPassword;

    @Value("${rmsdb.test.on.borrow}")
    private boolean classDbTestOnBorrow;

    @Value("${rmsdb.initial.size}")
    private int classdbInitialSize;

    @Value("${rmsdb.max.active}")
    private int classDbMaxActive;

    @Value("${rmsdb.max.idle}")
    private int classDbMaxIdle;

    @Value("${rmsdb.min.idle}")
    private int classDbMinIdle;

    @Value("${rmsdb.max.wait}")
    private int classDbMaxWait;

    @Value("${rmsdb.num.tests.per.eviction.run}")
    private int classDbNumTestsPerEvictionRun;

    @Value("${rmsdb.time.between.eviction.run}")
    private long classDbTimeBetweenEvictionRun;

    @Value("${rmsdb.min.evictable.idle.time}")
    private long classDbMinEvictableIdleTime;

    @Bean(destroyMethod = "close", name = "rmsDataSource")
    @Lazy(true)
    public BasicDataSource classDataSource() {
        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setDriverClassName(classDbJdbcDriverClassName);
        basicDataSource.setUrl(classDbJdbcUrl);
        basicDataSource.setUsername(classDbJdbcUsername);
        basicDataSource.setPassword(classDbJdbcPassword);
        basicDataSource.setDefaultAutoCommit(true);
        basicDataSource.setValidationQuery("SELECT 1");
        basicDataSource.setTestOnBorrow(classDbTestOnBorrow);
        basicDataSource.setInitialSize(classdbInitialSize);
        basicDataSource.setMaxActive(classDbMaxActive);
        basicDataSource.setMaxIdle(classDbMaxIdle);
        basicDataSource.setMinIdle(classDbMinIdle);
        basicDataSource.setMaxWait(classDbMaxWait);
        basicDataSource.setNumTestsPerEvictionRun(classDbNumTestsPerEvictionRun);
        basicDataSource.setTimeBetweenEvictionRunsMillis(classDbTimeBetweenEvictionRun);
        basicDataSource.setMinEvictableIdleTimeMillis(classDbMinEvictableIdleTime);

        return basicDataSource;
    }

    @Bean
    public DataSourceTransactionManager transactionManager() {
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
        dataSourceTransactionManager.setDataSource(classDataSource());
        return dataSourceTransactionManager;
    }

    @Bean(name = "liquibase")
    public SpringLiquibase setSpringLiquibaseBean() {
        SpringLiquibase springLiquibase = new SpringLiquibase();
        springLiquibase.setDataSource(classDataSource());
        springLiquibase.setChangeLog("classpath:db/db.changelog-master.yaml");
        return springLiquibase;
    }

}
