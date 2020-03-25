package com.ericho.coupleShare.dao;

import com.ericho.coupleShare.model.Relation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RelationDao extends JpaRepository<Relation,Integer>{

	List<Relation>  findByOriginName(String originName);
}
