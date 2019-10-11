package com.kdf.etl.base;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BaseHadoop {
	static {
		// 这里修改成自己的路径
		System.setProperty("hadoop.home.dir", "D:\\Java\\winutils-master\\hadoop-3.0.0");
	}

	static String dfs = "yyyy_MM_dd_HH";

	public String getYyyyMmDdHh() {
		LocalDateTime ldtOne = LocalDateTime.now().minusHours(1);
		String nowDate = DateTimeFormatter.ofPattern(dfs).format(ldtOne);

		return nowDate;
	}
}
