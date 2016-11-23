package com.luckymiao.wx.service.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luckymiao.base.BaseService;
import com.luckymiao.common.ResponseJson;
import com.luckymiao.common.utils.DateUtils;
import com.luckymiao.common.utils.StringUtils;
import com.luckymiao.wx.dao.T03_suggestDAO;
import com.luckymiao.wx.dto.T03_suggest;
import com.luckymiao.wx.service.T03_suggestService;
@Service("/t03_suggestService")
public class T03_suggestServiceImpl extends BaseService implements T03_suggestService{
	@Autowired
	private T03_suggestDAO t03_suggestDAO;
	@Override
	public ResponseJson saveSuggestContent(HttpServletRequest request,
			HttpServletResponse response, T03_suggest suggest) {
		ResponseJson result = new ResponseJson();
		try {
			suggest.setSid(StringUtils.getDateNumberSequence());
			suggest.setCreatedate(DateUtils.getCurrTime());
			t03_suggestDAO.insertT03_suggest(suggest);
			result.setCode("0");
			result.setCodemsg("提交成功");
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatusError();
		}
		return result;
	}

}
