package com.luckymiao.wx.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.luckymiao.common.ResponseJson;
import com.luckymiao.wx.dto.T03_user;
import com.luckymiao.wx.dto.T03_user_account;
import com.luckymiao.wx.dto.T03_user_project;
import com.luckymiao.wx.dto.T03_user_withdraw;
import com.luckymiao.wx.service.T03_userService;

@Controller
@RequestMapping("/user")
public class T03_userController {
	
	@Autowired
	private T03_userService t03_userService;
	
	@ResponseBody
	@RequestMapping("/regist")
	public String registUser(HttpServletRequest request,
			HttpServletResponse response, T03_user t03_user) {
		ResponseJson jsonObj = new ResponseJson();
		try {
			jsonObj = t03_userService.registUser(request, response, t03_user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonObj.toString();
	}
	
	@ResponseBody
	@RequestMapping("/login")
	public String loginUser(HttpServletRequest request,
			HttpServletResponse response, T03_user t03_user) {
		ResponseJson jsonObj = t03_userService.loginUser(request, response, t03_user);
		return jsonObj.toString();
	}
	
	@ResponseBody
	@RequestMapping("/user_info")
	public String getUserInfo(HttpServletRequest request,
			HttpServletResponse response, T03_user t03_user) {
		ResponseJson jsonObj = t03_userService.getUserInfo(request, response, t03_user);
		return jsonObj.toString();
	}
	
	@ResponseBody
	@RequestMapping("/verify_authcode")
	public String verifyAuthCode(HttpServletRequest request,
			HttpServletResponse response, T03_user t03_user) {
		ResponseJson jsonObj = t03_userService.verifyAuthCode(request, response, t03_user);
		return jsonObj.toString();
	}
	
	@ResponseBody
	@RequestMapping("/single_verify_authcode")
	public String singleVerifyAuthCode(HttpServletRequest request,
			HttpServletResponse response, T03_user t03_user) {
		ResponseJson jsonObj = t03_userService.singleVerifyAuthCode(request, response, t03_user);
		return jsonObj.toString();
	}
	
	@ResponseBody
	@RequestMapping("/send_authcode")
	public String sendAuthCode(HttpServletRequest request,
			HttpServletResponse response, T03_user t03_user) {
		ResponseJson jsonObj = t03_userService.sendAuthCode(request, response, t03_user);
		return jsonObj.toString();
	}
	
	@ResponseBody
	@RequestMapping("/alipay_modify")
	public String modifyAlipayAccount(HttpServletRequest request,
			HttpServletResponse response, T03_user t03_user) {
		ResponseJson jsonObj = t03_userService.modifyAlipayAccount(request, response, t03_user);
		return jsonObj.toString();
	}
	
	@ResponseBody
	@RequestMapping("/telephone_modify")
	public String modifyUserTelephone(HttpServletRequest request,
			HttpServletResponse response, T03_user t03_user) {
		ResponseJson jsonObj = t03_userService.modifyUserTelephone(request, response, t03_user);
		return jsonObj.toString();
	}
	
	@ResponseBody
	@RequestMapping("/password_modify")
	public String modifyUserPassword(HttpServletRequest request,
			HttpServletResponse response, T03_user t03_user) {
		ResponseJson jsonObj = t03_userService.modifyUserPassword(request, response, t03_user);
		return jsonObj.toString();
	}
	
	@ResponseBody
	@RequestMapping("/account_info")
	public String getUserAccount(HttpServletRequest request,
			HttpServletResponse response, T03_user_account t03_user_account) {
		ResponseJson jsonObj = t03_userService.getUserAccount(request, response, t03_user_account);
		return jsonObj.toString();
	}
	
	@ResponseBody
	@RequestMapping("/user_project")
	public String getUserProject(HttpServletRequest request,
			HttpServletResponse response, T03_user_project t03_user_project) {
		ResponseJson jsonObj = t03_userService.getUserProject(request, response, t03_user_project);
		return jsonObj.toString();
	}
	
	@ResponseBody
	@RequestMapping("/user_withdraw")
	public String peformUserWithdraw(HttpServletRequest request,
			HttpServletResponse response, T03_user_withdraw t03_user_withdraw) {
		ResponseJson jsonObj = new ResponseJson();
		try {
			jsonObj = t03_userService.peformUserWithdraw(request, response, t03_user_withdraw);
		} catch (Exception e) {
			e.printStackTrace();
			jsonObj.setStatusError();
		}
		return jsonObj.toString();
	}
	@ResponseBody
	@RequestMapping("/withdraw_list")
	public String getUserWithdrawList(HttpServletRequest request,
			HttpServletResponse response, T03_user_withdraw t03_user_withdraw) {
		ResponseJson jsonObj = t03_userService.getUserWithdrawList(request, response, t03_user_withdraw);
		return jsonObj.toString();
	}
	@ResponseBody
	@RequestMapping("/invitation_list")
	public String getUserInvitationList(HttpServletRequest request,
			HttpServletResponse response, T03_user t03_user) {
		ResponseJson jsonObj = t03_userService.getUserInvitationList(request, response, t03_user);
		return jsonObj.toString();
	}
	
}
