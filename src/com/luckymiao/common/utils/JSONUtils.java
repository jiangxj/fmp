package com.luckymiao.common.utils;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.xml.XMLSerializer;

public class JSONUtils {

	public static String mapToJSON(Map map) {

		return JSONObject.fromObject(map).toString();
	}

	public static String listToJSON(List list) {
		return JSONArray.fromObject(list).toString();
	}

	public static String objToJSON(Object o) {
		return JSONObject.fromObject(o).toString();
	}

	public static String xml2JSON(String xml) {
		JSONObject jobj = JSONObject.fromObject(xml);
		return jobj.toString();
	}

	public static String json2XML(String json) {
		JSONObject jobj = JSONObject.fromObject(json);
		String xml = new XMLSerializer().write(jobj);
		return xml;
	}
	

}
