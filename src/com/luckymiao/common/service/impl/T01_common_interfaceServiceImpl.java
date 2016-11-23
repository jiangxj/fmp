package com.luckymiao.common.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.luckymiao.base.BaseService;
import com.luckymiao.common.ResponseJson;
import com.luckymiao.common.bo.CommonInterface;
import com.luckymiao.common.dao.T01_common_interfaceDAO;
import com.luckymiao.common.dto.T01_common_interface;
import com.luckymiao.common.service.T01_common_interfaceService;
import com.luckymiao.common.utils.SQLUtils;

@Service("t01_common_interfaceService")
public class T01_common_interfaceServiceImpl extends BaseService implements
		T01_common_interfaceService {
	private static Logger logger = Logger
			.getLogger(T01_common_interfaceServiceImpl.class.getName());
	@Autowired
	private T01_common_interfaceDAO t01_common_interfaceDAO;

	public ResponseJson getResultFromCommonInterface(HttpServletRequest request) {
		CommonInterface service = null;
		ResponseJson resultJObj = new ResponseJson();
		try {
			String ciid = request.getParameter("actionid");
			String page = request.getParameter("page");
			T01_common_interface t01_common_interface = null;
			List<T01_common_interface> cacheList = cm
					.getListFromCache("getT01_common_interfaceList");
			for (T01_common_interface dto : cacheList) {
				if (StringUtils.equals(dto.getCiid(), ciid)) {
					t01_common_interface = dto;
					break;
				}
			}
			if (t01_common_interface == null) {
				t01_common_interface = t01_common_interfaceDAO
						.getT01_common_intefaceByCiid(ciid);
			}
			if (t01_common_interface != null) {
				String origSql = t01_common_interface.getExecsql();
				String type = t01_common_interface.getType();
				String execclass = t01_common_interface.getExecclass();
				if (!StringUtils.isBlank(execclass)) {
					try {
						service = (CommonInterface) Class.forName(execclass)
								.newInstance();
					} catch (Exception ex) {
						logger
								.error("CommonInterfaceService接口实例化失败，请检查表【cj_common_inteface】的actionid：【"
										+ ciid
										+ "】的配置execclass：【"
										+ execclass
										+ "】是否正确并且实现接口【CommonInterfaceService】");
					}

				}
				Map condMap = request.getParameterMap();
				if ("0".equals(type)) {
					if (service != null) {
						try {
							service.beforeCall(request);
						} catch (Exception ex) {
							logger.error(ex.getMessage());
						}
					}

					List<String> execSqlList = SQLUtils.parseSQL(origSql,
							request);
					Map<String, Object> data = new HashMap();
					List<Map> dataList = new ArrayList();
					String sql = execSqlList.get(0);
					if (sql.indexOf("#LIMIT#") >= 0) {
						sql = sql.replace("#LIMIT#", " ");
						dataList = t01_common_interfaceDAO.queryList(sql,
								Integer.parseInt(page), 10);

						data.put("pageInfo", getPageParameter("querySQL"));
					} else {
						dataList = t01_common_interfaceDAO.queryList(sql);
					}
					if (service != null) {
						try {
							service.afterCall(request, dataList);
						} catch (Exception ex) {
							logger.error(ex.getMessage());
						}
					}
					List tableList = new ArrayList();
					if (dataList != null && !dataList.isEmpty()) {
						for (Map eMap : dataList) {
							Map rowMap = new HashMap();
							Iterator it = eMap.keySet().iterator();
							while (it.hasNext()) {
								String key = (String) it.next();
								String value = String
										.valueOf(com.luckymiao.common.utils.StringUtils
												.null2String(eMap.get(key)));
								rowMap.put(key, value);
							}
							tableList.add(rowMap);
						}
					}
					data.put("datalist", tableList);
					resultJObj.setData(data);
				}
				resultJObj.setCode("0");
				resultJObj.setCodemsg("查询成功");
			} else {
				resultJObj.setCode("2");
				resultJObj.setCodemsg("功能不存在！");
			}

		} catch (Exception e) {
			resultJObj.setCode("1");
			resultJObj.setCodemsg("查询失败");
			resultJObj.setStatusError();
			e.printStackTrace();
		}
		return resultJObj;
	}

	@Transactional
	public ResponseJson updateFromCommonInterface(HttpServletRequest request) {
		CommonInterface service = null;
		ResponseJson resultJObj = new ResponseJson();
		try {
			String ciid = request.getParameter("actionid");
			T01_common_interface t01_common_interface = null;
			List<T01_common_interface> cacheList = cm
					.getListFromCache("getT01_common_interfaceList");
			for (T01_common_interface dto : cacheList) {
				if (StringUtils.equals(dto.getCiid(), ciid)) {
					t01_common_interface = dto;
					break;
				}
			}
			if (t01_common_interface == null) {
				t01_common_interface = t01_common_interfaceDAO
						.getT01_common_intefaceByCiid(ciid);
			}
			if (t01_common_interface != null) {
				String origSql = t01_common_interface.getExecsql();
				String type = t01_common_interface.getType();
				String execclass = t01_common_interface.getExecclass();
				if (!StringUtils.isBlank(execclass)) {
					try {
						service = (CommonInterface) Class.forName(execclass)
								.newInstance();
					} catch (Exception ex) {
						logger
								.error("CommonInterfaceService接口实例化失败，请检查表【cj_common_inteface】的actionid：【"
										+ ciid
										+ "】的配置execclass：【"
										+ execclass
										+ "】是否正确并且实现接口【CommonInterfaceService】");
					}

				}
				List resultList = new ArrayList();
				if ("1".equals(type)) {
					if (service != null) {
						try {
							service.beforeCall(request);
						} catch (Exception ex) {
							logger.error(ex.getMessage());
						}
					}

					Map condMap = request.getParameterMap();
					if (service != null) {
						try {
							service.beforeCall(request);
						} catch (Exception ex) {
							logger.error(ex.getMessage());
						}
					}
					List<String> execSqlList = SQLUtils.parseSQL(origSql,
							request);
					List<Integer> effectList = new ArrayList();
					for (String execSql : execSqlList) {
						int i = -1;
						if (execSql.toLowerCase().startsWith("delete")) {
							i = t01_common_interfaceDAO.execDelete(execSql);
						} else if (execSql.toLowerCase().startsWith("update")) {
							i = t01_common_interfaceDAO.execUpdate(execSql);
						} else if (execSql.toLowerCase().startsWith("insert")) {
							i = t01_common_interfaceDAO.execInsert(execSql);
						}
						effectList.add(i);
					}
				}
				resultJObj.setCode("0");
				resultJObj.setCodemsg("更新成功");
			} else {
				resultJObj.setCode("2");
				resultJObj.setCodemsg("功能不存在！");
			}

		} catch (Exception e) {
			resultJObj.setCode("1");
			resultJObj.setCodemsg("更新失败");
			resultJObj.setStatusError();
			e.printStackTrace();
		}
		return resultJObj;
	}
}
