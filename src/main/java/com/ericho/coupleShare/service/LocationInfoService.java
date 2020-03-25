package com.ericho.coupleShare.service;

import com.ericho.coupleShare.model.LocationInfo;

import java.util.List;

public interface LocationInfoService {
	LocationInfo findById(Long id);
	
	List<LocationInfo> findByUsername(List<String> usernames);

	void save(List<LocationInfo> list);
}
