package com.ericho.coupleShare.service;

import java.util.List;

import com.ericho.coupleShare.model.StatusNotice;

public interface StatusNoticeService {

	
	List<StatusNotice> getStatusList(String username);
	

	
	StatusNotice save(StatusNotice statusNotice);



	List<StatusNotice> getStatusList(List<String> usernames);
}
