package com.luckymiao.wx.dao.impl;

import org.springframework.stereotype.Repository;

import com.luckymiao.base.BaseDAO;
import com.luckymiao.wx.dao.Wx_access_tokenDAO;
import com.luckymiao.wx.dto.Wx_access_token;
@Repository("wx_access_tokenDAO")
public class Wx_access_tokenDAOImpl extends BaseDAO implements Wx_access_tokenDAO{

	@Override
	public Wx_access_token getWX_jsapi_ticketByAppid(Wx_access_token wxAccessToken) {
		return this.getSqlSession().selectOne("getWX_jsapi_ticketByAppid", wxAccessToken);
	}

}
