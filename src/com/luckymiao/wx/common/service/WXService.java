package com.luckymiao.wx.common.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.luckymiao.base.Service;
import com.luckymiao.common.ResponseJson;

public interface WXService extends Service{

	public String handleWXMsg(HttpServletRequest request, HttpServletResponse response);

	public ResponseJson oauth2(HttpServletRequest request,
			HttpServletResponse response) throws Exception;

}
