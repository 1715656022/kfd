package com.kdf.etl.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.kdf.etl.bean.UvTimeDistribution;

@Repository
public interface UvTimeDistributionRepository extends JpaRepository<UvTimeDistribution, Long> {

	UvTimeDistribution findByRequestTime(Date strToDate);

	List<UvTimeDistribution> findByRequestTimeBetween(Date date, Date date2);

	@Query(value = "select ifnull(sum(uv_count), 0) from kfd_uv_time_distribution where 1=1 and if(?1 != '', appid = ?1 , 1=1)", nativeQuery = true)
	Long countByRequestTimeAndAppid(String appid);

	@Query(value = "select ifnull(floor(avg(uv_count)), 0) from kfd_uv_time_distribution where 1=1 and if(?1 != '', appid = ?1 , 1=1)", nativeQuery = true)
	Long avgByRequestTimeAndAppid(String appid);

}
