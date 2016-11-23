package com.luckymiao.wx.dao;

import com.luckymiao.base.DAO;
import com.luckymiao.wx.dto.Wx_access_token;

public interface Wx_access_tokenDAO extends DAO{

	public Wx_access_token getWX_jsapi_ticketByAppid(Wx_access_token wxAccessToken);
	
}
