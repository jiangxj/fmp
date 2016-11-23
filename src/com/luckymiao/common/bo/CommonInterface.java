package com.luckymiao.common.bo;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * CommonInterfaceService 外部服务通用接口
 * @author jiangxiaojie
 * 
 */
public interface CommonInterface {
	/**
	 * before 在主SQL执行之前调用
	 * @param request
	 * @param response
	 */
	public void beforeCall(HttpServletRequest request);
	/**
	 * after 在主SQL执行之后调用
	 * @param request
	 * @param response
	 * @param dataList SQL执行后的结果数据，如果是更新或者插入则List中存放的是每条SQL执行成功的行数；如果是查询则List中存放的以Map形式数据记录，key-字段名（大写），value-字段的值
	 */
	public List afterCall(HttpServletRequest request, List dbDataList);
	/**
	 * finallyCall 在响应结果输出之前调用
	 * @param jsonStr 响应的JSON字符串
	 */
	public String finallyCall(String jsonStr);
}
