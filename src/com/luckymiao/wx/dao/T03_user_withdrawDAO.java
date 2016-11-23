package com.luckymiao.wx.dao;

import java.util.List;

import com.luckymiao.base.DAO;
import com.luckymiao.wx.dto.T03_user_withdraw;

public interface T03_user_withdrawDAO extends DAO{

	public void insertT03_user_withdraw(T03_user_withdraw withdraw);

	public List getT03_user_withdrawListByTelephone(
			T03_user_withdraw t03UserWithdraw);

}
