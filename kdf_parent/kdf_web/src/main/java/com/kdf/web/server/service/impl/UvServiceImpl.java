package com.kdf.web.server.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kdf.etl.repository.UvTimeDistributionRepository;
import com.kdf.web.server.service.UvService;

@Service
public class UvServiceImpl implements UvService {

	@Autowired
	private UvTimeDistributionRepository uvTimeDistributionRepository;

	@Override
	public Long countByRequestTimeAndAppid(String appid) {
		return uvTimeDistributionRepository.countByRequestTimeAndAppid(appid);
	}

	@Override
	public Long avgByRequestTimeAndAppid(String appid) {
		return uvTimeDistributionRepository.avgByRequestTimeAndAppid(appid);
	}

}
