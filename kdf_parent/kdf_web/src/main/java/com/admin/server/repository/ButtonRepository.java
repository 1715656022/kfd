package com.admin.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.admin.server.bean.PbButton;
@Repository
public interface ButtonRepository extends JpaRepository<PbButton, Integer> {

	 Page<PbButton> findAllByDelFlag(int i,Pageable pageable);

	
	
}
