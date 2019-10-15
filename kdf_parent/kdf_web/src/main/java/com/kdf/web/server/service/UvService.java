package com.kdf.web.server.service;

public interface UvService {

	Long countByRequestTimeAndAppid(String appid);

	Long avgByRequestTimeAndAppid(String appid);

}
