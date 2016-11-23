package com.luckymiao.wx.dao.impl;

import org.springframework.stereotype.Repository;

import com.luckymiao.base.BaseDAO;
import com.luckymiao.wx.dao.T03_user_platformDAO;
import com.luckymiao.wx.dto.T03_user_platform;
@Repository("t03_user_platformDAO")
public class T03_user_platformDAOImpl extends BaseDAO implements T03_user_platformDAO{

	@Override
	public void insertT03_user_platform(T03_user_platform platform) {
		this.getSqlSession().insert("insertT03_user_platform", platform);
	}

}
