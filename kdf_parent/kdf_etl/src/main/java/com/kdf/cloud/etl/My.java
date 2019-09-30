package com.kdf.cloud.etl;

import java.io.IOException;

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

import com.kdf.cloud.base.BaseHadoop;

public class My extends BaseHadoop {
	public static void main(String[] args) throws Exception {
		Configuration conf = getConf();
		processArgs(conf, args);

		Job job = Job.getInstance(conf, My.class.getName());

		job.setJarByClass(My.class);
		job.setMapperClass(MyMapper.class);
		job.setMapOutputKeyClass(NullWritable.class);
		job.setMapOutputValueClass(Put.class);
		TableMapReduceUtil.initTableReducerJob("table_name_test", null, job, null, null, null, null, false);
//		job.setNumReduceTasks(0);

		job.setReducerClass(MyReduce.class);

		// 设置输入路径
		setJobInputPaths(job);
		job.waitForCompletion(true);
		System.out.println("===============执行完成==============");

	}

	private static Connection connection;

	public static Configuration getConf() {
		Configuration conf = new Configuration();
		conf.set("fs.defaultFS", "hdfs://192.168.31.37:9000");
		conf.set("hbase.zookeeper.quorum", "39.107.92.210:2181");
		conf = HBaseConfiguration.create(conf);
		try {
			connection = ConnectionFactory.createConnection(conf);
//			createTable();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return conf;
	}

	private final static String tableName = "table_name_test";// 表名
	private final static String colf = "fam_test";// 列族
	private final static String col = "info_test";// 列

	private static void createTable() throws IOException {

		Admin admin = connection.getAdmin();
		// 删除表
		if (admin.tableExists(TableName.valueOf(tableName))) {
			System.out.println("table is already exists!");
			admin.disableTable(TableName.valueOf(tableName));
			admin.deleteTable(TableName.valueOf(tableName));
		}
		// 创建表
		HTableDescriptor desc = new HTableDescriptor(TableName.valueOf(tableName));
		HColumnDescriptor family = new HColumnDescriptor(colf);
		desc.addFamily(family);
		admin.createTable(desc);

	}

	/**
	 * 处理参数
	 * 
	 * @param conf
	 * @param args
	 */
	private static void processArgs(Configuration conf, String[] args) {
		conf.set("file", "");
	}

	private static void setJobInputPaths(Job job) {
		Configuration conf = job.getConfiguration();
		FileSystem fs = null;
		try {
			fs = FileSystem.get(conf);
			Path inputPath = new Path("hdfs://192.168.31.37:9000/test.log");

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