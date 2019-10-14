package com.kdf.web.server.controller.login;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.kdf.web.server.base.BaseResponse;
import com.kdf.web.server.base.ResultCode;
import com.kdf.web.server.service.UvService;

@RestController
@RequestMapping("home")
public class HomeController {

	@Autowired
	private UvService uvService;

	@GetMapping("")
	public ModelAndView home() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("html/home");
		return modelAndView;
	}

	@GetMapping("countUv")
	public ResponseEntity<BaseResponse<Map<String, Map<String, String>>>> countUv(String appid) {
		ResultCode ok = ResultCode.OK;
		BaseResponse<Map<String, Map<String, String>>> br = new BaseResponse<Map<String, Map<String, String>>>(
				ok.getCode(), ok.getMsg());
		Map<String, Map<String, String>> returnMap = new HashMap<String, Map<String, String>>(2);

		Long uvCount = uvService.countByRequestTimeAndAppid(appid);
		Long uvAvg = uvService.avgByRequestTimeAndAppid(appid);

		Map<String, String> uvMap = new HashMap<String, String>(2);
		uvMap.put("uvAvg", String.valueOf(uvAvg));
		uvMap.put("uvCount", String.valueOf(uvCount));
		returnMap.put("uv", uvMap);

		br.setData(returnMap);
		return br.sendResponse();
	}

}
