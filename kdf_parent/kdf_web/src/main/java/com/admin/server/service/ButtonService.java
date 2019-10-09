package com.admin.server.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.admin.server.bean.PbButton;

public interface ButtonService {

	Page<PbButton> findByDelFlag(int i,Integer page, Integer limit);

	void save(PbButton bean);

	void delById(Integer buttonId, Integer userId);

	PbButton updataById(PbButton bean, Integer userId);

	List<PbButton> getButtons(Integer userId, Integer menuId);

}
