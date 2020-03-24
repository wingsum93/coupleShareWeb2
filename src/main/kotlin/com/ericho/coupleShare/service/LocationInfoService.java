package com.ericho.coupleShare.service;

import java.util.List;

import com.ericho.coupleShare.model.LocationInfo;

public interface LocationInfoService {
	LocationInfo findById(Long id);
	
	List<LocationInfo> findByUsername(List<String> usernames);

	void save(List<LocationInfo> list);
}
