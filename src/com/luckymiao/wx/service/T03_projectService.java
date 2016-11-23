package com.luckymiao.wx.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.luckymiao.base.Service;
import com.luckymiao.common.ResponseJson;
import com.luckymiao.wx.dto.T03_project;

public interface T03_projectService extends Service{

	public ResponseJson getProjectDetail(HttpServletRequest request,
			HttpServletResponse response, T03_project project);

	public ResponseJson joinProject(HttpServletRequest request,
			HttpServletResponse response, T03_project project) throws Exception;

	public ResponseJson getProjectList(HttpServletRequest request,
			HttpServletResponse response, T03_project project);

}
