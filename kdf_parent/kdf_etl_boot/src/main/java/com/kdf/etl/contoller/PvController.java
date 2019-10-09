package com.kdf.etl.contoller;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.hadoop.hive.HiveClient;
import org.springframework.data.hadoop.hive.HiveClientCallback;
import org.springframework.data.hadoop.hive.HiveTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.beust.jcommander.internal.Maps;
import com.kdf.etl.base.BaseHadoop;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/pv")
public class PvController  extends BaseHadoop{

	static final String ip = "192.168.31.37";

	private static final String HDFS_HOST = "hdfs://" + ip + ":9000";
	private static String ZK_HOST = ip + ":2181";
	private final static String TABLENAME = "pv_log_hive";// 表名
	public final static String COLF = "log";// 列族
	
	@Autowired
	private HiveTemplate hiveTemplate;
	
	@GetMapping("/test2")
	public void testHive() {
		String  hiveSql = "select * from pv_log_hive";
//		hiveTemplate.executeScript(scripts);
		Map maaaa = hiveTemplate.execute(new HiveClientCallback<Map>() {

			@Override
			public Map doInHive(HiveClient hiveClient) throws Exception {
				Map map = Maps.newHashMap();

				java.sql.Connection conn = hiveClient.getConnection();

				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(hiveSql);
				// 处理数据
				while (rs.next()) {
					String clientType = rs.getString("clientType");
					System.out.println(clientType);
					map.put("clientType", clientType);
					map.put("url", map.get("url"));
				}
				return map;
			}

		});
		
		System.out.println("111111111111111111111"+maaaa);
	}
	
}
