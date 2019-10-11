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

		String sql = "select count(1) as uvCount from pv_log_hive_" + yearMonthDayHour + " group by ip";
		log.info("sql={}", sql);

		Map<String, Object> map = hiveTemplate.execute(new HiveClientCallback<Map<String, Object>>() {
			@Override
			public Map<String, Object> doInHive(HiveClient hiveClient) throws Exception {
				Map<String, Object> map = Maps.newHashMap();

				Connection conn = hiveClient.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql);
				while (rs.next()) {
					map.put("uvCount", rs.getString("uvCount"));
					map.put("yearMonthDayHour", yearMonthDayHour);
				}
				return map;
			}
		});

		UvTimeDistribution uvTimeDistribution = new UvTimeDistribution();
		uvTimeDistribution.setUvCount(Long.valueOf(String.valueOf(map.get("uvCount"))));
		uvTimeDistribution.setRequestTime(DateUtils.StrToDate(map.get("yearMonthDayHour")));
		uvTimeDistributionRepository.save(uvTimeDistribution);

		log.info("end->getUvTimeDistribution");
	}

	public UvTimeDistribution getUvTimeDistribution(String yearMonthDayHour) {
		return uvTimeDistributionRepository.findByRequestTime(DateUtils.StrToDate(yearMonthDayHour));
	}

	public List<UvTimeDistribution> getAreaUvTimeDistribution(String startYearMonthDayHour,
			String endYearMonthDayHour) {
		return uvTimeDistributionRepository.findByRequestTimeBetween(DateUtils.StrToDate(startYearMonthDayHour),
				DateUtils.StrToDate(endYearMonthDayHour));
	}

}
