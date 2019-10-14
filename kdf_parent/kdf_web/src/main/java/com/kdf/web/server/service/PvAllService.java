package com.kdf.web.server.service;

import java.util.List;
import java.util.Map;

public interface PvAllService {

	List<Map<String, Object>> findAllPvMap(String appid);

}
