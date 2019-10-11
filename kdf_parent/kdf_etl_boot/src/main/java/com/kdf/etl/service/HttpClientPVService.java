package com.kdf.etl.service;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kdf.etl.bean.PvHttpClient;
import com.kdf.etl.repository.HttpClientPVRepository;

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
public class HttpClientPVService {
	
	private Long size = 0l;
	private Long one = 1l;

	@Autowired
	private HttpClientPVRepository httpClientPVRepository;

	/**
	 *   用户pv总统计
	 * @param pvHttpClient
	 */
	public void save(PvHttpClient pvHttpClient) {
		log.info("===============数据pvHttpClient执行开始==============\"");
		PvHttpClient bean = httpClientPVRepository.findByMethod(pvHttpClient.getMethod());
		if (Objects.nonNull(bean)) {
			pvHttpClient.setPvCount(bean.getPvCount()+one);
			httpClientPVRepository.save(bean);
		} else {
			pvHttpClient.setPvCount(size);
			httpClientPVRepository.save(pvHttpClient);
		}
		
		log.info("===============数据pvHttpClient执行完成==============" + pvHttpClient);
	}

}
