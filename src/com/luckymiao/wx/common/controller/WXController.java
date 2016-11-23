package com.luckymiao.wx.common.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.luckymiao.common.ResponseJson;
import com.luckymiao.common.utils.HttpClientUtils;
import com.luckymiao.wx.common.service.WXService;
import com.luckymiao.wx.utils.SignUtils;

@Controller
@RequestMapping("/wx")
public class WXController {
	@Autowired
	private WXService wXService;
	@ResponseBody
	@RequestMapping(value="/receive", method=RequestMethod.GET)
	public String valid(HttpServletRequest request, HttpServletResponse response) {
		String result = "";
		// 微信加密签名
		String signature = request.getParameter("signature");
		// 时间戳
		String timestamp = request.getParameter("timestamp");
		// 随机数
		String nonce = request.getParameter("nonce");
		// 随机字符串
		String echostr = request.getParameter("echostr");
		// 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
		if (SignUtils.checkSignature(signature, timestamp, nonce)) {
			result = echostr;
		}
		return result;
	}
	@ResponseBody
	@RequestMapping(value="/receive", method=RequestMethod.POST)
	public String handleWXMsg(HttpServletRequest request, HttpServletResponse response) {
		String result = wXService.handleWXMsg(request, response);
		return "success";
	}
	@ResponseBody
	@RequestMapping("/oauth2")
	public String oauth2(HttpServletRequest request, HttpServletResponse response){
		ResponseJson jsonObj = new ResponseJson();
		try {
			jsonObj = wXService.oauth2(request, response);
			jsonObj.setCode("0");
			jsonObj.setCodemsg("查询成功");
		} catch (Exception e) {
			jsonObj.setStatusError();
			e.printStackTrace();
		}
		return jsonObj.toString();
	}
}
