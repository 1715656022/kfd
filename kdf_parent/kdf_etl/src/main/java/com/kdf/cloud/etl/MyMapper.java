package com.kdf.cloud.etl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.zip.CRC32;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import com.kdf.cloud.utils.UserAgentUtil;
import com.kdf.cloud.utils.UserAgentUtil.UserAgentInfo;

/**
 * 自定义数据解析map类
 * 
 * @author root
 *
 */
public class MyMapper extends Mapper<LongWritable, Text, NullWritable, Put> {

	private int inputCount, filterCount, outputCount;
	private byte[] family = Bytes.toBytes("test_name");
	private CRC32 crc32 = new CRC32();

	@Override
	protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
		this.inputCount++;
		System.out.println("Analyse data of :" + value);

		try {
			// 解析日志
//			Map<String, String> clientInfo = LoggerUtil.handleLog(value.toString());
			
			UserAgentInfo agentInfo = UserAgentUtil.analyticUserAgent(value.toString());
			Map  clientInfo = new HashMap<>();
			clientInfo.put("aaa", "aaaa");
			clientInfo.put("bbb", "bbbb");
			// 处理数据
			this.handleData(clientInfo, context);
			this.filterCount++;
		} catch (Exception e) {
			this.filterCount++;
			e.printStackTrace();
		}
	}

	@Override
	protected void cleanup(Context context) throws IOException, InterruptedException {
		super.cleanup(context);
		System.out.println(("输入数据:" + this.inputCount + "；输出数据:" + this.outputCount + "；过滤数据:" + this.filterCount));
	}

	/**
	 * 具体处理数据的方法
	 * 
	 * @param clientInfo
	 * @param context
	 * @param event
	 * @throws InterruptedException
	 * @throws IOException
	 */
	private void handleData(Map<String, String> clientInfo, Context context) throws IOException, InterruptedException {
		String uuid = UUID.randomUUID().toString();
		String memberId = "pc_no";//机器号
		String rowkey = this.generateRowKey(uuid, memberId, "");
		Put put = new Put(Bytes.toBytes(rowkey));
		for (Map.Entry<String, String> entry : clientInfo.entrySet()) {
			if (StringUtils.isNotBlank(entry.getKey()) && StringUtils.isNotBlank(entry.getValue())) {
				put.add(family, Bytes.toBytes(entry.getKey()), Bytes.toBytes(entry.getValue()));
			}
		}
		context.write(NullWritable.get(), put);
		this.outputCount++;
	}

	/**
	 * 根据uuid memberid servertime创建rowkey
	 * 
	 * @param uuid
	 * @param memberId
	 * @param eventAliasName
	 * @param serverTime
	 * @return
	 */
	private String generateRowKey(String uuid, String memberId, String serverTime) {
		StringBuilder sb = new StringBuilder();
		sb.append(serverTime).append("_");
		this.crc32.reset();
		if (StringUtils.isNotBlank(uuid)) {
			this.crc32.update(uuid.getBytes());
		}
		if (StringUtils.isNotBlank(memberId)) {
			this.crc32.update(memberId.getBytes());
		}
		sb.append(this.crc32.getValue() % 100000000L);
		return sb.toString();
	}
}
