package com.ericho.coupleShare.mobile.inputmodel;

import com.google.gson.annotations.SerializedName;

public class ViewLocationInputModel extends BaseAuthModel {
	@SerializedName("timeOption")
	private TimeOption timeOption;
	@SerializedName("includeOption")
	private IncludeOption includeOption;
}
