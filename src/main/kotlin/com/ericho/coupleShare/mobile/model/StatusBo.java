package com.ericho.coupleShare.mobile.model;

import com.google.gson.annotations.SerializedName;

public class StatusBo {

	@SerializedName("id")
	private Integer id;
	@SerializedName("photoUrlSuffix")
	private String photoUrlSuffix;
	@SerializedName("title")
	private String title;
	@SerializedName("content")
	private String content;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getPhotoUrlSuffix() {
		return photoUrlSuffix;
	}
	public void setPhotoUrlSuffix(String photoUrlSuffix) {
		this.photoUrlSuffix = photoUrlSuffix;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	
}
