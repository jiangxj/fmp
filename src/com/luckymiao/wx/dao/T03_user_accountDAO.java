package com.luckymiao.wx.dao;

import com.luckymiao.base.DAO;
import com.luckymiao.wx.dto.T03_user_account;
import com.luckymiao.wx.dto.T03_user_account_log;

public interface T03_user_accountDAO extends DAO{
	public void insertT03_user_account(T03_user_account t03UserAccount);

	public void modifyT03_user_accountFreezeByAid(T03_user_account t03UserAccount);
	
	public void modifyT03_user_accountAddFreezeByAid(T03_user_account t03UserAccount);

	public void modifyT03_user_accountBalanceForAddByUid(
			T03_user_account account);

	public void insertT03_user_account_log(T03_user_account_log log);

	public T03_user_account getT03_user_accountByUid(T03_user_account account);
}
