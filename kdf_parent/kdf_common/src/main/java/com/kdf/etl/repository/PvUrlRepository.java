package com.kdf.etl.repository;

import com.kdf.etl.bean.PvUrl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PvUrlRepository extends JpaRepository<PvUrl, Long> {
}
