package com.luckymiao.wx.dao.impl;

import org.springframework.stereotype.Repository;

import com.luckymiao.base.BaseDAO;
import com.luckymiao.wx.dao.T03_user_accountDAO;
import com.luckymiao.wx.dto.T03_user_account;
import com.luckymiao.wx.dto.T03_user_account_log;
@Repository("t03_user_accountDAO")
public class T03_user_accountDAOImpl extends BaseDAO implements T03_user_accountDAO{
	@Override
	public void insertT03_user_account(T03_user_account t03UserAccount) {
		this.getSqlSession().insert("insertT03_user_account", t03UserAccount);
	}

	@Override
	public void modifyT03_user_accountFreezeByAid(T03_user_account t03UserAccount) {
		this.getSqlSession().update("modifyT03_user_accountFreezeByAid", t03UserAccount);
	}
	
	@Override
	public void modifyT03_user_accountAddFreezeByAid(T03_user_account t03UserAccount) {
		this.getSqlSession().update("modifyT03_user_accountAddFreezeByAid", t03UserAccount);
	}

	@Override
	public void modifyT03_user_accountBalanceForAddByUid(
			T03_user_account t03UserAccount) {
		this.getSqlSession().update("modifyT03_user_accountBalanceForAddByUid", t03UserAccount);
	}

	@Override
	public void insertT03_user_account_log(T03_user_account_log log) {
		this.getSqlSession().insert("insertT03_user_account_log", log);
	}

	@Override
	public T03_user_account getT03_user_accountByUid(T03_user_account account) {
		return this.getSqlSession().selectOne("getT03_user_accountByUid", account);
	}
}
