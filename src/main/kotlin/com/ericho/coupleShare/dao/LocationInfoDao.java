package com.ericho.coupleShare.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ericho.coupleShare.model.LocationInfo;

public interface LocationInfoDao extends JpaRepository<LocationInfo, Long>{

	List<LocationInfo> findByUsername(List<String> usernames);
}
