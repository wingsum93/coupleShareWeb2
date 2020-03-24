package com.ericho.coupleShare.service;

import java.util.List;

import com.ericho.coupleShare.model.Relation;

public interface RelationService {
	List<Relation> findByOriginName(String originName);
}
