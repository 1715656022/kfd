package com.kdf.etl.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.kdf.etl.base.BaseHadoop;
import com.kdf.etl.service.PvService;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class PvJob extends BaseHadoop {
	@Autowired
	private PvService pvService;

//	@Scheduled(fixedRate = 1000000)
	@Scheduled(cron = "0 0 */1 * * ?")
	public void allpv() throws Exception {
		System.out.println("=======xxxxxxxxxxxxxxxxxx");
		String yearMonthDayHour = getYyyyMmDdHh();
		pvService.saveAllPv(yearMonthDayHour);
	}
}
