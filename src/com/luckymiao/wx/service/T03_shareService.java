package com.luckymiao.wx.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.luckymiao.base.Service;
import com.luckymiao.common.ResponseJson;
import com.luckymiao.wx.dto.T03_share;

public interface T03_shareService extends Service{

	public ResponseJson addUserShare(HttpServletRequest request,
			HttpServletResponse response, T03_share t03Share) throws Exception;

}
