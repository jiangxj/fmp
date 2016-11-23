package com.luckymiao.wx.dao;

import java.util.List;

import com.luckymiao.base.DAO;
import com.luckymiao.wx.dto.T03_user;
import com.luckymiao.wx.dto.T03_user_account;
import com.luckymiao.wx.dto.T03_user_account_log;
import com.luckymiao.wx.dto.T03_user_project;

public interface T03_userDAO extends DAO{

	public T03_user getT03_userByTelephone(T03_user t03User) throws Exception;

	public void insertT03_user(T03_user t03User) throws Exception;

	public T03_user getT03_userByTelephoneAndPassword(T03_user t03User) throws Exception;

	public void modifyT03_userAlipay_accountByTelephone(T03_user t03User) throws Exception;

	public void modifyT03_userTelephoneByTelephone(T03_user t03User);

	public T03_user_account getT03_user_accountByTelephone(
			T03_user_account t03UserAccount);

	public List getT03_user_account_logByAidShortDate(
			T03_user_account_log log);

	public List getT03_user_projectByTelephone(
			T03_user_project t03UserProject);

	public void modifyT03_user_accountBalanceByUid(T03_user_account user_account);

	public void modifyT03_userPasswordByTelephone(T03_user t03User);

	public List getT03_userListByCodeForInvitation(T03_user t03User, int page, int pageSize);
	
	public List getT03_userListByCodeForInvitation(T03_user t03User);

	public T03_user getT03_userByRecommend_code(T03_user t03User);


}
