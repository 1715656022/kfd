package com.kdf.etl.contoller;

import java.io.IOException;

import javax.validation.constraints.Future;

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
import org.springframework.data.hadoop.hbase.HbaseTemplate;
import org.springframework.data.hadoop.hive.HiveTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kdf.etl.base.BaseHadoop;
import com.kdf.etl.hadoop.MyMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/test2")
public class TestController2 extends BaseHadoop {
	
//	@Autowired
//	private HbaseTemplate hbaseTemplate;
	
//	@Autowired
//	private HiveTemplate hiveTemplate;

	@GetMapping
	@Future
	public String test() throws Exception {
		

		Configuration conf = getConf();
		processArgs(conf);
		Job job = Job.getInstance(conf, TestController2.class.getName());
		job.setJarByClass(TestController2.class);
		job.setMapperClass(MyMapper.class);
		job.setMapOutputKeyClass(NullWritable.class);
		job.setMapOutputValueClass(Put.class);
		TableMapReduceUtil.initTableReducerJob(TABLENAME, null, job, null, null, null, null, false);
		job.setNumReduceTasks(0);
//		job.setReducerClass(MyReduce.class);
		// 设置输入路径
		setJobInputPaths(job);
		job.waitForCompletion(true);
		log.info("===============执行完成==============");
		
		System.out.println(HbaseSynchronizationManager.getTableNames()+"========================");

		return "pook";
	}

	private static String ZK_HOST = "master:2181";
	private final static String TABLENAME = "table_name_test";// 表名
	private final static String COLF = "clientType";// 列族

	public static void main(String[] args) throws Exception {

	}

	private static Connection connection;
	

	public  Configuration getConf() {
		Configuration conf = new Configuration();
		conf.set("fs.defaultFS", "hdfs://192.168.0.105:9000");
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

	private  void createTable() throws IOException {

		Admin admin = connection.getAdmin();
		// 删除表
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
	private  void processArgs(Configuration conf) {
		conf.set("file", "");
	}

	private static void setJobInputPaths(Job job) {
		Configuration conf = job.getConfiguration();
		FileSystem fs = null;
		try {
			fs = FileSystem.get(conf);
			Path inputPath = new Path("hdfs://192.168.0.105:9000/test.log");

			if (fs.exists(inputPath)) {
				FileInputFormat.addInputPath(job, inputPath);
			} else {
				throw new RuntimeException("文件不存在:" + inputPath);
			}
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
