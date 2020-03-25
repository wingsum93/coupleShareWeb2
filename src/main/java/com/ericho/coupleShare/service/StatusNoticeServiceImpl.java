package com.ericho.coupleShare.service;

import com.ericho.coupleShare.dao.StatusNoticeDao;
import com.ericho.coupleShare.model.StatusNotice;
import com.ericho.coupleShare.util.ArrayUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service()
public class StatusNoticeServiceImpl implements StatusNoticeService {

	public static final Logger logger = LoggerFactory.getLogger(StatusNoticeServiceImpl.class);

	private final StatusNoticeDao dao;

	private final PhotoService photoService;

	public StatusNoticeServiceImpl(StatusNoticeDao dao, PhotoService photoService) {
		this.dao = dao;
		this.photoService = photoService;
	}

	@Override
	public List<StatusNotice> getStatusList(String username) {
		List<StatusNotice> list = dao.findByLatestUsername(Collections.singletonList(username));

		if (ArrayUtil.isNullOrEmpty(list)) {
			return Collections.emptyList();
		} else {

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
