package com.kdf.cloud.etl;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

/**
 * reduce
 * 对集合中的每一个元素进行迭代计算
 * 收到的是一个键对应一个值的集合
 * 通过功能对值的集合进行运算，处理完的数据直接输出到指定文件
 * @author PeiGuangTing
 *
 */
public class WordCountReducer extends Reducer<Text, LongWritable, Text, LongWritable> {

	
	protected void reduce(Text key, Iterable<LongWritable> value,
			Reducer<Text, LongWritable, Text, LongWritable>.Context context) throws IOException, InterruptedException {

		int count = 0;
		for (LongWritable LongWritable : value) {
			count += LongWritable.get();
		}
		context.write(new Text(key), new LongWritable(count));
	}
	
}
