package com.kdf.cloud;

import java.io.IOException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import com.kdf.cloud.base.BaseHadoop;

public class MyTest extends BaseHadoop{

	public static class TestMap extends Mapper<LongWritable, Text, Text, LongWritable>{
		@Override
		protected void map(LongWritable key, Text value, Context context)
				throws IOException, InterruptedException {
			Text k = new Text();
			LongWritable v = new LongWritable();
			
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("d/MMM/yyyy:HH" ,Locale.ENGLISH);
			String dateString = sdf.format(date);
			try {
				// 从文件中分割出单词数组
				String[] words = value.toString().split(" ");
				for (String word : words) {
					if (StringUtils.isNotBlank(word) && word.contains(dateString)) {
						k.set(word);
						v.set(1l);// 未排序分组前，每个单词出现一次记为1
						context.write(k, v);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static class TestReduce extends Reducer<Text, LongWritable, Text, LongWritable> {
		@Override
		protected void reduce(Text key, Iterable<LongWritable> values, Context context)
				throws IOException, InterruptedException {
			long count = 0;
			// values就是key的所有值的集合，遍历这个集合并相加就是这个key出现的次数
			for (LongWritable value : values) {
				count += value.get();
			}
			// 将最终结果输出
			context.write(key, new LongWritable(count));
		}
	}
	
	/**
	 * 判断输出路径是否存在，存在则删除，不然会报错
	 * 
	 * @param conf
	 * @param outPath
	 * @throws Exception
	 */
	public static void deleteOutPath(Configuration conf, String outPath) throws Exception {
		FileSystem fileSystem = FileSystem.get(new URI(outPath), conf);
		if (fileSystem.exists(new Path(outPath))) {
			fileSystem.delete(new Path(outPath), true);
		}
	}
	
	/**
	 * 将Map和Reduce操作执行
	 */
	public static void main(String[] args) throws Exception {
		String inPath = "hdfs://192.168.31.37:9000/lss.log";// 文件路径
		String outDir = "hdfs://192.168.31.37:9000/word_count_lss";// 输出目录，输出是建立一个目录，具体的内容在目录下

		Configuration conf = new Configuration();
		// 获取job，告诉他需要加载那个类
		Job job = Job.getInstance(conf, WordCount.class.getSimpleName());
		// 制定job所在的jar包，打成jar包在hadoop运行必须设置
		job.setJarByClass(MyTest.class);
		// 指定自己实现的Mapper类
		job.setMapperClass(TestMap.class);
		// 指定自己实现的Reducer类
		job.setReducerClass(TestReduce.class);
		// 设置map阶段的K,V输出类型
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(LongWritable.class);
		// 设置最终输出的K,V输出类型
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(LongWritable.class);
		// 设置输入输出文件路径
		FileInputFormat.addInputPath(job, new Path(inPath));
		FileOutputFormat.setOutputPath(job, new Path(outDir));
		// 检查输出路径是否存在，若存在则删除
		deleteOutPath(conf, outDir);
		// 执行job，直到完成
		job.waitForCompletion(true);
		System.out.println("===============执行完成==============");
		
	}
	
}
