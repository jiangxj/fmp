package com.luckymiao.wx.dao.impl;

import org.springframework.stereotype.Repository;

import com.luckymiao.base.BaseDAO;
import com.luckymiao.wx.dao.T03_suggestDAO;
import com.luckymiao.wx.dto.T03_suggest;
@Repository("t03_suggestDAO")
public class T03_suggestDAOImpl extends BaseDAO implements T03_suggestDAO{

	@Override
	public void insertT03_suggest(T03_suggest suggest) {
		this.getSqlSession().insert("insertT03_suggest", suggest);
	}

}
