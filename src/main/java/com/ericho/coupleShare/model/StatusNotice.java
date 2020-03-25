package com.ericho.coupleShare.model;

import javax.persistence.*;
import java.util.Date;

/**
 * a class for storing the status of each user. each user can create n of status notice, but user can only view the latest once for individual user.
 * @author steve_000
 *
 */
@Entity(name="statusNotice")
public class StatusNotice {
	
	@Id
	@GeneratedValue()
	private Integer id;
	@Column(name="username")
	private String username;
	@Column(name="date")
	private Date date;
	@Column(name="title")
	private String title;
	@Column(name="content",length = 1000)
	private String content;
	@Column(name="photo_id", updatable=false)
	private Integer photoId;
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="photo_id",insertable=false, updatable=false)
	private Photo photo;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
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

	public Integer getPhotoId() {
		return photoId;
	}

	public void setPhotoId(Integer photoId) {
		this.photoId = photoId;
	}

	public Photo getPhoto() {
		return photo;
	}

	public void setPhoto(Photo photo) {
		this.photo = photo;
		this.setPhotoId(photo.getId());
	}

	
	
}
