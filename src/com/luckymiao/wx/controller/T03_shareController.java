package com.luckymiao.wx.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.luckymiao.common.ResponseJson;
import com.luckymiao.wx.dto.T03_share;
import com.luckymiao.wx.service.T03_shareService;

@Controller
@RequestMapping("/share")
public class T03_shareController {
	@Autowired
	private T03_shareService t03_shareService;
	@ResponseBody
	@RequestMapping("/share_do")
	public String addUserShare(HttpServletRequest request, HttpServletResponse response, T03_share t03_share){
		ResponseJson jsonObj = new ResponseJson();
		try {
			jsonObj = t03_shareService.addUserShare(request, response, t03_share);
			jsonObj.setCode("0");
			jsonObj.setCodemsg("插入成功");
		} catch (Exception e) {
			jsonObj.setStatusError();
			e.printStackTrace();
		}
		return jsonObj.toString();
	}
}
