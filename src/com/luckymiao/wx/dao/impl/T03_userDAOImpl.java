package com.luckymiao.wx.dao.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import com.luckymiao.base.BaseDAO;
import com.luckymiao.wx.dao.T03_userDAO;
import com.luckymiao.wx.dto.T03_user;
import com.luckymiao.wx.dto.T03_user_account;
import com.luckymiao.wx.dto.T03_user_account_log;
import com.luckymiao.wx.dto.T03_user_project;
@Repository("t03_userDAO")
public class T03_userDAOImpl extends BaseDAO implements T03_userDAO{

	@Override
	public T03_user getT03_userByTelephone(T03_user t03User) throws Exception{
		T03_user user = this.getSqlSession().selectOne("getT03_userByTelephone", t03User);
		if(user == null){
			user = new T03_user();
		}
		return user;
	}

	@Override
	public void insertT03_user(T03_user t03User) throws Exception{
		this.getSqlSession().insert("insertT03_user", t03User);
	}

	@Override
	public T03_user getT03_userByTelephoneAndPassword(T03_user t03User) throws Exception{
		return this.getSqlSession().selectOne("getT03_userByTelephoneAndPassword", t03User);
	}

	@Override
	public void modifyT03_userAlipay_accountByTelephone(T03_user t03User) throws Exception{
		this.getSqlSession().update("modifyT03_userAlipay_accountByTelephone", t03User);
	}

	@Override
	public void modifyT03_userTelephoneByTelephone(T03_user t03User) {
		this.getSqlSession().update("modifyT03_userTelephoneByTelephone", t03User);
		
	}

	@Override
	public T03_user_account getT03_user_accountByTelephone(
			T03_user_account t03UserAccount) {
		return this.getSqlSession().selectOne("getT03_user_accountByTelephone", t03UserAccount);
	}

	@Override
	public List getT03_user_account_logByAidShortDate(
			T03_user_account_log log) {
		return this.getSqlSession().selectList("getT03_user_account_logByAidShortDate", log);
	}

	@Override
	public List getT03_user_projectByTelephone(T03_user_project t03UserProject) {
		return this.getSqlSession().selectList("getT03_user_projectByTelephone", t03UserProject);
	}

	@Override
	public void modifyT03_user_accountBalanceByUid(
			T03_user_account userAccount) {
		this.getSqlSession().update("modifyT03_user_accountBalanceByUid", userAccount);
	}

	@Override
	public void modifyT03_userPasswordByTelephone(T03_user t03User) {
		this.getSqlSession().update("modifyT03_userPasswordByTelephone", t03User);
	}

	@Override
	public List getT03_userListByCodeForInvitation(T03_user t03User, int page, int pageSize) {
		return this.getSqlSession().selectList("getT03_userListByCodeForInvitation", t03User, new RowBounds(page, pageSize));
	}
	
	@Override
	public List getT03_userListByCodeForInvitation(T03_user t03User) {
		return this.getSqlSession().selectList("getT03_userListByCodeForInvitation", t03User);
	}

	@Override
	public T03_user getT03_userByRecommend_code(T03_user t03User) {
		return this.getSqlSession().selectOne("getT03_userByRecommend_code", t03User);
	}

}
