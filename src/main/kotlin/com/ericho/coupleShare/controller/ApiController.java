package com.ericho.coupleShare.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.ericho.coupleShare.mobile.BaseResponse;
import com.ericho.coupleShare.mobile.BaseSingleResponse;
import com.ericho.coupleShare.mobile.inputmodel.CreateDeleteRelationInputModel;
import com.ericho.coupleShare.mobile.inputmodel.UploadLocationInputModel;
import com.ericho.coupleShare.mobile.inputmodel.ViewLocationInputModel;
import com.ericho.coupleShare.mobile.model.PhotoBo;
import com.ericho.coupleShare.mobile.model.StatusBo;
import com.ericho.coupleShare.model.LocationInfo;
import com.ericho.coupleShare.model.Photo;
import com.ericho.coupleShare.service.ApiService;
import com.ericho.coupleShare.service.PhotoService;
import com.ericho.coupleShare.service.ProductService;
import com.ericho.coupleShare.service.UserService;
import com.ericho.coupleShare.util.SecurityUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

@RestController
@RequestMapping("/api")
public class ApiController {
    Log log = LogFactory.getLog(ApiController.class);
    private Gson gson = new GsonBuilder().setDateFormat(DateFormat.LONG)
    		.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
                            public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                                return new Date(json.getAsJsonPrimitive().getAsLong());
                            }
                        } ).create();
    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;
    
    @Autowired
    private PhotoService photoService;
    
    @Autowired
    private ApiService apiService;
    //              for properties
    @Value("${coupleshare.folder}")
    private String filePath;
    
    
    
    
    
    //-----------------------------------------------------------------------------


    @RequestMapping("/register")
    @ResponseBody
    public BaseSingleResponse<String> registerUser(@RequestParam() String username,
                                                   @RequestParam() String password) {
        BaseSingleResponse<String> response = new BaseSingleResponse<>();

        log.debug("username " + username + " password=" + password);
        try {
            userService.register(username, password);
            boolean authenticate = true;
            response.setStatus(authenticate);
            response.setExtra("extra");
            log.debug(gson.toJson(response));
            return response;
        } catch (Exception e) {
            response.setStatus(false);
            response.setErrorMessage(e.getMessage());
            log.warn(gson.toJson(response));
            return response;
        }
    }

    @PostMapping("/login")
    @ResponseBody
    public BaseSingleResponse<Void> checkLogin(
            @RequestParam() String username,
            @RequestParam() String password) {
        log.debug("login  username= " + username);

        BaseSingleResponse<Void> response = new BaseSingleResponse<>();
        try {

            boolean authenticate = userService.checkLogin(username, password);
            
            response.setStatus(authenticate);
            log.debug(gson.toJson(response));
            return response;
        } catch (Exception e) {
            response.setStatus(false);
            response.setErrorMessage(e.getMessage());
            log.warn(gson.toJson(response));
            return response;
        }
    }

    @RequestMapping("/changePassword")
    @ResponseBody
    public BaseSingleResponse<Void> changePassword(
    		@RequestHeader("token") String token,
   		 	@RequestHeader("username") String username,
   		 	@RequestBody String json
   		 	) {
    	BaseSingleResponse<Void> response = new BaseSingleResponse<>();
    	try{
    		UploadLocationInputModel m = gson.fromJson(json, UploadLocationInputModel.class);
        	//check auth
        	SecurityUtil.authenticate(userService, username, token);
            boolean authenticate = true;

            
            response.setStatus(authenticate);
            return response;
    	}catch(Exception e){
    		response.setStatus(false);
    		response.setErrorMessage(e.getMessage());
            return response;
    	}
    }

    @RequestMapping(path = "/health")
    @ResponseBody
    public BaseSingleResponse<String> health() {

        BaseSingleResponse<String> response = new BaseSingleResponse<>();
        try {
        
        	response.setStatus(true);
        	response.setExtra("");
        	return response;
        } catch (Exception e) {
            response.setStatus(false);
            response.setErrorMessage(e.getMessage());
            log.warn(gson.toJson(response));
            return response;
        }
    }

    @RequestMapping(path = "/uploadPhoto",method = RequestMethod.POST)
    @ResponseBody
    public BaseSingleResponse<String> uploadPhoto(
            @RequestHeader("token") String token,
            @RequestHeader(value="username") String username,
            @RequestParam("photo") MultipartFile multipartFile,
            @RequestParam(name="tags",required=false) String tags
            ) {

        BaseSingleResponse<String> response = new BaseSingleResponse<>();
        
        Map<String ,String> map = new HashMap<>();
        map.put("photoName", multipartFile.getName());
        map.put("contentType", multipartFile.getContentType());
        map.put("getOriginalFilename", multipartFile.getOriginalFilename());
        
        try {
        	//check auth
        	SecurityUtil.authenticate(userService, username, token);
        	log.debug(gson.toJson(map));
        	File folder = new File(filePath);
        	
        	if(!folder.exists()){
        		folder.mkdirs();
        	}
        	
        	
        	this.apiService.photo_create(username, multipartFile, filePath);
        	
            boolean authenticate = true;
            
            response.setStatus(authenticate);
            log.debug(gson.toJson(response));
            return response;
        } catch (Exception e) {
            response.setStatus(false);
            response.setErrorMessage(e.getMessage());
            log.warn(gson.toJson(response));
            return response;
        }
    }
   
    
    /**
     * get the list ob photo object can be view by that users
     * @param
     * @return
     */
    @RequestMapping(path = "/getPhotoList",method = RequestMethod.GET)
    @ResponseBody
    public BaseResponse<PhotoBo> getPhotoList(  
    		@RequestHeader(value="username") String username,
    		@RequestHeader(value="token") String token) {

    	BaseResponse<PhotoBo> response = new BaseResponse<>();
       
        try {
        	//check auth
        	SecurityUtil.authenticate(userService, username, token);
        	
        	List<PhotoBo> res = apiService.findCanViewPhotoList(username);
        	
            boolean authenticate = true;
            response.setExtra(res);

            
            response.setStatus(authenticate);
            log.debug(gson.toJson(response));
            return response;
        } catch (Exception e) {
            response.setStatus(false);
            response.setErrorMessage(e.getMessage());
            log.warn(gson.toJson(response));
            return response;
        }
    }
    @RequestMapping(path = "/viewPhoto/{uuid}",produces = MediaType.IMAGE_JPEG_VALUE,method = RequestMethod.GET)//get photo data
    @ResponseBody
    public byte[] viewPhoto(
            @PathVariable("uuid") String uuid,
           
            @RequestHeader(value="username") String username,
    		@RequestHeader(value="token") String token) {

        BaseSingleResponse<String> response = new BaseSingleResponse<>();
        try {
        
        	Photo p = 
        	photoService.findByUsernameAndUUid(username,  uuid);
        	
        	if(p==null) throw new IOException("photo Not Found");
        	
        	File folder = new File(filePath);
        	
        	if(!folder.exists()) folder.mkdirs();
        	
        	File f = new File(folder,p.getPhotoName());
        	
        	if(!folder.exists()) throw new FileNotFoundException();
        	FileInputStream in = null;
        	try {
    			in = new FileInputStream(f);
    		} catch (FileNotFoundException e) {
    			log.warn("warn",e);
    		}

    		try {
    			return IOUtils.toByteArray(in);
    		} finally {
    			IOUtils.closeQuietly(in);
    		}
        } catch (Exception e) {
            response.setStatus(false);
            response.setErrorMessage(e.getMessage());
            log.warn(gson.toJson(response));
            return null;
        }
    }
    @RequestMapping(path = "/photo/{uuid}",method=RequestMethod.DELETE)//get photo data
    @ResponseBody
    public BaseSingleResponse<Boolean> deletePhoto(
    		@RequestHeader(value="token") String token,
    		@RequestHeader("username") String username,
            @PathVariable("uuid") String uuid
            
            ) {

        BaseSingleResponse<Boolean> response = new BaseSingleResponse<>();
        try {
        	
        	File folder = new File(filePath);
        	
        	if(!folder.exists()) folder.mkdirs();
        	boolean del = 
                	photoService.deletePhotoByUUID(username, uuid,folder);
        	response.setStatus(true);
        	response.setExtra(del);
        	
        	return response;
        } catch (Exception e) {
            response.setStatus(false);
            response.setErrorMessage(e.getMessage());
            log.warn(gson.toJson(response));
            return response;
        }
    }
    @RequestMapping(path = "/allPhoto",method=RequestMethod.DELETE)//delete all normal  photo data
    @ResponseBody
    public BaseSingleResponse<Boolean> deletePhoto(
    		@RequestHeader("token") String token,
    		@RequestHeader("username") String username
           
            ) {

        BaseSingleResponse<Boolean> response = new BaseSingleResponse<>();
        try {
        	
        	File folder = new File(filePath);
        	
        	if(!folder.exists()) folder.mkdirs();
        	boolean del = 
                	photoService.deleteAllPhoto(username,folder);
        	response.setStatus(true);
        	response.setExtra(del);
        	
        	return response;
        } catch (Exception e) {
            response.setStatus(false);
            response.setErrorMessage(e.getMessage());
            log.warn(gson.toJson(response));
            return response;
        }
    }
    @RequestMapping(path = "/viewStatusPhoto/{uuid}",method=RequestMethod.GET,produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public byte[] viewStatusPhoto(
            @PathVariable("uuid") String uuid, 
            @RequestHeader("token") String token,
    		@RequestHeader("username") String username
            ) {

        BaseSingleResponse<String> response = new BaseSingleResponse<>();
        try {
        
        	Photo p = 
        	photoService.getStatusPhotoByUUID(username, uuid);
        	
        	if(p==null) throw new IOException("");
        	
        	File folder = new File(filePath);
        	
        	if(!folder.exists()) folder.mkdirs();
        	
        	File f = new File(folder,p.getPhotoName());
        	
        	if(!folder.exists()) throw new FileNotFoundException();
        	FileInputStream in = null;
        	try {
    			in = new FileInputStream(f);
    		} catch (FileNotFoundException e) {
    			log.warn("warn",e);
    		}

    		try {
    			return IOUtils.toByteArray(in);
    		} finally {
    			IOUtils.closeQuietly(in);
    		}
        } catch (Exception e) {
            response.setStatus(false);
            response.setErrorMessage(e.getMessage());
            log.warn(gson.toJson(response));
            return null;
        }
    }
    
    
    @RequestMapping(path = "/createRelation",method = RequestMethod.POST)
    @ResponseBody
    public BaseSingleResponse<String> createRelation(  
    		@RequestHeader("token") String token,
   		 	@RequestHeader("username") String username,
   		 	@RequestBody() String json ) {

    	BaseSingleResponse<String> response = new BaseSingleResponse<>();
       
        try {
        	CreateDeleteRelationInputModel m = gson.fromJson(json, CreateDeleteRelationInputModel.class);
        	
        	//check auth
        	SecurityUtil.authenticate(userService, username, token);
        	
            boolean authenticate = true;
            
            response.setStatus(authenticate);
            log.debug(gson.toJson(response));
            return response;
        } catch (Exception e) {
            response.setStatus(false);
            response.setErrorMessage(e.getMessage());
            log.warn(gson.toJson(response));
            return response;
        }
    }
    
    @RequestMapping(path = "/relation",method = RequestMethod.GET)
    @ResponseBody
    public BaseSingleResponse<String> viewRelation( 
    		@RequestHeader(value="username") String username,
    		@RequestHeader(value="token") String token ) {

    	BaseSingleResponse<String> response = new BaseSingleResponse<>();
       
        try {
        	
        	//check auth
        	SecurityUtil.authenticate(userService, username, token);
        	
        	apiService.relation_view(username);
        	
            boolean status = true;
            
            response.setStatus(status);
            log.debug(gson.toJson(response));
            return response;
        } catch (Exception e) {
            response.setStatus(false);
            response.setErrorMessage(e.getMessage());
            log.warn(gson.toJson(response));
            return response;
        }
    }
    
    @RequestMapping(path = "/relation",method = RequestMethod.DELETE)
    @ResponseBody
    public BaseSingleResponse<String> deleteRelation(  
    		@RequestHeader("token") String token,
   		 	@RequestHeader("username") String username,
   		 	@RequestBody() String json ) {

    	BaseSingleResponse<String> response = new BaseSingleResponse<>();
       
        try {
        	UploadLocationInputModel m = gson.fromJson(json, UploadLocationInputModel.class);
        	//check auth
        	SecurityUtil.authenticate(userService, username, token);
        	
        	
            boolean authenticate = true;
            
            response.setStatus(authenticate);
            log.debug(gson.toJson(response));
            return response;
        } catch (Exception e) {
            response.setStatus(false);
            response.setErrorMessage(e.getMessage());
            log.warn(gson.toJson(response));
            return response;
        }
    }
    
    @RequestMapping(path = "/status",method = RequestMethod.DELETE)
    @ResponseBody
    public BaseSingleResponse<String> deleteStatus(  
    		@RequestHeader("token") String token,
   		 	@RequestHeader("username") String username,
    		@RequestBody() String json ) {

    	BaseSingleResponse<String> response = new BaseSingleResponse<>();
       
        try {
        	UploadLocationInputModel m = gson.fromJson(json, UploadLocationInputModel.class);
        	//check auth
        	SecurityUtil.authenticate(userService, username, token);
        	
        	
            boolean authenticate = true;
            
            //todo
            
            
            response.setStatus(authenticate);
            log.debug(gson.toJson(response));
            return response;
        } catch (Exception e) {
            response.setStatus(false);
            response.setErrorMessage(e.getMessage());
            log.warn(gson.toJson(response));
            return response;
        }
    }
    @RequestMapping(path = "/status",method = RequestMethod.GET)
    @ResponseBody
    public BaseResponse<StatusBo> viewStatus(  
    		@RequestHeader("token") String token,
   		 	@RequestHeader("username") String username ) {

    	BaseResponse<StatusBo> response = new BaseResponse<>();
       
        try {
        	
        	//check auth
        	SecurityUtil.authenticate(userService, username, token);

        	List<StatusBo> list = this.apiService.status_view(username);
        	
        	//todo
            
            response.setStatus(true);
            response.setExtra(list);
            log.debug(gson.toJson(response));
            return response;
        } catch (Exception e) {
            response.setStatus(false);
            response.setErrorMessage(e.getMessage());
            log.warn(gson.toJson(response));
            return response;
        }
    }
    @RequestMapping(path = "/status",method = RequestMethod.POST)
    @ResponseBody
    public BaseSingleResponse<String> createStatus(  
    		@RequestHeader("token") String token,
   		 	@RequestHeader("username") String username,
   		 	@RequestParam(name="photo") MultipartFile file,
   		 	@RequestParam(name="title") String title,
   		 	@RequestParam(name="content") String content) {

    	BaseSingleResponse<String> response = new BaseSingleResponse<>();
       
        try {
        	
        	
        	//check auth
        	SecurityUtil.authenticate(userService, username, token);
        	
            log.debug("content= "+content);
            
            
            this.apiService.status_create(username, file, title, content, filePath);
            response.setStatus(true);
            return response;
        } catch (Exception e) {
            response.setStatus(false);
            response.setErrorMessage(e.getMessage());
            log.warn(gson.toJson(response));
            return response;
        }
    }
    
    @RequestMapping(path = "/getLocationList",method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse<LocationInfo> getLocationList(  
    		 @RequestHeader("token") String token,
    		 @RequestHeader("username") String username,
    		 @RequestBody() ViewLocationInputModel m ) {

    	BaseResponse<LocationInfo> response = new BaseResponse<>();
       
        try {
        	//check auth
        	SecurityUtil.authenticate(userService, username, token);
        	
        	List<LocationInfo> res = apiService.location_get_list(username,m);
        	
        	log.warn("getLocationList");
        	log.warn(gson.toJson(res));
            boolean status = true;
            
            response.setStatus(status);
            response.setExtra(res);
            return response;
        } catch (Exception e) {
            response.setStatus(false);
            response.setErrorMessage(e.getMessage());
            log.warn(gson.toJson(response));
            return response;
        }
    }
    
    @RequestMapping(path = "/uploadLocation",method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse<String> uploadLocation(  
    		@RequestHeader("token") String token,
   		 	@RequestHeader("username") String username,
   		 	@RequestBody() String json ) {

    	BaseResponse<String> response = new BaseResponse<>();
       
        try {
        	UploadLocationInputModel m = gson.fromJson(json, UploadLocationInputModel.class);
        	//check auth
        	SecurityUtil.authenticate(userService, username, token);
        	
        	apiService.location_upload(username,m);
            boolean status = true;
            
            response.setStatus(status);
            
            return response;
        } catch (Exception e) {
            response.setStatus(false);
            response.setErrorMessage(e.getMessage());
            log.warn(gson.toJson(response));
            return response;
        }
    }
    @RequestMapping(path = "/location/{id}",method = RequestMethod.DELETE)
    @ResponseBody
    public BaseResponse<LocationInfo> getLocationList(  
    		 @RequestHeader("token") String token,
    		 @RequestHeader("username") String username,
    		 @PathVariable("id") String locationId ) {

    	BaseResponse<LocationInfo> response = new BaseResponse<>();
       
        try {
        	//check auth
        	SecurityUtil.authenticate(userService, username, token);
        	
        	
        	
            boolean status = true;
            
            response.setStatus(status);
            
            return response;
        } catch (Exception e) {
            response.setStatus(false);
            response.setErrorMessage(e.getMessage());
            log.warn(gson.toJson(response));
            return response;
        }
    }
}
