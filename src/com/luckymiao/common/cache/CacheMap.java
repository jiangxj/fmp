package com.luckymiao.common.cache;

import java.util.HashMap;
import java.util.Map;

public class CacheMap {
	private static Map<String, Object> cMap = new HashMap();
	public static void put(String key, Object obj){
		cMap.put(key, obj);
	}
	public static void remove(String key){
		cMap.remove(key);
	}
	public static Object get(String key){
		return cMap.get(key);
	}
	public static void clear(String key){
		cMap.clear();
	}
}
