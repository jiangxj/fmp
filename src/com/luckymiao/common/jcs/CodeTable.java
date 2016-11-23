package com.luckymiao.common.jcs;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.sql.DataSource;

import org.apache.jcs.JCS;
import org.apache.log4j.Logger;

import com.luckymiao.common.utils.CollectionUtils;
import com.luckymiao.common.utils.LogUtils;
import com.luckymiao.common.utils.SQLExecuteUtils;
import com.sun.org.apache.commons.beanutils.PropertyUtils;

public class CodeTable {
	private static Logger logger = LogUtils.getLogger(CodeTable.class);

	private DataSource dataSource = null;

	public CodeTable(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public boolean initCatchIndex(JCS commonCache) {
		boolean flag = false;
		initCatchDict(commonCache);
		initCatchSQL(commonCache);
		initCatchParam(commonCache);
		return flag;
	}

	public void initCatchDict(JCS commonCache) {
		LinkedHashMap treecacheMap = new LinkedHashMap();
		Connection conn = null;
		try {
			String sqlStr = "select * from t00_dict where flag = '1' order by seq";
			conn = dataSource.getConnection();
			List list = SQLExecuteUtils.queryForList(conn, sqlStr);
			Iterator<Map> iter = list.iterator();
			Map valueMap = new TreeMap();
			while (iter.hasNext()) {
				Map eMap = iter.next();//
				String key = (String) eMap.get("DISCTYPE");//
				valueMap = (Map) treecacheMap.get(key);
				if(valueMap == null){
					valueMap = new LinkedHashMap();
				}
				valueMap.put(eMap.get("DISCKEY"), eMap.get("DISCNAME"));
				treecacheMap.put(key, valueMap);
			}
			commonCache.put("jcs_sys_hash_dict_catch_index", treecacheMap);//
			logger.info("【jcs_sys_hash_dict_catch_index】缓存加载成功！");
		} catch (Exception e) {
			logger.error("INIT CJ_DICT ERROR!");
			e.printStackTrace();
		}finally{
			if(conn != null){
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void initCatchSQL(JCS commonCache) {
		LinkedHashMap jcsSqlCacheMap = new LinkedHashMap();
		Connection conn = null;
		try {
			String sqlStr = "select * from t00_jcs_sql where flag = '1'";
			conn = dataSource.getConnection();
			List list = SQLExecuteUtils.queryForList(conn, sqlStr);
			Iterator<Map> iter = list.iterator();
			Map valueMap = new TreeMap();
			while (iter.hasNext()) {
				Map eMap = iter.next();//
				String key = (String) eMap.get("JNAME");//
				String jsql = (String) eMap.get("JSQL");
				String jdto = (String) eMap.get("JDTO");
				List subList = SQLExecuteUtils.queryForList(conn, jsql);
				if (jdto == null || "".equals(jdto)
						|| "null".equalsIgnoreCase(jdto.trim())) {
					jcsSqlCacheMap.put(key, subList);
				} else {
					List eList = new ArrayList();
					for (Object object : subList) {
						try{
							Object jdtoOjb = Class.forName(jdto).newInstance();
							Map rowMap = (Map) object;
							Map lowerKeyMap = CollectionUtils
									.toLowerCaseKey(rowMap);
							PropertyUtils.copyProperties(jdtoOjb, lowerKeyMap);
							eList.add(jdtoOjb);
						}catch(Exception e){
							Map rowMap = (Map) object;
							Map lowerKeyMap = CollectionUtils
									.toLowerCaseKey(rowMap);
							eList.add(lowerKeyMap);
						}
					}
					jcsSqlCacheMap.put(key, eList);
				}
			}
			commonCache.put("jcs_sys_hash_sql_catch_index", jcsSqlCacheMap);//
			logger.info("【jcs_sys_hash_sql_catch_index】缓存加载成功！");
		} catch (Exception e) {
			logger.error("INIT CJ_JCS_SQL ERROR!");
			e.printStackTrace();
		}finally{
			if(conn != null){
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void initCatchParam(JCS commonCache) {
		LinkedHashMap systemParamCacheMap = new LinkedHashMap();
		Connection conn = null;
		try {
			String sqlStr = "select * from t00_system_param where flag = '1'";
			conn = dataSource.getConnection();
			List list = SQLExecuteUtils.queryForList(conn, sqlStr);
			Iterator<Map> iter = list.iterator();
			while (iter.hasNext()) {
				Map eMap = iter.next();//
				systemParamCacheMap.put(eMap.get("PARAMKEY"), eMap
						.get("PARAMVALUE"));
			}
			commonCache.put("jcs_sys_hash_param_catch_index", systemParamCacheMap);//
			logger.info("【jcs_sys_hash_param_catch_index】缓存加载成功！");
		} catch (Exception e) {
			logger.error("INIT CJ_SYSTEM_PARAM ERROR!");
			e.printStackTrace();
		}finally{
			if(conn != null){
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
