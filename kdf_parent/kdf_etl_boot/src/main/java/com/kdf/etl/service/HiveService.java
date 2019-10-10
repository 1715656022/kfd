package com.kdf.etl.service;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.hadoop.hive.HiveClient;
import org.springframework.data.hadoop.hive.HiveClientCallback;
import org.springframework.data.hadoop.hive.HiveTemplate;
import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;

@Service
public class HiveService {

	@Autowired
	private HiveTemplate hiveTemplate;

	public Map<String, String> getAllPv(String yearMonthDayHour) {
		String hiveSql = "select count(*)  pv_count,appid from pv_log_hive_" + yearMonthDayHour + "group by appid ";
		Map<String, String> cnt = hiveTemplate.execute(new HiveClientCallback<Map<String, String>>() {
			@Override
			public Map<String, String> doInHive(HiveClient hiveClient) throws Exception {
				Map<String, String> cntMap = Maps.newHashMap();
				java.sql.Connection conn = hiveClient.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(hiveSql);
				// 处理数据
				while (rs.next()) {
					String cnt = rs.getString("cnt");
					String appid = rs.getString("appid");

					cntMap.put("cnt", cnt);
					cntMap.put("appid", appid);
				}
				return cntMap;
			}
		});
		return cnt;

	}
}
