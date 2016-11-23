package com.luckymiao.common.service;

import javax.servlet.http.HttpServletRequest;

import com.luckymiao.base.Service;
import com.luckymiao.common.ResponseJson;

public interface T01_common_interfaceService extends Service{

	public ResponseJson getResultFromCommonInterface(HttpServletRequest request);
	
	public ResponseJson updateFromCommonInterface(HttpServletRequest request);
	
}
