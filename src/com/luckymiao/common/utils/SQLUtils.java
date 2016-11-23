package com.luckymiao.common.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

public class SQLUtils {
	/** 网络参数替换标志符 */
	public static String NET_SING = "@";

	/** 内置参数替换标志符 */
	public static String STATIC_SING = "#";

	/** 内置参数替换标志符 */
	public static String NOTEMPTY_START_SING = "<NOTEMPTY>";

	/** 内置参数替换标志符 */
	public static String NOTEMPTY_END_SING = "</NOTEMPTY>";

	/** 网络参数替换模式 */
	public static String NET_PARRERN = "@[a-zA-Z0-9_:]+@";

	/** 内置参数替换模式 */
	public static String STATIC_PARRERN = "#[a-zA-Z0-9_:]+#";

	/** 内置参数替换模式 */
	public static String NOTEMPTY_PARRERN = "<NOTEMPTY>[\\d\\w\\s=@:_]+</NOTEMPTY>";

	/** 参数及数据类型用分割符 */
	public static String SEPARATOR = ":";

	/** 替换后的空值 */
	private static final String STR_NULL = "^^^^^";

	public static List parseSQL(String sql, HttpServletRequest request) {
		List resultSqlList = new ArrayList();
		if (sql != null) {
			String[] sqls = sql.split(";");
			for (String sqltmp : sqls) {
				resultSqlList.add(parseSingleSQLDynamicParam(sqltmp, request));
			}
		}
		return resultSqlList;
	}

	public static String parseSingleSQLDynamicParam(String sql,
			HttpServletRequest request) {
		String resultSql = "";
		if (sql != null && !"".equals(sql)) {
			resultSql = sql.replace("#UUID:VARCHAR#", "'"
					+ StringUtils.getUUID() + "'");
			resultSql = resultSql.replace("#CURR_DATE:VARCHAR#", "'"
					+ DateUtils.getCurrDate() + "'");
			resultSql = resultSql.replace("#CURR_TIME:VARCHAR#", "'"
					+ DateUtils.getCurrTime() + "'");
			resultSql = resultSql.replace("#CLIENT_IP:VARCHAR#", "'"
					+ request.getRemoteHost() + "'");

			Pattern p2 = Pattern.compile(NOTEMPTY_PARRERN);
			Matcher m2 = p2.matcher(resultSql);
			
			while (m2.find()) {
				String eStr = m2.group();
				String tempStr = eStr.replace(NOTEMPTY_START_SING, "").replace(
						NOTEMPTY_END_SING, "");
				
				String subSql = tempStr;
				Pattern p = Pattern.compile(NET_PARRERN);
				Matcher m = p.matcher(subSql);
				boolean flag = false;
				while (m.find()) {
					String subStr = m.group();
					String str = subStr.replace(NET_SING, "");
					String[] strs = str.split(SEPARATOR);
					if(org.apache.commons.lang.StringUtils.isBlank(getValueIgnoreCaseKey(request, strs[0]))){
						flag = true;
						break;
					}
					if ("VARCHAR".equals(strs[1])) {
						subSql = subSql.replace(subStr, "'"
								+ getValueIgnoreCaseKey(request, strs[0]) + "'");
					} else {
						subStr = subStr.replace(subStr, getValueIgnoreCaseKey(
								request, strs[0]));
					}
				}
				if(flag){
					resultSql = resultSql.replace(eStr, " ");
				}else{
					resultSql = resultSql.replace(eStr, subSql);
				}
			}
			

			Pattern p1 = Pattern.compile(NET_PARRERN);
			Matcher m1 = p1.matcher(resultSql);
			while (m1.find()) {
				String eStr = m1.group();
				String tempStr = eStr.replace(NET_SING, "");
				String[] strs = tempStr.split(SEPARATOR);
				if ("VARCHAR".equals(strs[1])) {
					resultSql = resultSql.replace(eStr, "'"
							+ getValueIgnoreCaseKey(request, strs[0]) + "'");
				} else {
					resultSql = resultSql.replace(eStr, getValueIgnoreCaseKey(
							request, strs[0]));
				}

			}
		}
		return resultSql;
	}

	private static String getValueIgnoreCaseKey(HttpServletRequest request,
			String key) {
		String value = "";
		Map reqMap = request.getParameterMap();
		if (key != null) {
			Iterator<String> it = reqMap.keySet().iterator();
			while (it.hasNext()) {
				String key1 = it.next();
				if (key.equalsIgnoreCase(key1)) {
					value = ((Object[]) reqMap.get(key1))[0].toString();
					break;
				}
			}
		}
		if (org.apache.commons.lang.StringUtils.isBlank(value)) {
			value = StringUtils.null2String(request.getAttribute(key));
		} else if (org.apache.commons.lang.StringUtils.isBlank(value)) {
			value = StringUtils.null2String(request.getSession().getAttribute(
					key));
		} else if (org.apache.commons.lang.StringUtils.isBlank(value)) {
			value = "";
		}
		return value;
	}

	public static void main(String[] args) {
		Pattern p2 = Pattern.compile("<NOTEMPTY>[\\d\\w\\s=@:_]+</NOTEMPTY>");
		Matcher m2 = p2.matcher("select * from ekela_product where 1>0 <NOTEMPTY> and p_type = @p_type:VARCHAR@ </NOTEMPTY> and 1>0 <NOTEMPTY> and p_title = @p_title:VARCHAR@ </NOTEMPTY> #LIMIT#");
		while (m2.find()) {
			String eStr = m2.group();
			System.out.println(eStr);
		}
	}
}
