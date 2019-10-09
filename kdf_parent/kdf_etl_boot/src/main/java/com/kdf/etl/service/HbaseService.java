package com.kdf.etl.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.hadoop.hbase.HbaseTemplate;
import org.springframework.stereotype.Service;

@Service
public class HbaseService {
	
	@Autowired
	private HbaseTemplate hbaseTemplate;
	
	
	public void  createTable() {
//		hbaseTemplate.execute(tableName, action)
	}

}
