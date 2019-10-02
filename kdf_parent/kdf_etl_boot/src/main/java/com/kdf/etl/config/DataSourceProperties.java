package com.kdf.etl.config;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;


@Data
@ConfigurationProperties(prefix = DataSourceProperties.DATASOURCE, ignoreUnknownFields = false)
public class DataSourceProperties {
	final static String DATASOURCE = "spring.datasource";

	private Map<String, String> hive;

	private Map<String, String> commonConfig;

}