package com.luckymiao.common.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public class ReflectUtils {
	public static Method getGetMethod(Class cls, String fieldName){
		String targetMethodName = "get"+StringUtils.firstLetterUpper(fieldName);
		Method[] methods = cls.getDeclaredMethods();
		for (Method method : methods) {
			String currMethodName = method.getName();
			if(currMethodName.equals(targetMethodName)){
				return method;
			}
		}
		return null;
	}
	public static Method getSetMethod(Class cls, String fieldName){
		String targetMethodName = "set"+StringUtils.firstLetterUpper(fieldName);
		Method[] methods = cls.getDeclaredMethods();
		for (Method method : methods) {
			String currMethodName = method.getName();
			if(currMethodName.equals(targetMethodName)){
				return method;
			}
		}
		return null;
	}
	public static Object invokeSetMethod(Object obj, String fieldName, Object fieldValue) throws Exception{
		Class cls = obj.getClass();
		Method method = getSetMethod(cls, fieldName);
		if(method != null){
			method.invoke(obj, fieldValue);
		}
		return obj;
	}
}
