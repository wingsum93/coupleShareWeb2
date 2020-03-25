package com.ericho.coupleShare.service;

import com.ericho.coupleShare.model.Relation;

import java.util.List;

public interface RelationService {
    List<Relation> findByOriginName(String originName);
}
