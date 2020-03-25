package com.ericho.coupleShare.service;

import com.ericho.coupleShare.exception.JException;
import com.ericho.coupleShare.mobile.inputmodel.UploadLocationInputModel;
import com.ericho.coupleShare.mobile.inputmodel.ViewLocationInputModel;
import com.ericho.coupleShare.mobile.model.PhotoBo;
import com.ericho.coupleShare.mobile.model.StatusBo;
import com.ericho.coupleShare.model.*;
import com.ericho.coupleShare.util.ArrayUtil;
import com.ericho.coupleShare.util.Convertor;
import com.ericho.coupleShare.util.RelationUtil;
import com.google.gson.Gson;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Service()
public class ApiServiceImpl implements ApiService{
    private Log log = LogFactory.getLog(ApiServiceImpl.class);
    @Autowired
    UserService userService;
    @Autowired
    private RelationService relationService;
	@Autowired
    private LocationInfoService locationInfoService;
    @Autowired
    private ProductService productService;
    
    @Autowired
    private PhotoService photoService;
    
    @Autowired
    private StatusNoticeService statusNoticeService;

	@Override
	public List<PhotoBo> findCanViewPhotoList(String username) {
		List<String> usernames = RelationUtil.getCanViewUserList(null, username);
    	
    	List<Photo> res = photoService.findByUsernamesAndPhotoType(usernames,PhotoType.Normal);
    	
    	res = ArrayUtil.parse(res);
    	List<PhotoBo> result = new ArrayList<>();
    	
    	for(Photo p:res){
    		PhotoBo pbo = Convertor.photoConvert(p, username);
    		result.add(pbo);
    	}
    	
		return result;
	}

	@Override
	public List<Relation> relation_view(String username) {
		List<Relation> res = relationService.findByOriginName(username);
		res = ArrayUtil.parse(res);
		return res;
	}

	@Override
	public List<LocationInfo> location_get_list(String username, ViewLocationInputModel m) {
		List<String> usernames = RelationUtil.getCanViewUserList(null, username);
		List<LocationInfo> res = locationInfoService.findByUsername(usernames);
		
		return res;
	}

	@Override
	public void location_upload(String uploadBy,UploadLocationInputModel m) throws JException {

		//check any loc record ?
		if(ArrayUtil.isNullOrEmpty(m.getLocations())) throw new JException("");
		List<LocationInfo> list = new ArrayList<>();
		Date now = new Date();
		for(UploadLocationInputModel.Location loc:m.getLocations()){
			LocationInfo lfo = Convertor.locConvert(now, loc);
			lfo.setUploadBy(uploadBy);
			list.add(lfo);
		}
		
		locationInfoService.save(list);
	}
	@Override
	public void photo_create(String uploadBy, MultipartFile file, String photoFolderPath) throws JException, IOException {
		Map<String,String> map = new HashMap<>();
		map.put("uploadBy", uploadBy);
		map.put("photoFolderPath", photoFolderPath);
		String str=new Gson().toJson(map);
		log.debug(str);
		
		File folder = new File(photoFolderPath);
		String ss = UUID.randomUUID().toString()+".jpg";
		File saveFile = new File(folder,ss);
		FileCopyUtils.copy(file.getBytes(), saveFile);
		
		
		
		Photo o = photoService.addNewPhoto(new Date(), saveFile, uploadBy);
		
	}
	@Override
	public void status_create(String uploadBy, MultipartFile file, String title, String content, String photoFolderPath)
			throws JException, IOException {
		Map<String,String> map = new HashMap<>();
		map.put("uploadBy", uploadBy);
		map.put("title", title);
		map.put("content", content);
		map.put("photoFolderPath", photoFolderPath);
		String str=new Gson().toJson(map);
		log.debug(str);
		
		File folder = new File(photoFolderPath);
		String ss = UUID.randomUUID().toString()+".jpg";
		File saveFile = new File(folder,ss);
		FileCopyUtils.copy(file.getBytes(), saveFile);
		
		StatusNotice one = new StatusNotice();
		one.setTitle(title);
		one.setDate(new Date());
		one.setContent(content);
		one.setUsername(uploadBy);
		
		Photo o = photoService.addNewStatusPhoto(new Date(), saveFile, uploadBy);
		one.setPhoto(o);
		
		this.statusNoticeService.save(one);
		
	}

	@Override
	public List<StatusBo> status_view(String username) throws JException {
		//get corresponding friend
		List<String> users = RelationUtil.getCanViewUserList(relationService, username);
		
		List<StatusBo> res = new ArrayList<>();
		
		List<StatusNotice> sns = this.statusNoticeService.getStatusList(users);
		if(ArrayUtil.isNullOrEmpty(sns)) throw new JException("");
		
		for(StatusNotice status:sns){
			StatusBo bo = Convertor.statusConvert(status,username);
			
			res.add(bo);
		}
		
		return res;
	}
	
	
}
