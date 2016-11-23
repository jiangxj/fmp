package com.luckymiao.wx.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.luckymiao.base.BaseService;
import com.luckymiao.common.ResponseJson;
import com.luckymiao.common.utils.DateUtils;
import com.luckymiao.common.utils.StringUtils;
import com.luckymiao.wx.dao.T03_projectDAO;
import com.luckymiao.wx.dao.T03_userDAO;
import com.luckymiao.wx.dao.T03_user_platformDAO;
import com.luckymiao.wx.dto.T03_project;
import com.luckymiao.wx.dto.T03_project_access;
import com.luckymiao.wx.dto.T03_project_guide;
import com.luckymiao.wx.dto.T03_user;
import com.luckymiao.wx.dto.T03_user_platform;
import com.luckymiao.wx.dto.T03_user_project;
import com.luckymiao.wx.service.T03_projectService;
@Service("t03_projectServiceImpl")
public class T03_projectServiceImpl extends BaseService implements
		T03_projectService {
	@Autowired
	private T03_projectDAO t03_projectDAO;
	@Autowired
	private T03_userDAO t03_userDAO;
	@Autowired
	private T03_user_platformDAO t03_user_platformDAO;
	
	
	@Override
	public ResponseJson getProjectDetail(HttpServletRequest request,
			HttpServletResponse response, T03_project project) {
		Map dataMap = new HashMap();
		ResponseJson result = new ResponseJson();
		String telephone = StringUtils.null2String(request.getParameter("telephone"));
		try {
			T03_project p = t03_projectDAO.getT01_projectByP_id(project);
			List<T03_project_guide> list = t03_projectDAO.getT03_project_guideByP_id(project);
			
			T03_user t03User = new T03_user();
			t03User.setTelephone(telephone);
			T03_user userDb = t03_userDAO.getT03_userByTelephone(t03User);
			T03_project_access access = new T03_project_access();
			access.setCid(StringUtils.getDateNumberSequence());
			access.setCreatedate(DateUtils.getCurrTime());
			access.setPid(project.getP_id());
			access.setUid(userDb.getUid());
			t03_projectDAO.insertT03_project_access(access);
			
			T03_project param = new T03_project();
			param.setP_id(p.getP_id());
			param.setP_click_amount(new BigDecimal(p.getP_click_amount()).add(new BigDecimal("1")).toString());
			t03_projectDAO.modifyT03_projectP_click_acount(param);
			
			dataMap.put("project", p);
			dataMap.put("guide_list", list);
			result.setData(dataMap);
			result.setCode("0");
			result.setCodemsg("查询成功！");
		} catch (Exception e) {
			result.setStatusError();
			e.printStackTrace();
		}
		return result;
	}
	@Transactional
	@Override
	public ResponseJson joinProject(HttpServletRequest request,
			HttpServletResponse response, T03_project project) throws Exception{
		ResponseJson result = new ResponseJson();
		String telephone = request.getParameter("telephone");
		T03_project p = t03_projectDAO.getT01_projectByP_id(project);
		if("1".equals(p.getIs_outdate())){
			result.setCode("1");
			result.setCodemsg("项目已过期！");
		}else if(p == null || "".equals(p.getP_id())){
			result.setCode("2");
			result.setCodemsg("获取失败！");
		}else{
			T03_user_project param = new T03_user_project();
			param.setPid(project.getP_id());
			param.setTelephone(telephone);
			T03_user_project projectDb = t03_projectDAO.getT03_user_projectByPidAndTelephone(param);
			if(projectDb == null || "".equals(projectDb.getTid())){
				
				T03_user t03User = new T03_user();
				t03User.setTelephone(telephone);
				T03_user userDb = t03_userDAO.getT03_userByTelephone(t03User);
				
				T03_user_project user_project = new T03_user_project();
				user_project.setTid(StringUtils.getDateNumberSequence());
				user_project.setUid(userDb.getUid());
				user_project.setCreatedate(DateUtils.getCurrTime());
				user_project.setModifydate(DateUtils.getCurrTime());
				user_project.setStatus("1");
				user_project.setPid(project.getP_id());
				t03_projectDAO.insertT03_user_project(user_project);
				
				
				T03_user_platform platform = new T03_user_platform();
				platform.setId(StringUtils.getDateNumberSequence());
				platform.setUid(userDb.getUid());
				platform.setIs_regist("0");
				platform.setPlatformid(p.getP_platform_id());
				platform.setCreatedate(DateUtils.getCurrTime());
				t03_user_platformDAO.insertT03_user_platform(platform);
			}
			result.setData(p.getP_url());
			result.setCode("0");
			result.setCodemsg("查询成功！");
		}
		return result;
	}

	@Override
	public ResponseJson getProjectList(HttpServletRequest request,
			HttpServletResponse response, T03_project project) {
		ResponseJson result = new ResponseJson();
		String telephone = request.getParameter("telephone");
		try {
			
		} catch (Exception e) {
			result.setStatusError();
			e.printStackTrace();
		}
		return result;
	}

}
