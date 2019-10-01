package com.kdf.etl.config;

import org.apache.hadoop.hbase.HBaseConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.data.hadoop.hbase.HbaseTemplate;

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
}
