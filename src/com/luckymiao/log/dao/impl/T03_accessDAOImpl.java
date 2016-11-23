package com.luckymiao.log.dao.impl;

import org.springframework.stereotype.Repository;

import com.luckymiao.base.BaseDAO;
import com.luckymiao.log.dao.T03_accessDAO;
import com.luckymiao.log.dto.T03_access;
@Repository("t03_accessDAO")
public class T03_accessDAOImpl extends BaseDAO implements T03_accessDAO{

	@Override
	public void insertT03_access(T03_access access) {
		this.getSqlSession().insert("insertT03_access", access);
	}

}
