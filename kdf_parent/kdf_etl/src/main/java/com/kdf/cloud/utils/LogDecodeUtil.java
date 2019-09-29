package com.kdf.cloud.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

public class LogDecodeUtil {

//	private static String testStr = "GET /?clientType=Mozilla%2F5.0+%28Windows+NT+6.1%3B+Win64%3B+x64%29+AppleWebKit%2F537.36+%28KHTML%2C+like+Gecko%29+Chrome%2F77.0.3865.90+Safari%2F537.36&method=GET&port=62000&ip=192.168.31.8&url=http%3A%2F%2Flocalhost%3A8080%2Fadd HTTP/1.1";
	
	public static Map<String, String> log2Map(String logStr) {
		Map<String, String> resultMap = new HashMap<>();
		String subLogStr = logStr.substring(logStr.indexOf("?") + 1, logStr.lastIndexOf("H")).trim();
		String decodeLogStr = "";
		try {
			decodeLogStr = URLDecoder.decode(subLogStr, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String[] decodeLogArray = decodeLogStr.split("&");
		for(String s : decodeLogArray) {
			String[] mapStr = s.split("=");
			resultMap.put(mapStr[0], mapStr[1]);
		}
		return resultMap;
	}
}
