package com.ericho.coupleShare.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ericho.coupleShare.model.Relation;

public interface RelationDao extends JpaRepository<Relation,Integer>{

	List<Relation>  findByOriginName(String originName);
}
