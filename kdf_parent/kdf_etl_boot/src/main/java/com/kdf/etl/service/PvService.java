package com.kdf.etl.service;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beust.jcommander.internal.Lists;
import com.kdf.etl.bean.PvAll;
import com.kdf.etl.repository.PvAllRepository;
import com.kdf.etl.utils.DateUtils;

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

	public void saveAllPv(String yearMonthDayHour) {
		log.info("===============数据allpv执行开始==============");
		List<PvAll> list = hiveService.getAllPv(yearMonthDayHour);

		List<PvAll> pvAllList = Lists.newArrayList();
		pvAllRepository.saveAll(pvAllList);
		log.info("===============数据allpv执行完成==============" + list);
	}

	public Map<String, String> getPvCountByYearMonthDayHour(String yearMonthDayHour) throws ParseException {
		Map<String, String> cntPv = hiveService.getPvCountByYearMonthDayHour(yearMonthDayHour);
		// insert db
		PvAll pvAll = new PvAll();
		pvAll.setPvCount(Long.valueOf(cntPv.get("pvCount")));
		pvAll.setRequestTime(DateUtils.strToDate(yearMonthDayHour));
		pvAllRepository.save(pvAll);
		return cntPv;
	}

}
