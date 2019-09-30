package com.kdf.cloud;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import com.kdf.cloud.base.BaseHadoop;
import com.kdf.cloud.etl.WordCountMapper;
import com.kdf.cloud.etl.WordCountReducer;

/**
 * 继承本地hadoop插件
 * @author PeiGuangTing
 *
 */
public class WordCountMaster extends BaseHadoop {

	
	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		
		String inPath = "hdfs://192.168.31.37:9000/pgt.log";// 文件路径
		String outDir = "hdfs://192.168.31.37:9000/word_count_pgttest1";// 输出目录，输出是建立一个目录，具体的内容在目录下
		
		
		// 初始化配置
        Configuration conf = new Configuration();
        // 初始化job参数，指定job名称
        Job job = Job.getInstance(conf, WordCount.class.getSimpleName());
        // 设置运行job的类
        job.setJarByClass(WordCountMaster.class);
        // 设置Mapper类
        job.setMapperClass(WordCountMapper.class);
        // 设置Reducer类
        job.setReducerClass(WordCountReducer.class);
        // 设置Map的输出数据类型
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(LongWritable.class);
        // 设置Reducer的输出数据类型
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(LongWritable.class);
        // 设置输入的路径 -> 可以指定为某一路径或具体的文件
        FileInputFormat.setInputPaths(job, new Path(inPath));
        // 设置输出的路径 -> 最后一级路径自动生成，不会自动覆盖，需要手动修改
        FileOutputFormat.setOutputPath(job, new Path(outDir));
        // 提交job
        boolean result = job.waitForCompletion(true);
        // 执行成功后进行后续操作
        if (result) {
            System.out.println("Congratulations!");
        }
	}
}
