package com.kdf.etl.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kdf.etl.bean.PvAll;
import com.kdf.etl.repository.PvAllRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @ClassName: PvService
 * @Description: pv统计service
 * @author 王者の南が少ない 1715656022@qq.com
 * @date 2019年10月10日 上午11:04:44
 * 
 */
@Slf4j
@Service
public class PvService {

	@Autowired
	private HiveService hiveService;

	@Autowired
	private PvAllRepository pvAllRepository;

	public void getAllPv(String yearMonthDayHour) {
		log.info("===============数据allpv执行开始==============");
		Map<String, String> cntPv = hiveService.getAllPv(yearMonthDayHour);
		// insert db
		PvAll pvAll = new PvAll();
		pvAll.setAppid(cntPv.get("appid"));
		pvAll.setPvCount(Long.valueOf(cntPv.get("pv_count")));
//		pvAll.setRequestTime(requestTime);
		pvAllRepository.save(pvAll);
		log.info("===============数据allpv执行完成==============" + cntPv);
	}

}
