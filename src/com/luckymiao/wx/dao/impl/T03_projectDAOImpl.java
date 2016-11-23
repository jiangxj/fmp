package com.luckymiao.wx.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.luckymiao.base.BaseDAO;
import com.luckymiao.wx.dao.T03_projectDAO;
import com.luckymiao.wx.dto.T03_project;
import com.luckymiao.wx.dto.T03_project_access;
import com.luckymiao.wx.dto.T03_user_platform;
import com.luckymiao.wx.dto.T03_user_project;
@Repository
public class T03_projectDAOImpl extends BaseDAO implements T03_projectDAO{

	@Override
	public T03_project getT01_projectByP_id(T03_project project) throws Exception{
		return this.getSqlSession().selectOne("getT01_projectByP_id", project);
	}

	@Override
	public List getT03_project_guideByP_id(T03_project project) throws Exception{
		return this.getSqlSession().selectList("getT03_project_guideByP_id", project);
	}

	@Override
	public void insertT03_user_project(T03_user_project userProject) {
		this.getSqlSession().insert("insertT03_user_project", userProject);
	}

	@Override
	public T03_user_project getT03_user_projectByPidAndTelephone(
			T03_user_project param) {
		return this.getSqlSession().selectOne("getT03_user_projectByPidAndTelephone", param);
	}

	@Override
	public void insertT03_project_access(T03_project_access access) {
		this.getSqlSession().insert("insertT03_project_access", access);
	}

	@Override
	public void modifyT03_projectP_click_acount(T03_project project) {
		this.getSqlSession().update("modifyT03_projectP_click_acount", project);
	}

	@Override
	public T03_user_platform getT03_user_platformByPlatformidAndTelephone(
			T03_user_platform platform) {
		return this.getSqlSession().selectOne("getT03_user_platformByPlatformidAndTelephone", platform);
	}

}
