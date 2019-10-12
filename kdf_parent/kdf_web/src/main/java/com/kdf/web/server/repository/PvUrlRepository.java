package com.kdf.web.server.repository;

import com.kdf.web.server.bean.PvUrl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PvUrlRepository extends JpaRepository<PvUrl, Long> {

}
