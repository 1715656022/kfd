package com.kdf.etl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kdf.etl.bean.PvAll;

@Repository
public interface PvAllRepository extends JpaRepository<PvAll, Long> {

}
