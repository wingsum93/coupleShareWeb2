package com.ericho.coupleShare.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ericho.coupleShare.dao.StatusNoticeDao;
import com.ericho.coupleShare.model.StatusNotice;
import com.ericho.coupleShare.util.ArrayUtil;

@Service()
public class StatusNoticeServiceImpl implements StatusNoticeService{

	@Autowired
	private StatusNoticeDao dao;
	
	@Autowired
	private PhotoService photoService;

	@Override
	public List<StatusNotice> getStatusList(String username) {
		List<StatusNotice> list = dao.findByLatestUsername(Collections.singletonList(username));
		
		if(ArrayUtil.isNullOrEmpty(list)){
			return Collections.emptyList();
		}else{
		
			return Collections.singletonList(list.get(0));
		}
		
	}
	
	@Override
	public List<StatusNotice> getStatusList(List<String> usernames) {
		List<StatusNotice> list = dao.findByLatestUsername(usernames);
		
		if(ArrayUtil.isNullOrEmpty(list)){
			return Collections.emptyList();
		}else{
		
			return Collections.singletonList(list.get(0));
		}
		
	}

	@Override
	public StatusNotice save(StatusNotice statusNotice) {
		
		return dao.save(statusNotice);
	}
	
	
}
