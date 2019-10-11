package com.kdf.etl.contoller;

import java.util.List;
import java.util.Map;

import org.jboss.logging.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kdf.etl.base.BaseHadoop;
import com.kdf.etl.service.HiveService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/pv")
public class PvController  extends BaseHadoop{
	
	@Autowired
	private HiveService hiveService;
	
	@GetMapping("/testPv")
	public Map testPv(@RequestParam("yearMonthDayHour") String yearMonthDayHour) {
		log.info("===============数据testPv执行开始==============\"");
		// List dataList = hiveService.getAllPvByTime();
		Map dataMap = hiveService.getPvCountByYearMonthDayHour(yearMonthDayHour);
		log.info("===============数据testPv执行完成==============");
		return dataMap;
	}
	
}
