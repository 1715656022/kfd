package com.kdf.web.server.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kdf.etl.repository.PvAllRepository;
import com.kdf.web.server.service.PvAllService;
@Service
public class PvAllServiceImpl implements PvAllService {

	@Autowired
	private PvAllRepository pvAllRepository;

	@Override
	public List<Map<String, Object>> findAllPvMap(String appid) {
		return pvAllRepository.findAllPvMap(appid);
	}
	
	
}
