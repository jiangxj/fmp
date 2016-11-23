package com.luckymiao.quartz;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import com.luckymiao.common.utils.DateUtils;
import com.luckymiao.common.utils.HttpClientUtils;
import com.luckymiao.common.utils.SQLExecuteUtils;
import com.luckymiao.common.utils.StringUtils;

public class QuartzJob {
	Logger logger = Logger.getLogger(QuartzJob.class);
	private DataSource dataSource;
	
	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	private void work() {

	}
	
	public void wxfx() {
		System.out.println("Quartz的任务调度-start！！！");
		String jsapi_ticket = "";
		String grant_type = "client_credential";
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
			List<Map> mapList = SQLExecuteUtils.queryForList(conn, "select * from wx_app");
			for (Map rowMap : mapList) {
				String appid = (String)rowMap.get("APPID");
				String secret = (String)rowMap.get("SECRET");
				String urlAccesstoken = "https://api.weixin.qq.com/cgi-bin/token?grant_type="
					+ grant_type + "&" + "appid=" + appid + "&secret=" + secret;
			String accesstokenJSON = StringUtils.null2String(HttpClientUtils
					.sendGetRequest(urlAccesstoken));
			System.out.println("*************************access_token:"
					+ accesstokenJSON + "*************************");
			JSONObject accesstokenObj = new JSONObject(accesstokenJSON);
			String access_token = StringUtils.null2String(accesstokenObj
					.getString("access_token"));

			String urlJsapi = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="
					+ access_token + "&type=jsapi";

			String jsapiJSON = StringUtils.null2String(HttpClientUtils
					.sendGetRequest(urlJsapi));
			System.out.println("*************************jsapi:" + jsapiJSON
					+ "*************************");
			JSONObject jsapiObj = new JSONObject(jsapiJSON);
			jsapi_ticket = StringUtils.null2String(jsapiObj.get("ticket"));

			String updateSql = "update wx_access_token set jsapi_ticket = '"
					+ jsapi_ticket
					+ "',access_token = '"
					+ access_token
					+ "',createdate = '"
					+ DateUtils.getCurrTime()+"' "
					+ "where appid = '"+appid+"'";

			String insertSql = "insert into wx_access_token("
					+ "appid,jsapi_ticket,access_token,createdate) values "
					+ "('" + appid + "','" + jsapi_ticket +"','"
					+ access_token + "','"
					+ DateUtils.getCurrTime() + "')";
			
			int i = SQLExecuteUtils.update(conn, updateSql);
			if (i <= 0) {
				SQLExecuteUtils.update(conn, insertSql);
			}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				if(conn != null){
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Quartz的任务调度-finish！！！");
	}

}
