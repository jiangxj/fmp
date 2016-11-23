package com.luckymiao.common.utils;

import java.text.MessageFormat;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {
	public static String getUUID() {
		return UUID.randomUUID().toString().replace("-", "");
	}

	public static String null2String(Object str) {
		if (str == null || "null".equals(str.toString().trim())) {
			return "";
		}
		return str.toString();
	}
	
	public static String null2Zero(Object str) {
		if (str == null || "null".equals(str.toString().trim()) || "".equals(str)) {
			return "0";
		}
		return str.toString();
	}
	public static String null2One(Object str) {
		if (str == null || "null".equals(str.toString().trim()) || "".equals(str)) {
			return "1";
		}
		return str.toString();
	}

	public static String trim2String(String str) {
		if (str == null) {
			return "";
		}
		return str.trim();
	}

	public static String trim2null(String str) {
		if (str == null || "".equals(str.trim())) {
			return null;
		}
		return str.trim();
	}

	public boolean isBlank(String str) {
		if (str == null || "".equals(str.trim())) {
			return true;
		}
		return false;
	}

	public static String messageFormat(String pattern, Object[] arguments) {
		return MessageFormat.format(pattern, arguments);
	}
	
	public static boolean isNumberStr(String str){
		if("".equals(str) || str == null){
			return false;
		}
		Pattern p = Pattern.compile("-?\\d+$");
		Matcher m = p.matcher(str);
		if(m.matches()){
			return true;
		}
		return false;
		
	}
	public static boolean isLegalPassword(String password){
		if("".equals(password) || password == null){
			return true;
		}
		Pattern p = Pattern.compile("[0-9A-Za-z]+");
		Matcher m = p.matcher(password);
		if(m.matches()){
			return true;
		}
		return false;
		
	}
	/**
	 * 验证是否为合法的手机号码
	 * @param phone
	 * @return
	 */
	public static boolean isMobilePhone(String phone){
		if(org.apache.commons.lang.StringUtils.isNotBlank(phone)){
			Pattern pattern=Pattern.compile("^((1[0-9][0-9]))\\d{8}$");
			Matcher match=pattern.matcher(phone);
			if(match.matches()){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 创建指定数量的随机字符串
	 * 
	 * @param numberFlag
	 *            是否是数字
	 * @param length
	 * @return
	 */
	public static String createRandom(boolean numberFlag, int length) {
		String retStr = "";
		String strTable = numberFlag ? "1234567890" : "1234567890abcdefghijkmnpqrstuvwxyz";
		int len = strTable.length();
		boolean bDone = true;
		do {
			retStr = "";
			int count = 0;
			for (int i = 0; i < length; i++) {
				double dblR = Math.random() * len;
				int intR = (int) Math.floor(dblR);
				char c = strTable.charAt(intR);
				if (('0' <= c) && (c <= '9')) {
					count++;
				}
				retStr += strTable.charAt(intR);
			}
			if (count >= 2) {
				bDone = false;
			}
		} while (bDone);

		return retStr;
	}
	
	public static String firstLetterUpper(String str){
		String resultStr = null2String(str);
		String firstLetter = "";
		String otherLetter = "";
		if(resultStr.length()>1){
			firstLetter = resultStr.substring(0, 1).toUpperCase();
			otherLetter = resultStr.substring(1);
		}
		resultStr = firstLetter + otherLetter;
		return resultStr;
	}
	public static String httpURLParamsJoin(String baseUrl, String paramStrs){
		if(baseUrl == null || "".equals(baseUrl)){
			return "";
		}
		if(paramStrs == null || "".equals(paramStrs)){
			return baseUrl;
		}
		return baseUrl.indexOf("?")>=0 ? (baseUrl+"&"+paramStrs) : (baseUrl+"?"+paramStrs);
	}
	public static boolean isIP(String ip){
		Pattern pattern = Pattern.compile("\\b((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\b");
		Matcher matcher = pattern.matcher(ip);
		return matcher.matches();
	}
	public static boolean isEmail(String email){
		Pattern pattern = Pattern.compile("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$");
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}
	
	public static String getDateNumberSequence(){
		return createRandom(true ,6) + DateUtils.getCurrentDateyyyyMMddHHmmss() + createRandom(true ,6);
	}
	
	public static void main(String[] args) {
		System.out.println(getDateNumberSequence());
	}
}
