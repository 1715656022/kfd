package com.kdf.etl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kdf.etl.bean.UvClientType;

@Repository
public interface UvClientTypeRepository extends JpaRepository<UvClientType, Long> {

}
