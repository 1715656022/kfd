package com.kdf.etl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kdf.etl.bean.UvBrowser;

@Repository
public interface UvBrowserRepository extends JpaRepository<UvBrowser, Long> {

}
