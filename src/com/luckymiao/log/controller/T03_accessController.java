package com.luckymiao.log.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.luckymiao.common.ResponseJson;
import com.luckymiao.log.dto.T03_access;
import com.luckymiao.log.service.T03_accessService;
@Controller
@RequestMapping("/access")
public class T03_accessController {
	@Autowired
	private T03_accessService t03_accessService;
	
	@ResponseBody
	@RequestMapping("/access_add")
	public String addT03_access(HttpServletRequest request, HttpServletResponse response, T03_access access){
		ResponseJson jsonObj = new ResponseJson();
		String callback = request.getParameter("callback");
		try {
			jsonObj = t03_accessService.addT03_access(request, response, access);
			jsonObj.setStatusSuccess();
		} catch (Exception e) {
			e.printStackTrace();
			jsonObj.setStatusError();
		}
		
		if(!StringUtils.isBlank(callback)){
			jsonObj.setJsonp(true);
			jsonObj.setCallback(callback);
		}
		return jsonObj.toString();
	}
}
