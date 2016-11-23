package com.luckymiao.wx.dao;

import java.util.List;

import com.luckymiao.base.DAO;
import com.luckymiao.wx.dto.T03_project;
import com.luckymiao.wx.dto.T03_project_access;
import com.luckymiao.wx.dto.T03_user_platform;
import com.luckymiao.wx.dto.T03_user_project;

public interface T03_projectDAO extends DAO{

	public T03_project getT01_projectByP_id(T03_project project) throws Exception;

	public List getT03_project_guideByP_id(T03_project project) throws Exception;

	public void insertT03_user_project(T03_user_project userProject);

	public T03_user_project getT03_user_projectByPidAndTelephone(T03_user_project param);

	public void insertT03_project_access(T03_project_access access);

	public void modifyT03_projectP_click_acount(T03_project project);

	public T03_user_platform getT03_user_platformByPlatformidAndTelephone(
			T03_user_platform platform);

}
