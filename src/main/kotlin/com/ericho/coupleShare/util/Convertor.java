package com.ericho.coupleShare.util;

import java.util.Date;

import com.ericho.coupleShare.mobile.inputmodel.UploadLocationInputModel;
import com.ericho.coupleShare.mobile.model.PhotoBo;
import com.ericho.coupleShare.mobile.model.StatusBo;
import com.ericho.coupleShare.model.LocationInfo;
import com.ericho.coupleShare.model.Photo;
import com.ericho.coupleShare.model.StatusNotice;

public class Convertor {

	



	public static LocationInfo locConvert(Date collectDate,UploadLocationInputModel.Location loc){
		LocationInfo info = new LocationInfo();
		info.setAttitude(0d);
		info.setLongitude(loc.getLongitude());
		info.setLatitude(loc.getLatitude());
		info.setAccurate(10);
		info.setCollectDate(collectDate);
		info.setUsername(loc.getUsername());
		return info;
	}

	

	public static StatusBo statusConvert(StatusNotice statusNotice,String username) {
		StatusBo r = new StatusBo();
		r.setTitle(statusNotice.getTitle());
		r.setId(statusNotice.getId());
		r.setContent(statusNotice.getContent());
		Photo p = statusNotice.getPhoto();
		if(p !=null){
			String ff = p.getUuid();
			final String s= "api/viewStatusPhoto/" + ff + "?username="+username;
			r.setPhotoUrlSuffix(s);
		}
			
		return r;
	}
	public static PhotoBo photoConvert(Photo photo,String username) {
		PhotoBo r = new PhotoBo();
		r.setPhotoName(photo.getPhotoName());
		r.setId(photo.getId());
		r.setUsername(username);
		r.setUploadDate(photo.getCollectDate());
		r.setUuid(photo.getUuid());
		
		String ff = photo.getUuid();
		final String s= "api/viewPhoto/" + ff + "?username="+username;
		r.setSuffixUrl(s);
		
			
		return r;
	}
}
