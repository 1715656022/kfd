package com.kdf.etl.service;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kdf.etl.bean.UvBrowser;
import com.kdf.etl.repository.UvBrowserRepository;
import com.kdf.etl.utils.DateUtils;

/**
 * 
 * @title: UvService.java 
 * @package com.kdf.etl.service 
 * @description: uv统计service
 * @author: 、T
 * @date: 2019年10月11日 下午2:20:54 
 * @version: V1.0
 */
@Service
public class UvService {

	@Autowired
	private UvHiveService uvHiveService;
	
	@Autowired
	private UvBrowserRepository uvBrowerRepository;
	
	/**
	 * 
	 * @title: browserUv2MySql 
	 * @description: 统计浏览器的uv到mysql
	 * @author: 、T
	 * @date: 2019年10月11日 下午3:27:55
	 * @param yearMonthDayHour
	 * @throws:
	 */
	public void browserUv2MySql(String yearMonthDayHour) {
		List<Map<String, String>> uvList = uvHiveService.getBrowserUv(yearMonthDayHour);
		if(CollectionUtils.isNotEmpty(uvList)) {
			uvList.stream().forEach(map -> {
				String appid = String.valueOf(map.get("appid"));
				String browserName = String.valueOf(map.get("browserName"));
				Long count = Long.valueOf(String.valueOf(map.get("count")));
				UvBrowser uvBrowser = new UvBrowser();
				uvBrowser.setBrowserName(browserName);
				uvBrowser.setUvCount(count);
				uvBrowser.setAppid(appid);
				uvBrowser.setRequestTime(DateUtils.StrToDate(yearMonthDayHour));
				uvBrowerRepository.save(uvBrowser);
			});
		}
	}
	
	
}
