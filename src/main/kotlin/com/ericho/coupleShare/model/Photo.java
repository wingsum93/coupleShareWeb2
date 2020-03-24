package com.ericho.coupleShare.model;

import java.io.File;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name = "photo")
public class Photo {
	@Id
    @GeneratedValue()
    private Integer id;
    @Column(name = "username")
    private String username;
    @Column(name = "photoName")
    private String photoName;
   
    @Column(name = "collectDate")
    private Date collectDate;
    @Column(name = "uuid")
    private String uuid;
  
    @Enumerated(EnumType.STRING)
    private PhotoType photoType;
    
    
    
    public Photo(){
    	photoType = PhotoType.Normal;
    	uuid = UUID.randomUUID().toString();
    	collectDate = new Date();
    }
    
    public static Photo fromNormal(Date date, File photoFile,String username){
    	UUID uuid = UUID.randomUUID();
		Photo p = new Photo();
		
		p.setUuid(uuid.toString());
		p.setCollectDate(date);
		p.setPhotoName(photoFile.getName());
		p.setUsername(username);
		
		p.setPhotoType(PhotoType.Normal);
		return p;
    } 
    public static Photo fromStatusPhoto(Date date, File photoFile, String username){
    	UUID uuid = UUID.randomUUID();
		Photo p = new Photo();
		
		p.setUuid(uuid.toString());
		p.setCollectDate(date);
		p.setPhotoName(photoFile.getName());
		p.setUsername(username);
		p.setPhotoType(PhotoType.StatusPhoto);
		return p;
    }
    
    
    
	public PhotoType getPhotoType() {
		return photoType;
	}




	public void setPhotoType(PhotoType photoType) {
		this.photoType = photoType;
	}




	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	
	public Date getCollectDate() {
		return collectDate;
	}
	public void setCollectDate(Date collectDate) {
		this.collectDate = collectDate;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}




	public String getPhotoName() {
		return photoName;
	}




	public void setPhotoName(String photoName) {
		this.photoName = photoName;
	} 
    
    
}
