package com.ericho.coupleShare.dao;

import com.ericho.coupleShare.model.LocationInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LocationInfoDao extends JpaRepository<LocationInfo, Long> {
	@Query("from locationInfo where username IN ?1")
	List<LocationInfo> findByUsername(List<String> usernames);
}
