package com.luckymiao.wx.dao.impl;

import org.springframework.stereotype.Repository;

import com.luckymiao.base.BaseDAO;
import com.luckymiao.wx.dao.T03_shareDAO;
import com.luckymiao.wx.dto.T03_share;
@Repository("t03_shareDAO")
public class T03_shareDAOImpl extends BaseDAO implements T03_shareDAO{

	@Override
	public void insertT03_share(T03_share t03Share) {
		this.getSqlSession().insert("insertT03_share", t03Share);
	}

}
