package com.luckymiao.base;

import org.springframework.beans.factory.annotation.Autowired;

import com.luckymiao.common.cache.CacheMap;
import com.luckymiao.common.jcs.CatchManager;
import com.luckymiao.common.mybatis.plugin.PageInterceptor;
import com.luckymiao.common.mybatis.plugin.PageParameter;
import com.luckymiao.common.properties.XMLProperties;


public class BaseService {
	@Autowired
	public CatchManager cm;
	@Autowired
	public CacheMap cacheMap;
	@Autowired
	public XMLProperties sysConfig;
	@Autowired
	public PageInterceptor pageInterceptor;
	
	public PageParameter getPageParameter(String sqlId){
		return pageInterceptor.getPageParameter(sqlId);
	}
}
