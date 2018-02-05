package com.rejoice.active.console.config;

import java.sql.SQLException;
import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.ApplicationContextException;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.alibaba.druid.pool.DruidDataSource;

@Configuration
@EnableTransactionManagement
@MapperScan(basePackages = "com.rejoice.active.console.mapper")
public class DataSourceConfig implements EnvironmentAware { 
    private Environment environment; 
    private RelaxedPropertyResolver propertyResolver; 
    @Override 
    public void setEnvironment(Environment environment) { 
      this.environment = environment; 
      this.propertyResolver = new RelaxedPropertyResolver(environment,"spring.datasource."); 
    } 
    //注册dataSource 
    @Bean(initMethod = "init", destroyMethod = "close") 
    public DruidDataSource dataSource() throws SQLException { 
      if (StringUtils.isBlank(propertyResolver.getProperty("url"))) { 
        System.out.println("Your database connection pool configuration is incorrect!" 
            + " Please check your Spring profile, current profiles are:"
            + Arrays.toString(environment.getActiveProfiles())); 
         throw new ApplicationContextException( 
            "Database connection pool is not configured correctly"); 
      } 
      DruidDataSource druidDataSource = new DruidDataSource(); 
      druidDataSource.setDriverClassName(propertyResolver.getProperty("driver-class-name")); 
      druidDataSource.setUrl(propertyResolver.getProperty("url")); 
      druidDataSource.setUsername(propertyResolver.getProperty("username")); 
      druidDataSource.setPassword(propertyResolver.getProperty("password")); 
      druidDataSource.setInitialSize(Integer.parseInt(propertyResolver.getProperty("initialSize"))); 
      druidDataSource.setMinIdle(Integer.parseInt(propertyResolver.getProperty("minIdle"))); 
      druidDataSource.setMaxActive(Integer.parseInt(propertyResolver.getProperty("maxActive"))); 
      druidDataSource.setMaxWait(Integer.parseInt(propertyResolver.getProperty("maxWait"))); 
      druidDataSource.setTimeBetweenEvictionRunsMillis(Long.parseLong(propertyResolver.getProperty("timeBetweenEvictionRunsMillis"))); 
      druidDataSource.setMinEvictableIdleTimeMillis(Long.parseLong(propertyResolver.getProperty("minEvictableIdleTimeMillis"))); 
      druidDataSource.setValidationQuery(propertyResolver.getProperty("validationQuery")); 
      druidDataSource.setTestWhileIdle(Boolean.parseBoolean(propertyResolver.getProperty("testWhileIdle"))); 
      druidDataSource.setTestOnBorrow(Boolean.parseBoolean(propertyResolver.getProperty("testOnBorrow"))); 
      druidDataSource.setTestOnReturn(Boolean.parseBoolean(propertyResolver.getProperty("testOnReturn"))); 
      druidDataSource.setPoolPreparedStatements(Boolean.parseBoolean(propertyResolver.getProperty("poolPreparedStatements"))); 
      druidDataSource.setMaxPoolPreparedStatementPerConnectionSize(Integer.parseInt(propertyResolver.getProperty("maxPoolPreparedStatementPerConnectionSize"))); 
      druidDataSource.setFilters(propertyResolver.getProperty("filters")); 
      return druidDataSource; 
    } 
}