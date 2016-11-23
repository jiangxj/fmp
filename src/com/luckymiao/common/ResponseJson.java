package com.luckymiao.common;

import net.sf.json.JSONObject;

import com.luckymiao.common.mybatis.plugin.PageParameter;

public class ResponseJson {
	private boolean jsonp = false;
	private String callback = "";
	private String status = "ok";
	private String statusmsg = "成功";
	private String code = "";
	private String codemsg = "";
	private Object data = new Object();

	public String getStatus() {
		return status;
	}
	
	public String getStatusmsg() {
		return statusmsg;
	}

	public String getCode() {
		return code;
	}

	public String getCodemsg() {
		return codemsg;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setCodemsg(String codemsg) {
		this.codemsg = codemsg;
	}

	public void setStatusFailure() {
		this.status = "failure";
		this.statusmsg = "失败";
	}

	public void setStatusSuccess() {
		this.status = "ok";
	}

	public void setStatusError() {
		this.status = "error";
		this.statusmsg = "程序异常";
	}

	public String toString() {
		String result = "";
		if(jsonp && this.callback != null){
			result = this.callback+"("+JSONObject.fromObject(this).toString()+")";
		}else{
			result = JSONObject.fromObject(this).toString();
		}
		return result;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public void setJsonp(boolean jsonp) {
		this.jsonp = jsonp;
	}

	public void setCallback(String callback) {
		this.callback = callback;
	}
}
