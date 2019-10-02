package com.kdf.etl.utils;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import com.kdf.etl.utils.UserAgentUtil.UserAgentInfo;

import lombok.Data;

public class LogDecodeUtil {

	private static String testStr = "GET /?clientType=Mozilla%2F5.0+%28Windows+NT+6.1%3B+Win64%3B+x64%29+AppleWebKit%2F537.36+%28KHTML%2C+like+Gecko%29+Chrome%2F77.0.3865.90+Safari%2F537.36&method=GET&port=62000&ip=192.168.31.8&url=http%3A%2F%2Flocalhost%3A8080%2Fadd HTTP/1.1";

	public static void main(String[] args) {
		System.out.println(log2Map(testStr));
	}

	public static Map<String, String> log2Map(String logStr) {
		System.out.println("====================" + logStr);
		Map<String, String> resultMap = new HashMap<>();
		try {
			String subLogStr = logStr.substring(logStr.indexOf("?") + 1, logStr.lastIndexOf("H")).trim();
			String decodeLogStr = "";
			decodeLogStr = URLDecoder.decode(subLogStr, "utf-8");

			String[] decodeLogArray = decodeLogStr.split("&");
			int i = 0;
			for (String s : decodeLogArray) {
				String[] mapStr = s.split("=");
				if (i == 1) {
					resultMap.put(mapStr[0], UserAgentUtil.analyticUserAgent(mapStr[1]).getBrowserName());
				} else {

					resultMap.put(mapStr[0], mapStr[1]);
				}
				i++;
			}
			

//			LogAgent agent = new LogDecodeUtil().new LogAgent();
//			String clientType = decodeLogArray[0].split("=")[1];
//			UserAgentInfo userAgentInfo = UserAgentUtil.analyticUserAgent(clientType);
//			clientType = userAgentInfo.getBrowserName();
//			agent.setClientType(clientType);
//
//			String method = decodeLogArray[1].split("=")[1];
//			agent.setMethod(method);
//
//			String port = decodeLogArray[2].split("=")[1];
//			agent.setPort(port);
//
//			String ip = decodeLogArray[3].split("=")[1];
//			agent.setIp(ip);
//
//			String url = decodeLogArray[3].split("=")[1];
//			agent.setUrl(url);

		} catch (Exception e) {
//			e.printStackTrace();
		}

		return resultMap;
	}

	public static String log2Str(String logStr) {
		String resultStr = "";
		try {
			String subLogStr = logStr.substring(logStr.indexOf("?") + 1, logStr.lastIndexOf("H")).trim();
			resultStr = URLDecoder.decode(subLogStr, "utf-8");
		} catch (Exception e) {
//			e.printStackTrace();
		}
		return resultStr;
	}

	@Data
	class LogAgent {
		private String clientType;
		private String method;
		private String ip;
		private String port;
		private String url;
	}
}
