package com.luckymiao.common.jcs;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.jcs.JCS;
import org.apache.jcs.access.exception.CacheException;
import org.apache.log4j.Logger;

import com.luckymiao.common.properties.XMLProperties;
import com.luckymiao.common.utils.LogUtils;

public class CatchManager {
	private Logger logger = LogUtils.getLogger(CatchManager.class);

	private DataSource dataSource = null;

	private XMLProperties sysConfig = null;

	private JCS popCommonCache = null;

	private String cacheswitch = "";

	public CatchManager(DataSource _ds, XMLProperties sysConfig) {

		try {
			this.dataSource = _ds;
			this.cacheswitch = sysConfig.getProperty("root.cache.jcs.switch");
			this.popCommonCache = JCS.getInstance("popCommonCache");
			loadCacheIndex();

		} catch (Exception e) {
			this.logger.error("初始化缓存失败！");
			e.printStackTrace();
		}
	}

	private String loadCacheIndex() throws CacheException {

		CodeTable ct = new CodeTable(this.dataSource);
		ct.initCatchIndex(this.popCommonCache);
		return "";
	}

	public Map getMapFromCache(String type) {
		LinkedHashMap map = null;
		Object obj = getObjectFromCache(type);//cjpCommonCache.get("jcs_sys_hash_dict_catch_index");
		if (obj != null && obj instanceof LinkedHashMap) {
			map = (LinkedHashMap) obj;
		}
		if (map == null) {
			map = new LinkedHashMap();
		}
		return map;
	}

	public ArrayList getListFromCache(String type) {
		ArrayList list = null;
		Object obj = getObjectFromCache(type);//cjpCommonCache.get("jcs_sys_hash_dict_catch_index");
		if (obj != null && obj instanceof ArrayList) {
			list = (ArrayList) obj;
		}
		if (list == null) {
			list = new ArrayList();
		}
		return list;
	}
	

	public String getSystemParamFromCache(String name) {
		String resultStr = "";
		try {
			if(popCommonCache.get("jcs_sys_hash_param_catch_index") == null){
				loadParamCache();
			}
			resultStr = (String)((Map) popCommonCache
					.get("jcs_sys_hash_param_catch_index")).get(name);
		} catch (CacheException e) {
			e.printStackTrace();
		}
		return  resultStr;
	}

	private Object getObjectFromCache(String type) {
		Object vObj = null;
		try {
			if(this.popCommonCache.get("jcs_sys_hash_dict_catch_index") == null 
					|| this.popCommonCache.get("jcs_sys_hash_sql_catch_index") == null) {
				loadCache();
			}
			if(((LinkedHashMap) this.popCommonCache
					.get("jcs_sys_hash_dict_catch_index")).containsKey(type)){
				vObj = ((LinkedHashMap)this.popCommonCache.get("jcs_sys_hash_dict_catch_index")).get(type);
			}else if(((LinkedHashMap) this.popCommonCache
					.get("jcs_sys_hash_sql_catch_index")).containsKey(type)){
				vObj = ((LinkedHashMap)this.popCommonCache.get("jcs_sys_hash_sql_catch_index")).get(type);
			}else{
				vObj = new Object();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return vObj;
	}

	private void loadCache() throws CacheException {
		CodeTable ct = new CodeTable(this.dataSource);
		ct.initCatchIndex(this.popCommonCache);
	}
	private void loadSQLCache() throws CacheException {
		CodeTable ct = new CodeTable(this.dataSource);
		ct.initCatchSQL(this.popCommonCache);
	}
	private void loadDictCache() throws CacheException {
		CodeTable ct = new CodeTable(this.dataSource);
		ct.initCatchDict(this.popCommonCache);
	}
	private void loadParamCache() throws CacheException {
		CodeTable ct = new CodeTable(this.dataSource);
		ct.initCatchParam(this.popCommonCache);
	}

	public DataSource getDataSource() {
		return dataSource;
	}
	
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public JCS getCommonCache() {
		return popCommonCache;
	}
	
	public void setCommonCache(JCS popCommonCache) {
		this.popCommonCache = popCommonCache;
	}

	public XMLProperties getSysConfig() {
		return this.sysConfig;
	}

	public void setSysConfig(XMLProperties sysConfig) {
		this.sysConfig = sysConfig;
	}
}
