package com.ericho.coupleShare.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ericho.coupleShare.dao.RelationDao;
import com.ericho.coupleShare.model.Relation;
import com.ericho.coupleShare.util.ArrayUtil;

@Service()
public class RelationServiceImpl implements RelationService {

	@Autowired
	private RelationDao dao;
	
	@Override
	public List<Relation> findByOriginName(String originName) {
		List<Relation> res = dao.findByOriginName(originName);
		
		return ArrayUtil.parse(res);
	}

}
