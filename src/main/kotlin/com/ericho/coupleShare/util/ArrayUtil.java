package com.ericho.coupleShare.util;

import java.util.ArrayList;
import java.util.List;

public class ArrayUtil {
	public static <T>List<T> parse(List<T> list){
		if(list==null)
			return new ArrayList<>();
		return list;
	}
	
	public static boolean isNullOrEmpty(List<?> list){
		
		return list==null || list.isEmpty();
	}
}
