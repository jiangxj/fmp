package com.luckymiao.log.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.luckymiao.base.Service;
import com.luckymiao.common.ResponseJson;
import com.luckymiao.log.dto.T03_access;

public interface T03_accessService extends Service{

	public ResponseJson addT03_access(HttpServletRequest request,
			HttpServletResponse response, T03_access access);

}
