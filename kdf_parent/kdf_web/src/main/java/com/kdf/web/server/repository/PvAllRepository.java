package com.kdf.web.server.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kdf.web.server.bean.PvAll;

@Repository
public interface PvAllRepository extends JpaRepository<PvAll, Integer> {

	List<PvAll> findAllByAppidAndRequestTimeBetween(String appid, String startTime, String endTime);

}
