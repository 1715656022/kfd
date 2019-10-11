package com.kdf.etl.contoller.pv;

import java.text.ParseException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kdf.etl.service.HiveService;
import com.kdf.etl.service.PvService;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @ClassName: AllPvController
 * @Description: AllPvController
 * @author 王者の南が少ない 1715656022@qq.com
 * @date 2019年10月9日 下午3:04:44
 * 
 */
@Slf4j
@RestController
@RequestMapping("/allpv")
public class AllPvController {

	@Autowired
	private PvService pvService;
	
	@GetMapping()
	public String start() throws Exception {
		log.info("===============数据allpv执行开始==============\"");

//		long cntPv = hiveService.getAllPv();
//		System.out.println("===============" + cntPv);
		
//		pvService.getAllPv(yearMonthDayHour);
		// insert db
		log.info("===============数据allpv执行完成==============");

		return "ok";
	}
	
	/**
	 * 
	 * @Title: pvByTime   
	 * @Description: 总pv时间分布图
	 * @param yearMonthDayHour
	 * @return: Map      
	 */
	@GetMapping("/pvByTime")
	public Map pvByTime(@RequestParam("yearMonthDayHour") String yearMonthDayHour) {
		log.info("===============数据pvByTime执行开始==============\"");
		// List dataList = hiveService.getAllPvByTime();
		Map dataMap = null;
		try {
			dataMap = pvService.getPvCountByYearMonthDayHour(yearMonthDayHour);
		} catch (ParseException e) {
			log.error("数据pvByTime执行异常");
			e.printStackTrace();
		}
		log.info("===============数据pvByTime执行完成==============" + dataMap);
		return dataMap;
	}
}
