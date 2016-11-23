package com.luckymiao.wx.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.luckymiao.base.BaseDAO;
import com.luckymiao.wx.dao.T03_user_withdrawDAO;
import com.luckymiao.wx.dto.T03_user_withdraw;

@Repository("t03_user_withdrawDAO")
public class T03_user_withdrawDAOImpl extends BaseDAO implements T03_user_withdrawDAO{

	@Override
	public void insertT03_user_withdraw(T03_user_withdraw withdraw) {
		this.getSqlSession().insert("insertT03_user_withdraw", withdraw);
	}

	@Override
	public List getT03_user_withdrawListByTelephone(
			T03_user_withdraw t03UserWithdraw) {
		return this.getSqlSession().selectList("getT03_user_withdrawListByTelephone", t03UserWithdraw);
	}

}
