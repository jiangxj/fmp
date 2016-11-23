package com.luckymiao.wx.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.luckymiao.base.Service;
import com.luckymiao.common.ResponseJson;
import com.luckymiao.wx.dto.T03_user;
import com.luckymiao.wx.dto.T03_user_account;
import com.luckymiao.wx.dto.T03_user_project;
import com.luckymiao.wx.dto.T03_user_withdraw;

public interface T03_userService extends Service{

	public ResponseJson registUser(HttpServletRequest request,
			HttpServletResponse response, T03_user t03User) throws Exception ;

	public ResponseJson loginUser(HttpServletRequest request,
			HttpServletResponse response, T03_user t00User);

	public ResponseJson getUserInfo(HttpServletRequest request,
			HttpServletResponse response, T03_user t03User);

	public ResponseJson verifyAuthCode(HttpServletRequest request,
			HttpServletResponse response, T03_user t03User);
	
	public ResponseJson sendAuthCode(HttpServletRequest request,
			HttpServletResponse response, T03_user t03User);

	public ResponseJson modifyAlipayAccount(HttpServletRequest request,
			HttpServletResponse response, T03_user t03User);

	public ResponseJson modifyUserTelephone(HttpServletRequest request,
			HttpServletResponse response, T03_user t03User);

	public ResponseJson singleVerifyAuthCode(HttpServletRequest request,
			HttpServletResponse response, T03_user t03User);

	public ResponseJson getUserAccount(HttpServletRequest request,
			HttpServletResponse response, T03_user_account t03_user_account);

	public ResponseJson getUserProject(HttpServletRequest request,
			HttpServletResponse response, T03_user_project t03UserProject);

	public ResponseJson peformUserWithdraw(HttpServletRequest request,
			HttpServletResponse response, T03_user_withdraw t03_user_withdraw) throws Exception;

	public ResponseJson getUserWithdrawList(HttpServletRequest request,
			HttpServletResponse response, T03_user_withdraw t03UserWithdraw);

	public ResponseJson modifyUserPassword(HttpServletRequest request,
			HttpServletResponse response, T03_user t03User);

	public ResponseJson getUserInvitationList(HttpServletRequest request,
			HttpServletResponse response, T03_user t03User);

}
