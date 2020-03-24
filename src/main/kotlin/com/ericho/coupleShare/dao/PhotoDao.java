package com.ericho.coupleShare.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ericho.coupleShare.model.Photo;
import com.ericho.coupleShare.model.PhotoType;

public interface PhotoDao extends JpaRepository<Photo,Integer>{
	
	List<Photo> findByUsername(String username);
	List<Photo> findByUsernameOrderByCollectDateDesc(List<String> usernames);
	List<Photo> findByUsernameOrderByIdDesc(List<String> usernames);
	List<Photo> findByUsernameAndPhotoTypeOrderByCollectDateDesc(List<String> usernames, PhotoType type);
	List<Photo> findByUsernameAndPhotoTypeOrderByIdDesc(List<String> usernames, PhotoType type);
//	@Query("select p from photo p where username = ?1,uuid = ?2")
	List<Photo> findByUsernameAndUuid(String username, String uuid);
	List<Photo> findByUuid(String uuid);
	List<Photo> findByUuidAndPhotoType(String uuid, PhotoType type);
}
