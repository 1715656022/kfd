package com.kdf.etl.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

	public static Date StrToDate(Object yearMonthDayHour) {
		Date d;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yy_MM_dd_HH");
			d = sdf.parse((String) yearMonthDayHour);
		} catch (Exception e) {
			d = null;
			e.printStackTrace();
		}
		return d;
	}

}
