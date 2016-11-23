package com.luckymiao.wx.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.luckymiao.common.ResponseJson;
import com.luckymiao.wx.dto.T03_project;
import com.luckymiao.wx.service.T03_projectService;

@Controller
@RequestMapping("/project")
public class T03_projectController {
	@Autowired
	private T03_projectService t03_projectService;
	
	@ResponseBody
	@RequestMapping("/project_detail")
	public String getProjectDetail(HttpServletRequest request,
			HttpServletResponse response, T03_project project) {
		ResponseJson jsonObj = t03_projectService.getProjectDetail(request, response, project);
		return jsonObj.toString();
	}
	
	@ResponseBody
	@RequestMapping("/project_join")
	public String joinProject(HttpServletRequest request,
			HttpServletResponse response, T03_project project) {
		ResponseJson jsonObj = new ResponseJson();
		try {
			jsonObj = t03_projectService.joinProject(request, response, project);
		} catch (Exception e) {
			e.printStackTrace();
			jsonObj.setStatusError();
		}
		return jsonObj.toString();
	}
	
	@ResponseBody
	@RequestMapping("/project_list")
	public String getProjectList(HttpServletRequest request,
			HttpServletResponse response, T03_project project) {
		ResponseJson jsonObj = t03_projectService.getProjectList(request, response, project);
		return jsonObj.toString();
	}
}
