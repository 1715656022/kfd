package com.kdf.etl.service;

import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.springframework.stereotype.Service;

@Service
public class HbaseService extends BaseService {

	public void createTable(String tableNameStr, String colf) {

		Admin admin;
		try {
			getConf();
			admin = connection.getAdmin();
			// 删除表
			TableName tableName = TableName.valueOf(tableNameStr);
			if (admin.tableExists(tableName)) {
				System.out.println("table is already exists!");
				// 删除表
//			admin.disableTable(tableName);
//			admin.deleteTable(tableName);
			} else {
				// 创建表
				HTableDescriptor desc = new HTableDescriptor(tableName);
				HColumnDescriptor family = new HColumnDescriptor(colf);
				desc.addFamily(family);
				admin.createTable(desc);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
