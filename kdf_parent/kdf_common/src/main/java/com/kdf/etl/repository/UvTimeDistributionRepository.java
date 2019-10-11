package com.kdf.etl.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kdf.etl.bean.UvTimeDistribution;

@Repository
public interface UvTimeDistributionRepository extends JpaRepository<UvTimeDistribution, Long> {

	UvTimeDistribution findByRequestTime(Date strToDate);

	List<UvTimeDistribution> findByRequestTimeBetween(Date date, Date date2);

}
