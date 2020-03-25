package com.ericho.coupleShare.service;

import com.ericho.coupleShare.exception.JException;
import com.ericho.coupleShare.mobile.inputmodel.UploadLocationInputModel;
import com.ericho.coupleShare.mobile.inputmodel.ViewLocationInputModel;
import com.ericho.coupleShare.mobile.model.PhotoBo;
import com.ericho.coupleShare.mobile.model.StatusBo;
import com.ericho.coupleShare.model.LocationInfo;
import com.ericho.coupleShare.model.Relation;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ApiService {
	List<PhotoBo> findCanViewPhotoList(String username);

	List<Relation> relation_view(String username);

	List<LocationInfo> location_get_list(String username, ViewLocationInputModel m);

	void location_upload(String uploadBy, UploadLocationInputModel m) throws JException;
	
	void status_create(String uploadBy, MultipartFile file, String title, String content, String photoFolderPath) throws JException, IOException;

	List<StatusBo> status_view(String username) throws JException;

	void photo_create(String uploadBy, MultipartFile file, String photoFolderPath) throws JException, IOException;
}
