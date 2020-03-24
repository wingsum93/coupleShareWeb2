package com.ericho.coupleShare.mobile.model;

import java.util.Date;

import com.google.gson.annotations.SerializedName;

public class PhotoBo {
	@SerializedName("id")
	private Integer id ;
	@SerializedName("suffixUrl")
	private String  suffixUrl ;
	@SerializedName("photoName")
	private String  photoName ;
	@SerializedName("username")
	private String  username ;
	@SerializedName("uploadDate")
	private Date uploadDate;
	@SerializedName("uuid")
	private String  uuid ;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getSuffixUrl() {
		return suffixUrl;
	}
	public void setSuffixUrl(String suffixUrl) {
		this.suffixUrl = suffixUrl;
	}
	public String getPhotoName() {
		return photoName;
	}
	public void setPhotoName(String photoName) {
		this.photoName = photoName;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Date getUploadDate() {
		return uploadDate;
	}
	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
}
