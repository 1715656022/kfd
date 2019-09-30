package com.kdf.cloud.etl;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

/**
 * 对集合中每一个元素进行计算 
 * 注意传入4个参数的类型 偏移量默认读取的是一行，但是将一行拆分后，偏移量会发生改变，但并不会重复读
 * 处理的原始数据，结果生产的是单个键值对（同键的不会合并）
 * 
 * @author PeiGuangTing
 *
 */
public class WordCountMapper extends Mapper<LongWritable, Text, Text, LongWritable> {

	protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, LongWritable>.Context context)
			throws IOException, InterruptedException {
		String line = value.toString();
		String[] words = line.split(" ");
		for (String word : words) {
			context.write(new Text(word), new LongWritable(1));
		}
	}

}
