package com.luckymiao.wx.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.luckymiao.common.ResponseJson;
import com.luckymiao.wx.dto.T03_suggest;
import com.luckymiao.wx.service.T03_suggestService;

@Controller
@RequestMapping("/suggest")
public class T03_suggestController {
	@Autowired
	private T03_suggestService t03_suggestService;
	@ResponseBody
	@RequestMapping("/suggest_save")
	public String saveSuggestContent(HttpServletRequest request,
			HttpServletResponse response, T03_suggest suggest){
		ResponseJson jsonObj = t03_suggestService.saveSuggestContent(request, response, suggest);
		return jsonObj.toString();
	}
}
