package com.kdf.etl.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.hadoop.hive.HiveClient;
import org.springframework.data.hadoop.hive.HiveClientCallback;
import org.springframework.data.hadoop.hive.HiveTemplate;
import org.springframework.stereotype.Service;

import com.beust.jcommander.internal.Lists;
import com.google.common.collect.Maps;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class HiveService {

	@Autowired
	private HiveTemplate hiveTemplate;

	/**
	 * 总pv计算
	 * 
	 * @param yearMonthDayHour 年月日小时 时间戳
	 * @return 汇总结果
	 */
	public List<Map<String, String>> getAllPv(String yearMonthDayHour) {
		String hiveSql = "select count(1) pvCount,appid from pv_log_hive_" + yearMonthDayHour + " group by appid ";
		log.debug("getAllPv sql={}", hiveSql);
		List<Map<String, String>> listMap = hiveTemplate.execute((hiveClient) -> {
			List<Map<String, String>> list = Lists.newArrayList();
			Connection conn = hiveClient.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(hiveSql);
			Map<String, String> map = Maps.newHashMap();
			while (rs.next()) { 
				map.put("pvCount", rs.getString("pvCount"));
				map.put("appid", rs.getString("appid"));
				list.add(map);
			}
			return list;
		});
		return listMap;
	}

	public Map<String, String> getPvCountByYearMonthDayHour(String yearMonthDayHour) {
		String hiveSql = "select count(1) as pvCount from pv_log_hive_" + yearMonthDayHour;
		log.info("sql={}" + hiveSql);
		Map<String, String> resultMap = hiveTemplate.execute(new HiveClientCallback<Map<String, String>>() {
			@Override
			public Map doInHive(HiveClient hiveClient) throws Exception {
				Map<String, String> cntMap = Maps.newHashMap();
				java.sql.Connection conn = hiveClient.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(hiveSql);
				while (rs.next()) {
					String pvCount = rs.getString("pvCount");
					cntMap.put("pvCount", pvCount);
					cntMap.put("yearMonthDayHour", yearMonthDayHour);
				}
				return cntMap;
			}
		});
		return resultMap;
	}
}
