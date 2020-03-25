package com.ericho.coupleShare.mobile.inputmodel;

public class CreateDeleteRelationInputModel  extends BaseAuthModel{
	private String originUserName;
	private String targetUserName;
	
	
	public String getOriginUserName() {
		return originUserName;
	}
	public void setOriginUserName(String originUserName) {
		this.originUserName = originUserName;
	}
	public String getTargetUserName() {
		return targetUserName;
	}
	public void setTargetUserName(String targetUserName) {
		this.targetUserName = targetUserName;
	}
	
	
}
