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
import com.kdf.etl.bean.UvTimeDistribution;
import com.kdf.etl.repository.UvTimeDistributionRepository;
import com.kdf.etl.utils.DateUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UvTimeDistributionService {

	@Autowired
	private HiveTemplate hiveTemplate;

	@Autowired
	private UvTimeDistributionRepository uvTimeDistributionRepository;

	public void saveUvTimeDistribution(String yearMonthDayHour) {
		log.info("star->getUvTimeDistribution:yearMonthDayHour={}", yearMonthDayHour);

		String sql = "select count(1) as uvCount, appid from pv_log_hive_" + yearMonthDayHour + " group by ip, appid";
		log.info("sql={}", sql);

		List<Map<String, String>> list = hiveTemplate.execute(new HiveClientCallback<List<Map<String, String>>>() {
			@Override
			public List<Map<String, String>> doInHive(HiveClient hiveClient) throws Exception {
				List<Map<String, String>> list = Lists.newArrayList();

				Connection conn = hiveClient.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql);

				Map<String, String> map = Maps.newHashMap();
				while (rs.next()) {
					map.keySet().removeIf(key -> key != "1");

					map.put("appid", rs.getString("appid"));
					map.put("uvCount", rs.getString("uvCount"));
					list.add(map);
				}
				return list;
			}
		});

		list.forEach(m -> {
			UvTimeDistribution uvTimeDistribution = new UvTimeDistribution();
			uvTimeDistribution.setAppid(m.get("appid"));
			uvTimeDistribution.setUvCount(Long.valueOf(m.get("uvCount")));
			uvTimeDistribution.setRequestTime(DateUtils.strToDate(yearMonthDayHour));
			uvTimeDistributionRepository.save(uvTimeDistribution);
		});

		log.info("end->getUvTimeDistribution");
	}

	public UvTimeDistribution getUvTimeDistribution(String yearMonthDayHour) {
		return uvTimeDistributionRepository.findByRequestTime(DateUtils.strToDate(yearMonthDayHour));
	}

	public List<UvTimeDistribution> getAreaUvTimeDistribution(String startYearMonthDayHour,
			String endYearMonthDayHour) {
		return uvTimeDistributionRepository.findByRequestTimeBetween(DateUtils.strToDate(startYearMonthDayHour),
				DateUtils.strToDate(endYearMonthDayHour));
	}

}
