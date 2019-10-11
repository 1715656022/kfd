package com.kdf.etl.contoller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.kdf.etl.bean.UvTimeDistribution;
import com.kdf.etl.service.UvTimeDistributionService;

@RestController
public class MengppControllerTest {

	@Autowired
	private UvTimeDistributionService uvTimeDistributionService;

	@GetMapping("mengppTest")
	public void test() {
		String yearMonthDayHour = "2019_10_10_10";
		String endYearMonthDayHour = "2019_10_11_13";

		// 保存
		uvTimeDistributionService.saveUvTimeDistribution(yearMonthDayHour);

		// 查询
		UvTimeDistribution uvTimeDistribution = uvTimeDistributionService.getUvTimeDistribution(yearMonthDayHour);
		System.out.println(JSON.toJSONString(uvTimeDistribution));

		// 区段查询
		List<UvTimeDistribution> list = uvTimeDistributionService.getAreaUvTimeDistribution(yearMonthDayHour, endYearMonthDayHour);
		System.out.println(JSON.toJSONString(list));
	}

}
