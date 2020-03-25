package com.ericho.coupleShare.mobile.inputmodel;

import com.google.gson.annotations.SerializedName;

public class RelationActionInputModel extends BaseAuthModel {
	@SerializedName("action")
	private Action action;
	@SerializedName("username")
	private String username;

	enum Action{
	    ADD,
	    APPROVE,
	    DELETE
	}
}
