package com.kdf.etl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kdf.etl.bean.UvUserAreaDistribution;

@Repository
public interface UvUserAreaDistributionRepository extends JpaRepository<UvUserAreaDistribution, Long> {

}
