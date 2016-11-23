package com.luckymiao.wx.dto;

public class Wx_access_token {
	private String appid = "";
	private String jsapi_ticket = "";
	private String access_token = "";
	private String createdate = "";
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public String getJsapi_ticket() {
		return jsapi_ticket;
	}
	public void setJsapi_ticket(String jsapiTicket) {
		jsapi_ticket = jsapiTicket;
	}
	public String getAccess_token() {
		return access_token;
	}
	public void setAccess_token(String accessToken) {
		access_token = accessToken;
	}
	public String getCreatedate() {
		return createdate;
	}
	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}
	
}
