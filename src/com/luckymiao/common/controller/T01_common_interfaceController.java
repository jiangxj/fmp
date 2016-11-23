package com.luckymiao.common.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.luckymiao.base.BaseController;
import com.luckymiao.common.ResponseJson;
import com.luckymiao.common.service.T01_common_interfaceService;

@Controller
@RequestMapping("/common_interface")
public class T01_common_interfaceController extends BaseController{
	private static Logger logger = Logger.getLogger(T01_common_interfaceController.class.getName());
	@Autowired
	private T01_common_interfaceService t01_common_interfaceService;
	
	@RequestMapping("/query")
	@ResponseBody
	public String getResultFromCommonInterface(HttpServletRequest request, HttpServletResponse response){
		ResponseJson jsonObj = t01_common_interfaceService.getResultFromCommonInterface(request);
		return jsonObj.toString();
	}
	
	@RequestMapping("/update")
	@ResponseBody
	public String updateFromCommonInterface(HttpServletRequest request, HttpServletResponse response){
		ResponseJson jsonObj = t01_common_interfaceService.updateFromCommonInterface(request);
		return jsonObj.toString();
	}
}
