package com.ericho.coupleShare.util;

import java.util.Collections;
import java.util.List;

public class RelationUtil {
	public static List<String> getCanViewUserList(Object xxxService,String username){
		return Collections.singletonList(username);
	}
}
