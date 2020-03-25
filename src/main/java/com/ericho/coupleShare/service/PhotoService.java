package com.ericho.coupleShare.service;

import com.ericho.coupleShare.model.Photo;
import com.ericho.coupleShare.model.PhotoType;

import java.io.File;
import java.util.Date;
import java.util.List;

public interface PhotoService {

	List<Photo> getAllPhoto(String username);

	List<Photo> findByUsernames(List<String> usernames);
	
	Photo findById(Integer id);

	/**
	 * add the new photo to the records in table photo
	 * @param date
	 * @param photoFile the actual file store in this permanent folder
	 */
	Photo addNewPhoto(Date date, File photoFile, String username);
	Photo addNewStatusPhoto(Date date, File photoFile, String username);
	
	Photo findByUsernameAndUUid(String username, String uuid);
	
	Photo getStatusPhotoByUUID(String username, String uuid);

	List<Photo> findByUsernamesAndPhotoType(List<String> usernames, PhotoType type);

	boolean deletePhotoByUUID(String username, String uuid, File photoDir);

	boolean deleteAllPhoto(String username, File folder);
}
