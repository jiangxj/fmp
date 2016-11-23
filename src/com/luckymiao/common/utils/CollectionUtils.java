package com.luckymiao.common.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class CollectionUtils {
	public static Object[] ListToArray(List list){
		if(list == null || list.isEmpty()){
			return new Object[0];
		}
		Object[] objs = new Object[list.size()];
		int i = 0;
		for (Object object : list) {
			objs[i] = object;
			i++;
		}
		return objs;
	}
	public static Map toLowerCaseKey(Map<String, Object> paramMap){
		Map resultMap = new HashMap();
		if(paramMap != null){
			Iterator<String> it = paramMap.keySet().iterator();
			while(it.hasNext()){
				String key = it.next();
				resultMap.put(key.toLowerCase(), paramMap.get(key));
			}
		}
		return resultMap;
	}
	
}
