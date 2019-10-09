package com.kdf.etl.service;

import org.apache.hadoop.hbase.client.HTableInterfaceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.hadoop.hbase.HbaseTemplate;
import org.springframework.stereotype.Service;

@Service
public class HbaseService {
	
	@Autowired
	private HbaseTemplate hbaseTemplate;
	
	
	public void  createTable() {
		HTableInterfaceFactory f = hbaseTemplate.getTableFactory();
//		hbaseTemplate.execute(tableName, action)
	}

}
