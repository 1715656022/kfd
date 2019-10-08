package com.kdf.etl.config;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.hadoop.hbase.HBaseConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.hadoop.hbase.HbaseTemplate;
import org.springframework.data.hadoop.hive.HiveClientFactory;
import org.springframework.data.hadoop.hive.HiveClientFactoryBean;
import org.springframework.data.hadoop.hive.HiveTemplate;

import com.alibaba.druid.pool.DruidDataSource;
//@Configurable
@Configuration
public class InitConfig {
	@Bean
	public HbaseTemplate hbaseTemplate(@Value("${hbase.zookeeper.quorum}") String quorum) {
		HbaseTemplate hbaseTemplate = new HbaseTemplate();
		org.apache.hadoop.conf.Configuration conf = HBaseConfiguration.create();
		conf.set("hbase.zookeeper.quorum", quorum);
//		conf.set("hbase.zookeeper.port", port);
		hbaseTemplate.setConfiguration(conf);
		hbaseTemplate.setAutoFlush(true);
		return hbaseTemplate;
	}

	@Bean() // 新建bean实例
	
	@Qualifier("hiveTemplate")//标识

	public HiveTemplate hiveTemplate() {
		System.out.println("1111111111111111111111111111111111");
//		DruidDataSource hiveDruidDataSource = dataSource() ;
		HiveClientFactoryBean hiveClientFactoryBean = new HiveClientFactoryBean();
		hiveClientFactoryBean.setHiveDataSource(dataSource());
		try {
			hiveClientFactoryBean.afterPropertiesSet();
		} catch (Exception e) {
			e.printStackTrace();
		}
		HiveClientFactory hiveClientFactory = hiveClientFactoryBean.getObject();
		HiveTemplate hiveTemplate = new HiveTemplate(hiveClientFactory);
		return hiveTemplate;
	}

	@Autowired
	private DataSourceProperties dataSourceProperties;

	@Autowired
	private DataSourceCommonProperties dataSourceCommonProperties;

	public DataSource dataSource() {
		DruidDataSource datasource = new DruidDataSource();

		// 配置数据源属性
		datasource.setUrl(dataSourceProperties.getHive().get("url"));
		datasource.setUsername(dataSourceProperties.getHive().get("username"));
		datasource.setPassword(dataSourceProperties.getHive().get("password"));
		datasource.setDriverClassName(dataSourceProperties.getHive().get("driver-class-name"));

		// 配置统一属性
		datasource.setInitialSize(dataSourceCommonProperties.getInitialSize());
		datasource.setMinIdle(dataSourceCommonProperties.getMinIdle());
		datasource.setMaxActive(dataSourceCommonProperties.getMaxActive());
		datasource.setMaxWait(dataSourceCommonProperties.getMaxWait());
		datasource.setTimeBetweenEvictionRunsMillis(dataSourceCommonProperties.getTimeBetweenEvictionRunsMillis());
		datasource.setMinEvictableIdleTimeMillis(dataSourceCommonProperties.getMinEvictableIdleTimeMillis());
		datasource.setValidationQuery(dataSourceCommonProperties.getValidationQuery());
		datasource.setTestWhileIdle(dataSourceCommonProperties.isTestWhileIdle());
		datasource.setTestOnBorrow(dataSourceCommonProperties.isTestOnBorrow());
		datasource.setTestOnReturn(dataSourceCommonProperties.isTestOnReturn());
		datasource.setPoolPreparedStatements(dataSourceCommonProperties.isPoolPreparedStatements());
		try {
			datasource.setFilters(dataSourceCommonProperties.getFilters());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return datasource;
	}

}
