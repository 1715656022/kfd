package com.kdf.cloud.etl;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import com.kdf.cloud.base.BaseHadoop;

public class MrRunner extends BaseHadoop implements Tool {
	private Configuration conf = null;

	public static void main(String[] args) {
		try {
			ToolRunner.run(new Configuration(), new MrRunner(), args);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void setConf(Configuration conf) {
		conf.set("fs.defaultFS", "hdfs://192.168.31.37:9000");
		conf.set("hbase.zookeeper.quorum", "39.107.92.210:2181");
		this.conf = HBaseConfiguration.create(conf);
	}

	@Override
	public Configuration getConf() {
		return this.conf;
	}

	@Override
	public int run(String[] args) throws Exception {
		Configuration conf = this.getConf();
		this.processArgs(conf, args);

		Job job = Job.getInstance(conf, "test_name");

		job.setJarByClass(MrRunner.class);
		job.setMapperClass(MyMapper.class);
		job.setMapOutputKeyClass(NullWritable.class);
		job.setMapOutputValueClass(Put.class);
		TableMapReduceUtil.initTableReducerJob("table_name_test", null, job, null, null, null, null, false);
		job.setNumReduceTasks(0);

		// 设置输入路径
		this.setJobInputPaths(job);
		return job.waitForCompletion(true) ? 0 : -1;
	}

	/**
	 * 处理参数
	 * 
	 * @param conf
	 * @param args
	 */
	private void processArgs(Configuration conf, String[] args) {
		conf.set("file", "");
	}

	private void setJobInputPaths(Job job) {
		Configuration conf = job.getConfiguration();
		FileSystem fs = null;
		try {
			fs = FileSystem.get(conf);
			Path inputPath = new Path("hdfs://192.168.31.37:9000/hadoop-root-datanode-slave.log");

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
