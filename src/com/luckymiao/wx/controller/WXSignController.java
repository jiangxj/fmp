package com.luckymiao.wx.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.luckymiao.common.ResponseJson;
import com.luckymiao.common.utils.SignUtils;
import com.luckymiao.wx.dao.Wx_access_tokenDAO;
import com.luckymiao.wx.dto.Wx_access_token;

@Controller
@RequestMapping("/wx_jsapi")
public class WXSignController {
	@Autowired
	private Wx_access_tokenDAO wx_access_tokenDAO;
	@ResponseBody
	@RequestMapping("/sign")
	public String sign(HttpServletRequest request, HttpServletResponse response, Wx_access_token wx_access_token) {
		ResponseJson jsonObj = new ResponseJson();
		try {
			String shareUrl = request.getParameter("url");
			Wx_access_token dto =  wx_access_tokenDAO.getWX_jsapi_ticketByAppid(wx_access_token);
			if(dto != null){
				Map<String, String> resultMap = SignUtils.sign(dto.getJsapi_ticket(),
						shareUrl);
				jsonObj.setData(resultMap);
				jsonObj.setCode("0");
				jsonObj.setCodemsg("成功！");
			}else{
				jsonObj.setStatusFailure();
			}
		} catch (Exception e) {
			jsonObj.setStatusError();
			e.printStackTrace();
		}
		return jsonObj.toString();
	}
}
