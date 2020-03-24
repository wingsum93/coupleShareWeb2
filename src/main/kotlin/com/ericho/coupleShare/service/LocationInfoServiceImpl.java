package com.ericho.coupleShare.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ericho.coupleShare.dao.LocationInfoDao;
import com.ericho.coupleShare.model.LocationInfo;
import com.ericho.coupleShare.util.ArrayUtil;

@Service
public class LocationInfoServiceImpl implements LocationInfoService{

	 @Autowired
	 private LocationInfoDao dao;
	 
	 
	 
	 @Override
	 public LocationInfo findById(Long id){
		 return this.dao.findOne(id);
	 }



	@Override
	public List<LocationInfo> findByUsername(List<String> usernames) {
		List<LocationInfo> res = dao.findByUsername(usernames);
		res = ArrayUtil.parse(res);
		return res;
	}



	@Override
	public void save(List<LocationInfo> locationInfos) {
		dao.save(locationInfos);
		
	}
	 
	 
}
