package com.kdf.web.server.controller.pv;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.druid.util.StringUtils;
import com.kdf.etl.bean.PvHttpClient;
import com.kdf.web.server.service.HttpClientService;

@RestController
@RequestMapping("httpClient")
public class HttpClientController {

	@Autowired
	private HttpClientService httpClientService;
	
	/**
	 * toMenu 请求pv展示页
	 * 
	 * @return
	 */
	@GetMapping("")
	public ModelAndView toMenu(HttpSession session) {
		return new ModelAndView("html/pv/httpClient");
	}

	@PostMapping("list")
	public List<PvHttpClient> list(HttpSession session,String startTime,String endTime) {
		String appid = "pbkj_ly";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		List<PvHttpClient> list = null;
		try {
			list = new ArrayList<>();
			if (!StringUtils.isEmpty(startTime) && !StringUtils.isEmpty(endTime)) {
				Date startTimeDate = sdf.parse(startTime);
				Date endTimeDate = sdf.parse(endTime);
				list = httpClientService.findByAppidAndRequestTimeBetween(appid,startTimeDate,endTimeDate);
			} else {
				list = httpClientService.finAll();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
}
