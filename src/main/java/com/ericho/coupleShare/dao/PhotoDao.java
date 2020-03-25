package com.ericho.coupleShare.dao;

import com.ericho.coupleShare.model.Photo;
import com.ericho.coupleShare.model.PhotoType;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PhotoDao extends JpaRepository<Photo, Integer> {

	List<Photo> findByUsername(String username);

	List<Photo> findByUsernameIn(List<String> usernames, Sort sort);

	List<Photo> findByUsernameInOrderByIdDesc(List<String> usernames);

	List<Photo> findByUsernameAndPhotoTypeOrderByCollectDateDesc(List<String> usernames, PhotoType type);

	List<Photo> findByUsernameAndPhotoTypeOrderByIdDesc(List<String> usernames, PhotoType type);

	//	@Query("select p from photo p where username = ?1,uuid = ?2")
	List<Photo> findByUsernameAndUuid(String username, String uuid);

	List<Photo> findByUuid(String uuid);

	List<Photo> findByUuidAndPhotoType(String uuid, PhotoType type);
}
