package com.luckymiao.wx.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.luckymiao.base.Service;
import com.luckymiao.common.ResponseJson;
import com.luckymiao.wx.dto.T03_suggest;

public interface T03_suggestService extends Service{

	public ResponseJson saveSuggestContent(HttpServletRequest request,
			HttpServletResponse response, T03_suggest suggest);

}
