package com.kdf.web.server.service;

import java.util.List;

import com.kdf.web.server.bean.PvAll;

public interface PvAllService {

	List<PvAll> getPvAllList(String appid, String startTime, String endTime);

}
