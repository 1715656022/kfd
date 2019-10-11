package com.kdf.etl.contoller;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.hadoop.hbase.HbaseSynchronizationManager;
import org.springframework.data.hadoop.hive.HiveClient;
import org.springframework.data.hadoop.hive.HiveClientCallback;
import org.springframework.data.hadoop.hive.HiveTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.beust.jcommander.internal.Maps;
import com.google.common.collect.Lists;
import com.kdf.etl.base.BaseHadoop;
import com.kdf.etl.bean.PvHttpClient;
import com.kdf.etl.hadoop.MyMapper;
import com.kdf.etl.service.HttpClientPVService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/test4")
public class HttpClientPVController extends BaseHadoop {

//	@Autowired
//	private HbaseTemplate hbaseTemplate;

	static final String ip = "192.168.31.37";

	private static final String HDFS_HOST = "hdfs://" + ip + ":9000";
	private static String ZK_HOST = ip + ":2181";
	private final static String TABLENAME = "my_test_hive";// 表名
	public final static String COLF = "log";// 列族

	@Autowired
	private HiveTemplate hiveTemplate;
	
	@Autowired
	private HttpClientPVService httpClientPVService;

	@GetMapping
	public String test() throws Exception {

		List<String> query = hiveTemplate.query("select clientType  from my_test_hive limit 0,10");

		System.out.println("=================" + query);
		for (String x : query) {
			System.out.println(x + "============================================================================");
		}
		return "pook";
	}
	@GetMapping("/test4")
	public void testHive() {
		String  hiveSql = "select * from aapv_log_hive_2019_10_10_10";
		
//		hiveTemplate.executeScript(scripts);
		List<Map<String, Object>> list = hiveTemplate.execute(new HiveClientCallback<List<Map<String, Object>>>() {

			@Override
			public List<Map<String, Object>> doInHive(HiveClient hiveClient) throws Exception {
				
				List<Map<String, Object>> list = Lists.newArrayList();
				java.sql.Connection conn = hiveClient.getConnection();

				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(hiveSql);
				// 处理数据
				while (rs.next()) {
					Map<String, Object> map = Maps.newHashMap();
					map.put("key", rs.getString("key"));
					map.put("appid", rs.getString("appid"));
					map.put("user_agent", rs.getString("user_agent"));
					map.put("method", rs.getString("method"));
					map.put("ip", rs.getString("ip"));
					map.put("port", rs.getString("port"));
					map.put("url", rs.getString("url"));
					map.put("request_time", rs.getString("request_time"));
					list.add(map);
				}
				return list;
			}

		});
		for (Map<String, Object> map : list) {
			PvHttpClient pvHttpClient = new PvHttpClient();
			pvHttpClient.setIp(map.get("ip").toString());
			pvHttpClient.setMethod(map.get("method").toString());
//			pvHttpClient.setPvCount( Long.parseLong( map.get("pvCount").toString() ) );
			pvHttpClient.setCreateTime(new Date());
			pvHttpClient.setRequestTime(TimestampToDate( Integer.parseInt( map.get("request_time").toString() ) ));
			httpClientPVService.save(pvHttpClient);
		}
		System.out.println("111111111111111111111"+list);
	}
	
	/**
	 * 10位时间戳转Date
	 * @param time
	 * @return
	 */
	public static Date TimestampToDate(Integer time){
		long temp = (long)time*1000;
		Timestamp ts = new Timestamp(temp);  
        Date date = new Date();  
        try {  
            date = ts;  
            //System.out.println(date);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return date;
	}


	private static Connection connection;

	public Configuration getConf() {
		Configuration conf = new Configuration();
		conf.set("fs.defaultFS", HDFS_HOST);
		conf.set("hbase.zookeeper.quorum", ZK_HOST);
		conf = HBaseConfiguration.create(conf);
		try {
			connection = ConnectionFactory.createConnection(conf);
			createTable();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return conf;
	}

	private void createTable() throws IOException {
		Admin admin = connection.getAdmin();
		TableName tableName = TableName.valueOf(TABLENAME);
		if (admin.tableExists(tableName)) {
			System.out.println("0table is already exists!");
			// 删除表
//		admin.disableTable(tableName);
//		admin.deleteTable(tableName);
		} else {
			// 创建表
			HTableDescriptor desc = new HTableDescriptor(tableName);
			HColumnDescriptor family = new HColumnDescriptor(COLF);
			desc.addFamily(family);
			admin.createTable(desc);
		}

//		 finally {
//	            try {
//	                if (admin != null) {
//	                    admin.close();
//	                }
//
//	                if (connection != null && !connection.isClosed()) {
//	                    connection.close();
//	                }
//	            } catch (Exception e2) {
//	                e2.printStackTrace();
//	            }
//	        }

	}

	/**
	 * 处理参数
	 * 
	 * @param conf
	 * @param args
	 */
	private void processArgs(Configuration conf) {
		conf.set("file", "");
	}

	private static void setJobInputPaths(Job job) {
		Configuration conf = job.getConfiguration();
		FileSystem fs = null;
		try {
			fs = FileSystem.get(conf);
			Path inputPath = new Path(HDFS_HOST + "/test.log");
			if (!fs.exists(inputPath)) {
				throw new RuntimeException("文件不存在:" + inputPath);
			}
			FileInputFormat.addInputPath(job, inputPath);
		} catch (IOException e) {
			throw new RuntimeException("设置输入路径出现异常", e);
		} finally {
			if (fs != null) {
				try {
					fs.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
