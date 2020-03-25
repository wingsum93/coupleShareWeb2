package com.ericho.coupleShare.service;

import com.ericho.coupleShare.model.StatusNotice;

import java.util.List;

public interface StatusNoticeService {


    List<StatusNotice> getStatusList(String username);


    StatusNotice save(StatusNotice statusNotice);



	List<StatusNotice> getStatusList(List<String> usernames);
}
