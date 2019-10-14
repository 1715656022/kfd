package com.kdf.web.server.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.kdf.web.server.bean.PvAll;

public interface PvAllService {

	List<PvAll> getPvAllList(String appid, Date startTime, Date endTime);

	List<Map<String, Object>> findAllPvMap(String appid);

}
