package com.luckymiao.log.service.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luckymiao.base.BaseService;
import com.luckymiao.common.ResponseJson;
import com.luckymiao.common.utils.DateUtils;
import com.luckymiao.common.utils.StringUtils;
import com.luckymiao.log.dao.T03_accessDAO;
import com.luckymiao.log.dto.T03_access;
import com.luckymiao.log.service.T03_accessService;
@Service("t03_accessService")
public class T03_accessServiceImpl extends BaseService implements T03_accessService{
	@Autowired
	private T03_accessDAO t03_accessDAO;
	
	@Override
	public ResponseJson addT03_access(HttpServletRequest request,
			HttpServletResponse response, T03_access access) {
		ResponseJson result = new ResponseJson();
		access.setAid(StringUtils.getDateNumberSequence());
		access.setCreatedate(DateUtils.getCurrTime());
		access.setRip(request.getRemoteHost());
		t03_accessDAO.insertT03_access(access);
		result.setCode("0");
		result.setCodemsg("记录成功");
		return result;
	}

}
