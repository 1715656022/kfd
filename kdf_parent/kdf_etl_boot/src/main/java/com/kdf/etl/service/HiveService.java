package com.kdf.etl.service;

import java.sql.ResultSet;
import java.sql.Statement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.hadoop.hive.HiveClient;
import org.springframework.data.hadoop.hive.HiveClientCallback;
import org.springframework.data.hadoop.hive.HiveTemplate;
import org.springframework.stereotype.Service;

@Service
public class HiveService {

	@Autowired
	private HiveTemplate hiveTemplate;

	public long getAllPv() {
		String hiveSql = "select count(*)  cnt from pv_log_hive";
		Long cnt = hiveTemplate.execute(new HiveClientCallback<Long>() {

			@Override
			public Long doInHive(HiveClient hiveClient) throws Exception {
				long contHive = 0;
				java.sql.Connection conn = hiveClient.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(hiveSql);
				// 处理数据
				while (rs.next()) {
					contHive = rs.getLong("cnt");
				}
				return contHive;
			}
		});
		return cnt;
	}
}
