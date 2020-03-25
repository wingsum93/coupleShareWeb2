package com.ericho.coupleShare.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Entity(name = "locationInfo")
public class LocationInfo {
	@Id
    @GeneratedValue()
    private int id;
	@Column(name = "username")
    private String username;
	@Column(name = "uploadBy")
    private String uploadBy;
	
    @Column(name = "attitude")
    private Double attitude;
    @Column(name = "longitude")
    private Double longitude;
    @Column(name = "latitude")
    private Double latitude;
    
    @Column(name = "accurate")
    private Integer accurate;
    @Column(name = "collectDate")
    private Date collectDate;
    
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Double getAttitude() {
		return attitude;
	}
	public void setAttitude(Double attitude) {
		this.attitude = attitude;
	}
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	public Integer getAccurate() {
		return accurate;
	}
	public void setAccurate(Integer accurate) {
		this.accurate = accurate;
	}
	public Date getCollectDate() {
		return collectDate;
	}
	public void setCollectDate(Date collectDate) {
		this.collectDate = collectDate;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUploadBy() {
		return uploadBy;
	}
	public void setUploadBy(String uploadBy) {
		this.uploadBy = uploadBy;
	}
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
    
    
}
